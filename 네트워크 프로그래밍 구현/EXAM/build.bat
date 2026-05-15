@echo off
chcp 65001 > nul
setlocal

cd /d %~dp0

if not exist bin mkdir bin

echo [build] 컴파일 시작...
echo [build] 소스 파일 목록 수집 중... (src + provided)

dir /s /b src\*.java       > sources.txt
dir /s /b provided\*.java >> sources.txt

javac -encoding UTF-8 --release 17 -d bin -cp "lib\*" @sources.txt
set RC=%ERRORLEVEL%

del sources.txt

if %RC% NEQ 0 (
    echo.
    echo [build] X 컴파일 실패 (코드 %RC%).
    exit /b %RC%
)

echo [build] O 컴파일 성공 -^> bin\
endlocal
