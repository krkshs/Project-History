$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem
if (!('RecoveryClassInfo' -as [type])) {
    Add-Type -TypeDefinition @'
using System;
using System.IO;
using System.Text;
public sealed class RecoveryClassInfo {
    public int MajorVersion;
    public string SourceFile;
    static int U2(BinaryReader r) { return (r.ReadByte() << 8) | r.ReadByte(); }
    static uint U4(BinaryReader r) { return ((uint)U2(r) << 16) | (uint)U2(r); }
    static void Skip(BinaryReader r, long n) {
        if (r.BaseStream.CanSeek) r.BaseStream.Seek(n, SeekOrigin.Current);
        else { while (n > 0) { int k = (int)Math.Min(n, 8192); if (r.ReadBytes(k).Length != k) throw new EndOfStreamException(); n -= k; } }
    }
    static void Member(BinaryReader r) {
        Skip(r, 6); int count = U2(r);
        for (int i = 0; i < count; i++) { U2(r); Skip(r, U4(r)); }
    }
    public static RecoveryClassInfo Read(Stream s) {
        using (var r = new BinaryReader(s, Encoding.UTF8, true)) {
            if (U4(r) != 0xCAFEBABE) throw new InvalidDataException("Invalid class header");
            U2(r); var result = new RecoveryClassInfo { MajorVersion = U2(r) };
            int cpCount = U2(r); var strings = new string[cpCount];
            for (int i = 1; i < cpCount; i++) {
                int tag = r.ReadByte();
                switch (tag) {
                    case 1: strings[i] = Encoding.UTF8.GetString(r.ReadBytes(U2(r))); break;
                    case 3: case 4: Skip(r, 4); break;
                    case 5: case 6: Skip(r, 8); i++; break;
                    case 7: case 8: case 16: case 19: case 20: Skip(r, 2); break;
                    case 9: case 10: case 11: case 12: case 17: case 18: Skip(r, 4); break;
                    case 15: Skip(r, 3); break;
                    default: throw new InvalidDataException("Unknown constant pool tag: " + tag);
                }
            }
            Skip(r, 6); Skip(r, U2(r) * 2);
            int fields = U2(r); for (int i = 0; i < fields; i++) Member(r);
            int methods = U2(r); for (int i = 0; i < methods; i++) Member(r);
            int attrs = U2(r);
            for (int i = 0; i < attrs; i++) {
                string name = strings[U2(r)]; uint length = U4(r);
                if (name == "SourceFile" && length == 2) result.SourceFile = strings[U2(r)];
                else Skip(r, length);
            }
            return result;
        }
    }
}
'@
}

