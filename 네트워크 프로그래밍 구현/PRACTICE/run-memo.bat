@echo off
chcp 65001 > nul
cd /d %~dp0
start "Memo Management" javaw -cp "bin;lib\*" memo.MemoManagementUI
