@echo off
chcp 65001 > nul
cd /d %~dp0
start "Book Management" javaw -cp "bin;lib\*" book.BookManagementUI
