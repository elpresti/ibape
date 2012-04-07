@echo off
REM STOP.BAT - MicroApache - http://microapache.amadis.sytes.net
ECHO Stopping MicroApache ...
REM Abruptly kill the MicroApache process
REM Use KILLPROC.EXE /? for more information
killproc.exe Apache.exe /all
killproc.exe mysqld.exe /all