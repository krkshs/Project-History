param([switch]$Clean, [switch]$Run, [switch]$Zip, [string]$JavaHome)
$ErrorActionPreference = 'Stop'
$projectRoot = $PSScriptRoot
if (!$JavaHome) {
    $candidates = @()
    $jarCommand = Get-Command jar -ErrorAction SilentlyContinue
    if ($jarCommand) { $candidates += Split-Path -Parent (Split-Path -Parent $jarCommand.Source) }
    foreach ($parent in @('C:\Program Files\Eclipse Adoptium', 'C:\Program Files\Java')) {
        if (Test-Path -LiteralPath $parent) { $candidates += @(Get-ChildItem -LiteralPath $parent -Directory | Where-Object { $_.Name -match 'jdk-?(17|21|22|23|24)(\D|$)' } | Sort-Object Name -Descending | Select-Object -ExpandProperty FullName) }
    }
    if ($env:JAVA_HOME) { $candidates += $env:JAVA_HOME }
    foreach ($candidate in $candidates | Select-Object -Unique) {
        $releasePath = Join-Path $candidate 'release'
        if (!(Test-Path -LiteralPath (Join-Path $candidate 'bin\javac.exe')) -or !(Test-Path -LiteralPath $releasePath)) { continue }
        $release = [IO.File]::ReadAllText($releasePath)
        if ($release -match 'JAVA_VERSION="(\d+)\.' -and [int]$Matches[1] -ge 17 -and [int]$Matches[1] -le 24) { $JavaHome = $candidate; break }
    }
}
if (!$JavaHome -or !(Test-Path -LiteralPath (Join-Path $JavaHome 'bin\javac.exe'))) { throw 'JDK 17-24 is required. Pass -JavaHome with its installation directory.' }
$gradle = Join-Path $projectRoot 'tools\gradle-8.14.3\bin\gradle.bat'
if (!(Test-Path -LiteralPath $gradle)) { $gradle = Join-Path $projectRoot 'gradlew.bat' }
$tasks = @()
if ($Clean) {
    $buildTarget = [IO.Path]::GetFullPath((Join-Path $projectRoot 'build'))
    $allowedRoot = [IO.Path]::GetFullPath($projectRoot).TrimEnd('\','/') + [IO.Path]::DirectorySeparatorChar
    if (!$buildTarget.StartsWith($allowedRoot, [StringComparison]::OrdinalIgnoreCase)) { throw 'Build directory is outside this project.' }
    if (Test-Path -LiteralPath $buildTarget) {
        $resolvedTarget = (Resolve-Path -LiteralPath $buildTarget).Path
        if (!$resolvedTarget.StartsWith($allowedRoot,[StringComparison]::OrdinalIgnoreCase) -or ((Get-Item -LiteralPath $buildTarget).Attributes -band [IO.FileAttributes]::ReparsePoint)) { throw 'Build directory must be inside this project and cannot be a junction.' }
    }
    $tasks += 'clean'
}
$tasks += 'build'
if ($Zip) { $tasks += 'gameZip' }
if ($Run) { $tasks += 'run' }
$previousJavaHome = $env:JAVA_HOME
try {
    $env:JAVA_HOME = $JavaHome
    & $gradle -p $projectRoot --console=plain --no-daemon @tasks
    if ($LASTEXITCODE -ne 0) { throw 'Build failed. See the Gradle output above.' }
    Write-Output "Game folder: $(Join-Path $projectRoot 'build\dist')"
} finally { $env:JAVA_HOME = $previousJavaHome }
