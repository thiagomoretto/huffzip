@echo off

REM
REM Huffzip Command Line
REM
REM Thiago Galves Moretto
REM 28 de Novembro de 2005
REM

set DISTPATH=C:\ED\Huffpack\dist
set JAR=huffzip-commandline-0.2.jar
set COMMANDLINE=%DISTPATH%\%JAR%

if not exist %COMMANDLINE% goto error
goto run

:error
echo Pacote JAR do huffzip linha de comando nao existe.
goto end

:run
java -jar %COMMANDLINE% %1 %2 %3
goto end

:end