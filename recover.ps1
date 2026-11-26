param([string]$Java = 'java')

$ErrorActionPreference = 'Stop'
$recoveryRoot = $PSScriptRoot
$workspaceRoot = Split-Path -Parent $recoveryRoot
$gameRoot = Join-Path $workspaceRoot 'game'
$decompiler = Join-Path $recoveryRoot 'tools\vineflower-1.11.2.jar'
$jdCli = Join-Path $recoveryRoot 'tools\jd-cli-1.3.0-RC-1.jar'

foreach ($directory in @('tools', 'logs', 'src', 'resources', 'editors')) {
    New-Item -ItemType Directory -Path (Join-Path $recoveryRoot $directory) -Force | Out-Null
}
foreach ($tool in @(
    @{ Path = $decompiler; Url = 'https://repo.maven.apache.org/maven2/org/vineflower/vineflower/1.11.2/vineflower-1.11.2.jar'; Hash = 'e1e2415e7f78b34960402c4beddfc88e033d7842a23ecd132a8ec2eadd54f6bf' }
)) {
    if (!(Test-Path -LiteralPath $tool.Path)) { Invoke-WebRequest -Uri $tool.Url -OutFile $tool.Path }
    if ((Get-FileHash -LiteralPath $tool.Path -Algorithm SHA256).Hash.ToLowerInvariant() -ne $tool.Hash) {
        throw "Decompiler checksum mismatch: $($tool.Path)"
    }
}
Add-Type -AssemblyName System.IO.Compression.FileSystem
if (!(Test-Path -LiteralPath $jdCli)) {
    $jdDistribution = Join-Path $recoveryRoot 'tools\jd-cli-1.3.0-RC-1-dist.zip'
    Invoke-WebRequest -Uri 'https://github.com/intoolswetrust/jd-cli/releases/download/jd-cli-1.3.0-RC-1/jd-cli-1.3.0-RC-1-dist.zip' -OutFile $jdDistribution
    if ((Get-FileHash -LiteralPath $jdDistribution -Algorithm SHA256).Hash.ToLowerInvariant() -ne '9a5473f6c22ed7a3658c36e6cff60d48153165a770c6beba1a861bf39a42ccfe') { throw 'JD-CLI distribution checksum mismatch.' }
    $distributionArchive = [System.IO.Compression.ZipFile]::OpenRead($jdDistribution)
    try { [System.IO.Compression.ZipFileExtensions]::ExtractToFile($distributionArchive.GetEntry('jd-cli.jar'), $jdCli, $true) }
    finally { $distributionArchive.Dispose() }
}
if ((Get-FileHash -LiteralPath $jdCli -Algorithm SHA256).Hash.ToLowerInvariant() -ne 'f459d4fc56724cb578657a839aa980b09961c582df829b893e6c10934fe666b5') { throw 'JD-CLI checksum mismatch.' }

$originalJar = Join-Path $gameRoot '_original files\game.jar'
if (!(Test-Path -LiteralPath $originalJar)) {
    $originalJar = Join-Path $gameRoot 'game.jar'
}
$jobs = @(
    @{ Name = 'game'; Jar = $originalJar; Output = (Join-Path $recoveryRoot 'src'); Resources = (Join-Path $recoveryRoot 'resources') },
    @{ Name = 'MapEditor_2'; Jar = (Join-Path $gameRoot 'map\MapEditor_2.jar'); Output = (Join-Path $recoveryRoot 'editors\MapEditor_2\src'); Resources = (Join-Path $recoveryRoot 'editors\MapEditor_2\resources') },
    @{ Name = 'MapEditor_2_Cities'; Jar = (Join-Path $gameRoot 'map\MapEditor_2_Cities.jar'); Output = (Join-Path $recoveryRoot 'editors\MapEditor_2_Cities\src'); Resources = (Join-Path $recoveryRoot 'editors\MapEditor_2_Cities\resources') }
)

foreach ($job in $jobs) {
    foreach ($destination in @($job.Output, $job.Resources)) {
        New-Item -ItemType Directory -Path $destination -Force | Out-Null
    }
    Write-Output "Decompiling $($job.Name)..."
    if ($job.Name -eq 'game') {
        $logPath = Join-Path $recoveryRoot 'logs\game-only-vineflower.log'
        & $Java -Xmx3G -jar $decompiler --folder --thread-count=4 --log-level=WARN --skip-extra-files=true --only=aoc/ --only=age/ "--add-external=$(Join-Path $gameRoot 'jre\lib\rt.jar')" $job.Jar $job.Output *> $logPath
        if ($LASTEXITCODE -ne 0) { throw "Game decompilation failed. See $logPath" }
        $logPath = Join-Path $recoveryRoot 'logs\libraries-jd-cli.log'
        & $Java -Xmx1G -jar $jdCli --skipResources --serialProcessing --logLevel WARN --pattern '^(?!aoc/|age/).*' --outputDir $job.Output $job.Jar *> $logPath
    } else {
        $logPath = Join-Path $recoveryRoot "logs\$($job.Name)-vineflower.log"
        & $Java -Xmx1G -jar $decompiler --folder --thread-count=2 --log-level=WARN --skip-extra-files=true "--add-external=$(Join-Path $gameRoot 'jre\lib\rt.jar')" $job.Jar $job.Output *> $logPath
    }
    if ($LASTEXITCODE -ne 0) { throw "Decompiler failed for $($job.Name). See $logPath" }

    $archive = [System.IO.Compression.ZipFile]::OpenRead($job.Jar)
    try {
        $allowedRoot = [System.IO.Path]::GetFullPath($job.Resources).TrimEnd('\', '/') + [System.IO.Path]::DirectorySeparatorChar
        foreach ($entry in $archive.Entries) {
            if ($entry.FullName.EndsWith('.class') -or $entry.FullName.EndsWith('/')) { continue }
            $target = [System.IO.Path]::GetFullPath((Join-Path $job.Resources $entry.FullName))
            if (!$target.StartsWith($allowedRoot, [System.StringComparison]::OrdinalIgnoreCase)) {
                throw "Archive entry escapes output directory: $($entry.FullName)"
            }
            New-Item -ItemType Directory -Path (Split-Path -Parent $target) -Force | Out-Null
            [System.IO.Compression.ZipFileExtensions]::ExtractToFile($entry, $target, $true)
        }
    } finally { $archive.Dispose() }
}

& (Join-Path $recoveryRoot 'recover-modules.ps1') -Java $Java -Jar $originalJar
& (Join-Path $recoveryRoot 'repair-decompilation.ps1') -Java $Java
& (Join-Path $recoveryRoot 'verify-recovery.ps1')