$workspaceRoot = Split-Path -Parent $PSScriptRoot
$originalJar = Join-Path $workspaceRoot 'game\_original files\game.jar'
if (!(Test-Path -LiteralPath $originalJar)) { $originalJar = Join-Path $workspaceRoot 'game\game.jar' }
$jobs = @(
    @{ Name = 'game'; Jar = $originalJar; Output = (Join-Path $PSScriptRoot 'src') },
    @{ Name = 'MapEditor_2'; Jar = (Join-Path $workspaceRoot 'game\map\MapEditor_2.jar'); Output = (Join-Path $PSScriptRoot 'editors\MapEditor_2\src') },
    @{ Name = 'MapEditor_2_Cities'; Jar = (Join-Path $workspaceRoot 'game\map\MapEditor_2_Cities.jar'); Output = (Join-Path $PSScriptRoot 'editors\MapEditor_2_Cities\src') }
)
$allRecords = [System.Collections.Generic.List[object]]::new()
$summaries = @()
foreach ($job in $jobs) {
    $archive = [System.IO.Compression.ZipFile]::OpenRead($job.Jar)
    try {
        $records = [System.Collections.Generic.List[object]]::new()
        $moduleIndex = 0
        foreach ($entry in $archive.Entries) {
            if (!$entry.FullName.EndsWith('.class')) { continue }
            $stream = $entry.Open()
            try { $info = [RecoveryClassInfo]::Read($stream) } finally { $stream.Dispose() }
            $slash = $entry.FullName.LastIndexOf('/')
            $prefix = if ($slash -ge 0) { $entry.FullName.Substring(0, $slash + 1) } else { '' }
            $sourceName = $info.SourceFile
            if (!$sourceName) { $sourceName = (($entry.Name -replace '\.class$', '') -split '\$')[0] + '.java' }
            $sourcePath = $prefix + $sourceName
            if ($entry.Name -eq 'module-info.class') {
                $moduleIndex++
                $sourcePath = '../module-descriptors/' + ('{0:D2}' -f $moduleIndex) + '/module-info.java'
            }
            $record = [pscustomobject]@{
                Archive = $job.Name
                Class = $entry.FullName
                MajorVersion = $info.MajorVersion
                Source = $sourcePath
                SourcePresent = (Test-Path -LiteralPath (Join-Path $job.Output $sourcePath) -PathType Leaf)
            }
            $records.Add($record)
            $allRecords.Add($record)
        }
        $summaries += [pscustomobject]@{
            Archive = $job.Name
            Input = $job.Jar
            SHA256 = (Get-FileHash -LiteralPath $job.Jar -Algorithm SHA256).Hash.ToLowerInvariant()
            ClassFiles = $records.Count
            GameClassFiles = @($records | Where-Object { $_.Class.StartsWith('aoc/') -or $_.Class.StartsWith('age/') }).Count
            JavaSourceFiles = @(Get-ChildItem -LiteralPath $job.Output -File -Recurse -Filter '*.java').Count
            ClassesWithSource = @($records | Where-Object SourcePresent).Count
            ModuleDescriptors = $moduleIndex
            MissingSourceClasses = @($records | Where-Object { !$_.SourcePresent } | Select-Object -ExpandProperty Class)
            ClassVersions = @($records | Group-Object MajorVersion | ForEach-Object { [pscustomobject]@{ MajorVersion = [int]$_.Name; Count = $_.Count } })
        }
    } finally { $archive.Dispose() }
}
$allRecords | Export-Csv -LiteralPath (Join-Path $PSScriptRoot 'class-inventory.csv') -NoTypeInformation -Encoding utf8
$summaries | ConvertTo-Json -Depth 8 | Set-Content -LiteralPath (Join-Path $PSScriptRoot 'recovery-report.json') -Encoding utf8
$markers = [System.Collections.Generic.List[object]]::new()
$failurePattern = "This method has failed to decompile|Couldn't be decompiled|Could not be decompiled|Unable to fully structure|\*\* GOTO|\*\* break|Exception decompiling|// Byte code:"
foreach ($sourceFile in @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src'), (Join-Path $PSScriptRoot 'editors'), (Join-Path $PSScriptRoot 'module-descriptors') -Recurse -File -Filter '*.java')) {
    if ([regex]::IsMatch([System.IO.File]::ReadAllText($sourceFile.FullName), $failurePattern, [System.Text.RegularExpressions.RegexOptions]::IgnoreCase)) {
        $markers.Add([pscustomobject]@{File = $sourceFile.FullName; Reason = 'Decompiler failure marker'})
    }
}
ConvertTo-Json -InputObject @($markers.ToArray()) -Depth 3 | Set-Content -LiteralPath (Join-Path $PSScriptRoot 'decompiler-issues.json') -Encoding utf8
$summaries | Select-Object Archive, ClassFiles, GameClassFiles, JavaSourceFiles, ClassesWithSource, ModuleDescriptors | ConvertTo-Json
Write-Output "Source files with decompiler failure markers: $($markers.Count)"
if (@($allRecords | Where-Object { !$_.SourcePresent }).Count -gt 0) { throw 'Some class files have no corresponding recovered source. See recovery-report.json.' }
if ($markers.Count -gt 0) { throw 'Some source files contain decompiler failure markers. See decompiler-issues.json.' }
