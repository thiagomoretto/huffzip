
Huffzip 0.2 README
Revisão: 28 de Novembro de 2005

Compactador e Descompactador de arquivos utilizando técnicas criados por David Huffman.
Sofware experimental escrito em Java.

Este é um SOFTWARE LIVRE.

Há um artigo técnico/ciêntifico, esta na pasta ./pdf/

1. Informações de desenvolvimento:
----------------------------------

          Autor: Thiago Galves Moretto
         E-mail: thiago@moretto.eng.br
Tempo investido: 15 horas (Apenas a versão 0.2 exceto Artigo)
     Plataforma: J2SE 1.4.2 (java.sun.com) ou superior.
Funcionalidades: Compactação e descompactação de arquivos utilizando o método de Huffman.
    Ferramentas: Eclipse IDE 4.1 (www.eclipse.org), Cafeteira BLACK&DECKER
	Liçensa: GPL (General Public License)

2. Execução
-----------

É necessário no mínimo ter uma JVM com a versão minima 1.4.2, o software não foi testando com versões anteriores a esta, este pacote já contém o arquivo de distribuição (JAR), na pasta dist, há dois scripts para facilitar a execucão:

No windows:
c:\Huffzip>run

No Linux:
$: ./run

ATENÇÃO: Estes dois scripts executando o huffzip versão com parte gráfica, porém o ele vem com uma versão de linha de comando simples,
utilize os scripts huffzip.bat (para Windows) e huffzip (para o Linux). Você também pode instalar no seu linux para que fique
visivel em todas as pastas, utilize o install.sh (Beta) para facilitar a instalação (apenas para a versão linha de comando).

Se no caso os scripts não servirem você pode chamar a JVM direto.
Tanto no Linux quanto no Windows:

java -jar Huffzip-0.2.jar (implicitando que o arquivo JAR esteja no diretório corrente).

3. Compilação e geração do Javadoc com ANT
------------------------------------------

O pacote contém um arquivo build.xml com tarefas prontas, você tem que ter o Ant instalado (ant.apache.org).

Algumas tarefas:
$ ant build
Compila (compile) e constrói o pacote (dist) (JAR).

$ ant javadoc
Gera o Javadoc dos fontes do projeto.

$ ant clean
Limpa os arquivos gerados pela tarefa build.

$ ant dist
Cria apenas o pacote de distribuição.

$ ant compila
Apenas compila os fontes.

Há uma pasta lib, é para bibliotecas fora de J2SE, coloque-as ali se você estiver reimplementando o software e precisar utilizar uma destas bibliotecas, o Ant vai gerenciar o seu classpath na hora da compilação.

ATENÇÃO: Há outro arquivo build, é o build-gui.xml, que é para parte gráfica, para executar ele é da mesma forma que o outro, porém coloca-se o -f build-gui.xml

4. Compilação sem o ANT
-----------------------

Para Windows há o build.bat com duas regras.

c:\projeto>build compile
Compila e cria o pacote de distribuição.

c:\projeto>build javadoc
Gera o Javadoc dos fontes.

Para o Linux é identico,

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

1. Parte gráfica:
   moretto.GUI

2. Classe de compressão:
   moretto.HuffmanEncode

3. Classe de descompressão:
   moretto.HuffmanDecode

4. Fornece métodos para a HuffmanEncode e HuffmanDecode, classe fundamental:
   moretto.Huffman

5. Interface para integração de classes de compressão de descompresão com GUI ou CUI.
   moretto.ProcessListener

As outras classes você pode ver pelo Javadoc, na pasta doc.

6. Contate o autor
------------------

Thiago Galves Moretto
E-mail: thiago@moretto.eng.br ou thiago.moretto@gmail.com

Universidade Católica Dom Bosco
Engenharia de Computação
Novembro 2005


