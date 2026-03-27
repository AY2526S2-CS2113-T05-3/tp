@echo off
setlocal enableextensions
pushd %~dp0

for /f "tokens=*" %%g in ('where git') do (
    set GITBIN=%%~dpg..\usr\bin
    goto :found_git
)
:found_git

cd ..
set PROJECTROOT=%CD%
call gradlew clean shadowJar

cd text-ui-test

for /f "tokens=*" %%a in ('dir /b "%PROJECTROOT%\build\libs\*.jar"') do (
    set jarloc=%PROJECTROOT%\build\libs\%%a
)

java -jar "%jarloc%" < input.txt > ACTUAL.TXT

if not exist ACTUAL.TXT (
    echo ERROR: ACTUAL.TXT was not created!
    exit /b 1
)

copy EXPECTED.TXT EXPECTED-UNIX.TXT >NUL
"%GITBIN%\dos2unix.exe" EXPECTED-UNIX.TXT ACTUAL.TXT >NUL 2>&1

"%GITBIN%\..\..\\bin\bash.exe" -c "sed -E -e 's/v2.0 | .*/v2.0 | <DATE_PLACEHOLDER>/g' -e 's/Daily quote:\".*\"/Daily quote:\"<QUOTE_PLACEHOLDER>\"/g' -e 's/(Workouts logged[[:space:]]+: )[0-9]+/\1<NUM>/g' -e 's/(Workouts done[[:space:]]+: )[0-9]+ \/ [0-9]+/\1<NUM> \/ <NUM>/g' -e 's/(Total exercises[[:space:]]+: )[0-9]+/\1<NUM>/g' -e 's/[[:space:]]*$//' ACTUAL.TXT | diff EXPECTED-UNIX.TXT -"

if %errorlevel% == 0 (
    echo Test passed!
    exit /b 0
) else (
    echo Test failed!
    exit /b 1
)