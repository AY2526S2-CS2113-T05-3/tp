@echo off
setlocal enableextensions
pushd %~dp0

cd ..
call gradlew clean shadowJar

cd build\libs
for /f "tokens=*" %%a in (
    'dir /b *.jar'
) do (
    set jarloc=%%a
)

java -jar %jarloc% < ..\..\text-ui-test\input.txt > ..\..\text-ui-test\ACTUAL.TXT

cd ..\..\text-ui-test

powershell -Command "$actual = (Get-Content ACTUAL.TXT) -replace 'v2\.0 \| .*','v2.0 | <DATE_PLACEHOLDER>' -replace 'Daily quote:\".*\"','Daily quote:\"<QUOTE_PLACEHOLDER>\"' -replace '(Workouts logged\s+: )\d+','${1}<NUM>' -replace '(Workouts done\s+: )\d+ / \d+','${1}<NUM> / <NUM>' -replace '(Total exercises\s+: )\d+','${1}<NUM>' -replace '\s+$',''; $expected = (Get-Content EXPECTED.TXT) -replace '\s+$',''; if (Compare-Object $actual $expected) { Write-Host 'Test failed!'; exit 1 } else { Write-Host 'Test passed!'; exit 0 }"