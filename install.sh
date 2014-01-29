#!/bin/sh

#
# BETA
# Pequeno script para auxiliar a instalação do huffzip versão de linha de comando.
# Thiago Galves Moretto
# <thiago@moretto.eng.br>
#

echo "Qual pasta para instalar, deve estar variavel PATH: "
read path

if [ -d $path ];  then

	echo "#!/bin/sh" > huffzip-install
	echo "java -jar $path/huffzip-commandline-0.2.jar \$1 \$2 \$3" >> huffzip-install

	echo "Copiando arquivos..."
	cp dist/huffzip-commandline-0.2.jar $path 
	cp huffzip-install $path/huffzip
	rm huffzip-install
	chmod +x $path/huffzip

else
	echo "Diretório inválido" > /dev/stderr
fi
