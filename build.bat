@echo off
setlocal EnableDelayedExpansion

rem Clean up the build directory quietly
if exist bin rmdir /s /q bin
mkdir bin

rem Build a variable containing all .java files (with quotes)
set SOURCES=
for /r %%f in (*.java) do (
    set SOURCES=!SOURCES! "%%f"
)

rem Optionally, remove these echo commands if you don't want to see any messages
rem echo Compiling:
rem echo !SOURCES!

rem Call javac with all file paths and redirect output to nul
javac -d bin !SOURCES! >nul 2>&1

if errorlevel 1 (
    rem You may want to display an error if compilation fails
    echo Compilation failed.
    exit /b 1
)

rem Run the program silently (redirecting output)
java -cp bin LibraryManagementSystem >nul 2>&1
