@echo off
chcp 65001 > nul
setlocal
cd /d %~dp0
if not exist bin mkdir bin
echo [PRACTICE build] 컴파일 시작...
dir /s /b src\*.java       > sources.txt
dir /s /b provided\*.java >> sources.txt
javac -encoding UTF-8 --release 17 -d bin -cp "lib\*" @sources.txt
set RC=%ERRORLEVEL%
del sources.txt
if %RC% NEQ 0 ( echo. & echo [PRACTICE build] X 컴파일 실패 (코드 %RC%). & exit /b %RC% )
echo [PRACTICE build] O 컴파일 성공 -^> bin\
endlocal
