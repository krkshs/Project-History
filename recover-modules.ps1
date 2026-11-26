param([string]$Java = 'java', [string]$Jar)
$ErrorActionPreference = 'Stop'
if (!$Jar) { $Jar = Join-Path (Split-Path -Parent $PSScriptRoot) 'game\_original files\game.jar' }
Add-Type -AssemblyName System.IO.Compression.FileSystem
$archive = [System.IO.Compression.ZipFile]::OpenRead($Jar)
try {
    $moduleIndex = 0
    $modules = @()
    foreach ($entry in $archive.Entries) {
        if ($entry.Name -ne 'module-info.class') { continue }
        $moduleIndex++
        $moduleDirectory = Join-Path $PSScriptRoot ('module-descriptors\{0:D2}' -f $moduleIndex)
        New-Item -ItemType Directory -Path $moduleDirectory -Force | Out-Null
        $classPath = Join-Path $moduleDirectory 'module-info.class'
        [System.IO.Compression.ZipFileExtensions]::ExtractToFile($entry, $classPath, $true)
        $logPath = Join-Path $moduleDirectory 'decompiler.log'
        & $Java -Xmx256M -jar (Join-Path $PSScriptRoot 'tools\jd-cli-1.3.0-RC-1.jar') --skipResources --logLevel WARN --outputDir $moduleDirectory $classPath *> $logPath
        if ($LASTEXITCODE -ne 0 -or !(Test-Path -LiteralPath (Join-Path $moduleDirectory 'module-info.java'))) { throw "Failed to restore module descriptor $moduleIndex" }
        $modules += [pscustomobject]@{ Index = $moduleIndex; ArchiveEntry = $entry.FullName; Source = "module-descriptors/$('{0:D2}' -f $moduleIndex)/module-info.java"; SHA256 = (Get-FileHash -LiteralPath $classPath -Algorithm SHA256).Hash.ToLowerInvariant() }
    }
    $modules | ConvertTo-Json -Depth 4 | Set-Content -LiteralPath (Join-Path $PSScriptRoot 'module-descriptors\inventory.json') -Encoding utf8
    Write-Output "Recovered $moduleIndex module descriptors separately."
} finally { $archive.Dispose() }
