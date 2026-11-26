param([string]$Java = 'java')
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem
$workspaceRoot = Split-Path -Parent $PSScriptRoot
$originalJar = Join-Path $workspaceRoot 'game\_original files\game.jar'
if (!(Test-Path -LiteralPath $originalJar)) { $originalJar = Join-Path $workspaceRoot 'game\game.jar' }
$cfr = Join-Path $PSScriptRoot 'tools\cfr-0.152.jar'
if (!(Test-Path -LiteralPath $cfr)) {
    Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/benf/cfr/0.152/cfr-0.152.jar' -OutFile $cfr
}
if ((Get-FileHash -LiteralPath $cfr -Algorithm SHA256).Hash.ToLowerInvariant() -ne 'f686e8f3ded377d7bc87d216a90e9e9512df4156e75b06c655a16648ae8765b2') { throw 'CFR checksum mismatch.' }
$classes = @(
    'aoc/kingdoms/lukasz/jakowski/AI/AI_PeaceTreaty',
    'aoc/kingdoms/lukasz/jakowski/AI/Build/AI_Build',
    'aoc/kingdoms/lukasz/jakowski/AI/Diplomacy/AI_RelationsDamage',
    'aoc/kingdoms/lukasz/jakowski/CharactersManager'
)
$archive = [System.IO.Compression.ZipFile]::OpenRead($originalJar)
try {
    foreach ($className in $classes) {
        $inputPath = Join-Path $PSScriptRoot ('alternatives/input/' + $className + '.class')
        $sourcePath = Join-Path $PSScriptRoot ('src/' + $className + '.java')
        $backupPath = Join-Path $PSScriptRoot ('alternatives/vineflower/' + $className + '.java')
        New-Item -ItemType Directory -Path (Split-Path -Parent $inputPath), (Split-Path -Parent $backupPath) -Force | Out-Null
        if (!(Test-Path -LiteralPath $backupPath)) { Copy-Item -LiteralPath $sourcePath -Destination $backupPath }
        [System.IO.Compression.ZipFileExtensions]::ExtractToFile($archive.GetEntry($className + '.class'), $inputPath, $true)
        $alternativeRoot = Join-Path $PSScriptRoot 'alternatives/cfr'
        $logPath = Join-Path $PSScriptRoot ('logs/repair-' + ($className -split '/')[-1] + '-cfr.log')
        & $Java -Xmx1G -jar $cfr $inputPath --outputdir $alternativeRoot --silent true --extraclasspath ($originalJar + ';' + (Join-Path $workspaceRoot 'game\jre\lib\rt.jar')) *> $logPath
        if ($LASTEXITCODE -ne 0) { throw "CFR failed for $className" }
        $alternativePath = Join-Path $alternativeRoot ($className + '.java')
        if (!(Test-Path -LiteralPath $alternativePath)) { throw "CFR did not produce $className" }
        if ([regex]::IsMatch([IO.File]::ReadAllText($alternativePath), 'Unable to fully structure|This method has failed to decompile|\*\* GOTO')) { throw "CFR produced incomplete source for $className" }
        Copy-Item -LiteralPath $alternativePath -Destination $sourcePath
        Write-Output "Selected CFR source for $className"
    }
} finally { $archive.Dispose() }

# JVM int constants are shared with booleans; these six local values must be ints.
foreach ($fix in @(
    @{ Path = 'aoc/kingdoms/lukasz/menusInGame/RecruitArmy/InGame_RecruitArmy_NewArmy.java'; From = 'int var27 = false;'; To = 'int var27 = 0;' },
    @{ Path = 'aoc/kingdoms/lukasz/menus/NewGame/NewGameCiv.java'; From = 'int var61 = false;'; To = 'int var61 = 0;' },
    @{ Path = 'aoc/kingdoms/lukasz/menusInGame/Civ/InGame_Civ.java'; From = 'int var103 = false;'; To = 'int var103 = 0;' },
    @{ Path = 'aoc/kingdoms/lukasz/menusInGame/Diplomacy/InGame_RivalsList.java'; From = 'int var30 = false;'; To = 'int var30 = 0;' },
    @{ Path = 'aoc/kingdoms/lukasz/menusInGame/RecruitArmy/InGame_RecruitArmy.java'; From = 'int var20 = false;'; To = 'int var20 = 0;' },
    @{ Path = 'aoc/kingdoms/lukasz/menusScenarioEditor/ScenarioArmies_List.java'; From = 'int var23 = false;'; To = 'int var23 = 0;' }
)) {
    $targetPath = Join-Path $PSScriptRoot ('src/' + $fix.Path)
    $backupPath = Join-Path $PSScriptRoot ('alternatives/vineflower/' + $fix.Path)
    if (!(Test-Path -LiteralPath $backupPath)) {
        New-Item -ItemType Directory -Path (Split-Path -Parent $backupPath) -Force | Out-Null
        Copy-Item -LiteralPath $targetPath -Destination $backupPath
    }
    $text = [IO.File]::ReadAllText($backupPath).Replace($fix.From, $fix.To)
    [IO.File]::WriteAllText($targetPath, $text, [Text.UTF8Encoding]::new($false))
}

# The original exception table handles java/lang/Exception before the subclass
# GdxRuntimeException over the same bytecode range, making that handler unreachable.
$loadManagerPath = Join-Path $PSScriptRoot 'src/aoc/kingdoms/lukasz/jakowski/SaveLoad/LoadManager.java'
$loadManagerBackup = Join-Path $PSScriptRoot 'alternatives/before-type-fixes/aoc/kingdoms/lukasz/jakowski/SaveLoad/LoadManager.java'
if (!(Test-Path -LiteralPath $loadManagerBackup)) {
    New-Item -ItemType Directory -Path (Split-Path -Parent $loadManagerBackup) -Force | Out-Null
    Copy-Item -LiteralPath $loadManagerPath -Destination $loadManagerBackup
}
$text = [IO.File]::ReadAllText($loadManagerPath)
$text = [regex]::Replace($text, '\} catch \(Exception var7\) \{\s*\} catch \(GdxRuntimeException var8\) \{\s*CFG\.LOG\(var8\);\s*\}', "} catch (Exception var7) {`n      }")
[IO.File]::WriteAllText($loadManagerPath, $text, [Text.UTF8Encoding]::new($false))

# Restore the erased generic element type for JsonValue iteration.
foreach ($fix in @(
    @{ Path = 'aoc/kingdoms/lukasz/jakowski/CharactersManager.java'; From = 'ArrayList tempArrayData = json.fromJson'; To = 'ArrayList<JsonValue> tempArrayData = json.fromJson' },
    @{ Path = 'aoc/kingdoms/lukasz/menusMapEditor/EditorMap_GenerateCivTemplates.java'; From = 'for (JsonValue jValue : json.fromJson(ArrayList.class, fileList))'; To = 'for (JsonValue jValue : (ArrayList<JsonValue>) json.fromJson(ArrayList.class, fileList))' }
)) {
    $targetPath = Join-Path $PSScriptRoot ('src/' + $fix.Path)
    $backupPath = Join-Path $PSScriptRoot ('alternatives/before-type-fixes/' + $fix.Path)
    if (!(Test-Path -LiteralPath $backupPath)) {
        New-Item -ItemType Directory -Path (Split-Path -Parent $backupPath) -Force | Out-Null
        Copy-Item -LiteralPath $targetPath -Destination $backupPath
    }
    $text = [IO.File]::ReadAllText($targetPath).Replace($fix.From, $fix.To)
    [IO.File]::WriteAllText($targetPath, $text, [Text.UTF8Encoding]::new($false))
}
