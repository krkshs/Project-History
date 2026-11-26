param([string]$JavaHome = 'C:\Program Files\Eclipse Adoptium\jdk-21.0.3.9-hotspot')
$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
$java = Join-Path $JavaHome 'bin\java.exe'
$javac = Join-Path $JavaHome 'bin\javac.exe'
$logRoot = Join-Path $projectRoot 'logs\workflow'
New-Item -ItemType Directory -Path $logRoot -Force | Out-Null
$javaFile = Join-Path $projectRoot 'src\aoc\kingdoms\lukasz\jakowski\desktop\DesktopLauncher.java'
$pngFile = Join-Path $projectRoot 'asset-src\map\Earth3\data\scales\1\0.png'
$jsonFile = Join-Path $projectRoot 'asset-src\map\Earth3\civs_template\aac.json'
$paths = @($javaFile, $pngFile, $jsonFile)
$backups = @{}
foreach ($path in $paths) {
    $backups[$path] = [IO.File]::ReadAllBytes($path)
    [IO.File]::WriteAllBytes((Join-Path $logRoot ((Split-Path -Leaf $path) + '.original')), $backups[$path])
}
$helperOutput = Join-Path $projectRoot 'build\workflow-checker'
New-Item -ItemType Directory -Path $helperOutput -Force | Out-Null
& $javac --release 8 -Xlint:-options -d $helperOutput (Join-Path $PSScriptRoot 'AssetEditProbe.java')
if ($LASTEXITCODE -ne 0) { throw 'Probe compilation failed' }
$marker = 'AOH3_REBUILD_WORKFLOW_VERIFIED'
$writtenHashes = @{}
$result = [ordered]@{SourceEdit=$false;PngEdit=$false;SerializedJsonEdit=$false;BaselineRestored=$false}
try {
    $text = [Text.Encoding]::UTF8.GetString($backups[$javaFile])
    $needle = 'public class DesktopLauncher {'
    if (!$text.Contains($needle)) { throw 'DesktopLauncher class declaration changed; no probe was applied.' }
    $text = $text.Replace($needle, $needle + "`n   public static final String REBUILD_WORKFLOW_PROBE = `"$marker`";")
    [IO.File]::WriteAllText($javaFile, $text, [Text.UTF8Encoding]::new($false))
    $writtenHashes[$javaFile] = (Get-FileHash -LiteralPath $javaFile).Hash
    & $java '-Djava.awt.headless=true' -cp $helperOutput AssetEditProbe $pngFile
    if ($LASTEXITCODE -ne 0) { throw 'PNG probe failed' }
    $writtenHashes[$pngFile] = (Get-FileHash -LiteralPath $pngFile).Hash
    $template = [Text.Encoding]::UTF8.GetString($backups[$jsonFile]) | ConvertFrom-Json
    $template.year[0] = [int]$template.year[0] + 1
    [IO.File]::WriteAllText($jsonFile, ($template | ConvertTo-Json -Depth 30), [Text.UTF8Encoding]::new($false))
    $writtenHashes[$jsonFile] = (Get-FileHash -LiteralPath $jsonFile).Hash
    & (Join-Path $projectRoot 'build.ps1') -JavaHome $JavaHome *> (Join-Path $logRoot 'edited-build.log')
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $zip = [IO.Compression.ZipFile]::OpenRead((Join-Path $projectRoot 'build\dist\game.jar'))
    try {
        $stream = $zip.GetEntry('aoc/kingdoms/lukasz/jakowski/desktop/DesktopLauncher.class').Open()
        $memory = [IO.MemoryStream]::new()
        try { $stream.CopyTo($memory); $result.SourceEdit = [Text.Encoding]::UTF8.GetString($memory.ToArray()).Contains($marker) }
        finally { $stream.Dispose(); $memory.Dispose() }
    } finally { $zip.Dispose() }
    if (!$result.SourceEdit) { throw 'Source edit did not reach the rebuilt JAR' }
    $classpath = (Join-Path $projectRoot 'build\classes\java\assetTool') + ';' + (Join-Path $projectRoot 'build\classes\java\main') + ';' + (Join-Path $projectRoot 'lib\gson-2.10.1.jar')
    & $java '-Djava.awt.headless=true' -cp $classpath aoh3.tools.AssetTool verify-edits (Join-Path $projectRoot 'asset-src') (Join-Path $projectRoot 'build\dist') *> (Join-Path $logRoot 'asset-verification.log')
    if ($LASTEXITCODE -ne 0) { throw 'Built asset contents do not match edits' }
    $assetReport = Get-Content -LiteralPath (Join-Path $projectRoot 'build\asset-edits-verification.json') -Raw | ConvertFrom-Json
    if ($assetReport.verifiedEditedAssets -ne 2) { throw 'Expected two edited assets' }
    $result.PngEdit = $true
    $result.SerializedJsonEdit = $true
    Copy-Item -LiteralPath (Join-Path $projectRoot 'build\asset-build-report.json') -Destination (Join-Path $logRoot 'edited-asset-build-report.json') -Force
} finally {
    foreach ($path in $paths) {
        if (!$writtenHashes.ContainsKey($path) -or (Get-FileHash -LiteralPath $path).Hash -eq $writtenHashes[$path]) {
            [IO.File]::WriteAllBytes($path, $backups[$path])
        } else { throw "File changed during the probe; kept the current version: $path" }
    }
    & (Join-Path $projectRoot 'build.ps1') -JavaHome $JavaHome *> (Join-Path $logRoot 'restored-build.log')
    $result.BaselineRestored = $true
    $result | ConvertTo-Json | Set-Content -LiteralPath (Join-Path $projectRoot 'workflow-verification.json') -Encoding utf8
}
$result | ConvertTo-Json
