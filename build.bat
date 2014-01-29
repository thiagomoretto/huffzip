@echo off

REM
REM Auxiliar para compilação, criação de documentos Javadoc para
REM o Huffzip versão 0.2
REM
REM Author Thiago Galves Moretto <thiago@moretto.eng.br>
REM

set CMD_LINE_ARG=%1
set MANIFEST=inf/MANIFEST.MF
set DISTFILE=dist/Huffzip-0.2.jar

if ""%1"" ==  """" goto help
if ""%1"" ==  ""compile"" goto build
if ""%1"" ==  ""javadoc"" goto javadoc
goto help

:build
javac src/moretto/GUI.java -sourcepath src/ -d bin
echo task compile complete
goto package

:javadoc
javadoc -subpackages moretto -sourcepath src/ -d doc
echo task javadoc
goto end

:package
if not exist %MANIFEST% goto createManifest
jar cvfm %DISTFILE% %MANIFEST% -C bin .
echo Pacote criado em %DISTFILE%
echo task package complete
goto end

:createManifest
echo Manifest-Version: 1.0>> %MANIFEST%
echo Built-By: Thiago Moretto>> %MANIFEST%
echo Main-class: moretto.GUI>> %MANIFEST%
echo Ant-Version: Apache Ant 1.6.5>> %MANIFEST%
goto package

:help
echo Huffzip 0.2 arquivo auxiliar
echo build [parametro]
echo Parametros:
echo    compile - Compila o projeto
echo    javadoc - Gera os documentos Javadoc
goto end

:end
