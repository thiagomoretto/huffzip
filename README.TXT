
Huffzip 0.2 README
Revis�o: 28 de Novembro de 2005

Compactador e Descompactador de arquivos utilizando t�cnicas criados por David Huffman.
Sofware experimental escrito em Java.

Este � um SOFTWARE LIVRE.

H� um artigo t�cnico/ci�ntifico, esta na pasta ./pdf/

1. Informa��es de desenvolvimento:
----------------------------------

          Autor: Thiago Galves Moretto
         E-mail: thiago@moretto.eng.br
Tempo investido: 15 horas (Apenas a vers�o 0.2 exceto Artigo)
     Plataforma: J2SE 1.4.2 (java.sun.com) ou superior.
Funcionalidades: Compacta��o e descompacta��o de arquivos utilizando o m�todo de Huffman.
    Ferramentas: Eclipse IDE 4.1 (www.eclipse.org), Cafeteira BLACK&DECKER
	Li�ensa: GPL (General Public License)

2. Execu��o
-----------

� necess�rio no m�nimo ter uma JVM com a vers�o minima 1.4.2, o software n�o foi testando com vers�es anteriores a esta, este pacote j� cont�m o arquivo de distribui��o (JAR), na pasta dist, h� dois scripts para facilitar a execuc�o:

No windows:
c:\Huffzip>run

No Linux:
$: ./run

ATEN��O: Estes dois scripts executando o huffzip vers�o com parte gr�fica, por�m o ele vem com uma vers�o de linha de comando simples,
utilize os scripts huffzip.bat (para Windows) e huffzip (para o Linux). Voc� tamb�m pode instalar no seu linux para que fique
visivel em todas as pastas, utilize o install.sh (Beta) para facilitar a instala��o (apenas para a vers�o linha de comando).

Se no caso os scripts n�o servirem voc� pode chamar a JVM direto.
Tanto no Linux quanto no Windows:

java -jar Huffzip-0.2.jar (implicitando que o arquivo JAR esteja no diret�rio corrente).

3. Compila��o e gera��o do Javadoc com ANT
------------------------------------------

O pacote cont�m um arquivo build.xml com tarefas prontas, voc� tem que ter o Ant instalado (ant.apache.org).

Algumas tarefas:
$ ant build
Compila (compile) e constr�i o pacote (dist) (JAR).

$ ant javadoc
Gera o Javadoc dos fontes do projeto.

$ ant clean
Limpa os arquivos gerados pela tarefa build.

$ ant dist
Cria apenas o pacote de distribui��o.

$ ant compila
Apenas compila os fontes.

H� uma pasta lib, � para bibliotecas fora de J2SE, coloque-as ali se voc� estiver reimplementando o software e precisar utilizar uma destas bibliotecas, o Ant vai gerenciar o seu classpath na hora da compila��o.

ATEN��O: H� outro arquivo build, � o build-gui.xml, que � para parte gr�fica, para executar ele � da mesma forma que o outro, por�m coloca-se o -f build-gui.xml

4. Compila��o sem o ANT
-----------------------

Para Windows h� o build.bat com duas regras.

c:\projeto>build compile
Compila e cria o pacote de distribui��o.

c:\projeto>build javadoc
Gera o Javadoc dos fontes.

Para o Linux � identico,

$ ./build.sh compile
e
$ ./build.sh javadoc


5. Como utilizar
----------------

Execute o programa, ele tem um pequeno "help" de como proceder.


6. Como desenvolver
-------------------

Utilize o Javadoc.


5. Fontes
---------

pacote.classe

1. Parte gr�fica:
   moretto.GUI

2. Classe de compress�o:
   moretto.HuffmanEncode

3. Classe de descompress�o:
   moretto.HuffmanDecode

4. Fornece m�todos para a HuffmanEncode e HuffmanDecode, classe fundamental:
   moretto.Huffman

5. Interface para integra��o de classes de compress�o de descompres�o com GUI ou CUI.
   moretto.ProcessListener

As outras classes voc� pode ver pelo Javadoc, na pasta doc.

6. Contate o autor
------------------

Thiago Galves Moretto
E-mail: thiago@moretto.eng.br ou thiago.moretto@gmail.com

Universidade Cat�lica Dom Bosco
Engenharia de Computa��o
Novembro 2005


