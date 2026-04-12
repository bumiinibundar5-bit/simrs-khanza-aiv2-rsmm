@echo off
cd /d D:\SIMRS2026\simrs-khanza-aiv2-rsmm

echo ==========================
echo PUSH REPO RSMM
echo ==========================

git add .

git commit -m "auto update rsmm"

git push rsmm main

echo ==========================
echo SELESAI PUSH RSMM
echo ==========================
pause