@echo off
chcp 65001 > nul
cd /d %~dp0
start "Messenger Client" javaw -cp "bin;lib\*" chat.MessengerClientUI
