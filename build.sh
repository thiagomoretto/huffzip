#!/bin/sh
#
# Thiago Galves Moretto
# <thiago@moretto.eng.br>
#

MANIFEST1=inf/MANIFEST.MF
MANIFEST2=inf/MANIFEST-CommandLine.MF

DISTGUI=dist/huffzip-0.2.jar
DISTCMD=dist/huffzip-commandline-0.2.jar

if [ -z $1 ]; then
	echo "Huffzip 0.2 arquivo auxiliar"
	echo "Opções:"
	echo "	compile		- Compila os fontes e cria o pacote"
	echo "	javadoc		- Gera os documentos Javadoc"
	echo " "
else
	if [ "$1" == "compile" ]; then
		# Manifesto para moretto.GUI
		if [ ! -e $MANIFEST1 ]; then
			echo Manifest-Version: 1.0>> $MANIFEST1
			echo Built-By: Thiago Moretto>> $MANIFEST1
			echo Main-class: moretto.GUI>> $MANIFEST1
		fi

		# Manifesto para moretto.CommandLine
		if [ ! -e $MANIFEST2 ]; then
			echo Manifest-Version: 1.0>> $MANIFEST2
			echo Built-By: Thiago Moretto>> $MANIFEST2
			echo Main-class: moretto.CommandLine>> $MANIFEST2
		fi

		javac src/moretto/GUI.java -sourcepath src/ -d bin
		echo task compile complete
		
		jar cvfm $DISTGUI $MANIFEST1 -C bin .
		jar cvfm $DISTCMD $MANIFEST2 -C bin .
		echo task package complete
	fi

	if [ "$1" == "javadoc" ]; then
		javadoc -subpackages moretto -sourcepath src/ -d doc
		echo task javadoc
	fi
fi

