@echo off
where pwsh >nul 2>nul
if errorlevel 1 (
    echo PowerShell 7 is required for this shortcut. Use gradlew.bat build with JDK 17-24.
    pause
    exit /b 1
)
pwsh -NoProfile -ExecutionPolicy Bypass -File "%~dp0build.ps1" %*
if errorlevel 1 pause
