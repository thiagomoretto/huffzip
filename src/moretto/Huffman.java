/*
 * @(#)Huffman.java	0.1 15/11/05 Thiago Galves Moretto
 *
 *  Copyright (c) 2005  Thiago Galves Moretto
 *  All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or
 *  without modification, are permitted provided that the following
 *  conditions are met:
 * 
 *   * Redistributions of source code must retain the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer.
 * 
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 * 
 *   * Neither the name of Radwin.org nor the names of its
 *     contributors may be used to endorse or promote products
 *     derived from this software without specific prior written
 *     permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 *  CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 *  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 *  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */

package moretto;

import java.io.*;
import moretto.ed.*;

/**
 * Métodos utilitários para auxílio na criação de uma aplicação de
 * compactação e descompactação de dados utilizando a técnica de 
 * Huffman.
 * 
 * @author Thiago Galves Moretto
 * @since 15/11/2005
 * @version 0.2 15/11/2005
 */
public class Huffman
{
	private InputStream inputStream		= null;
	private OutputStream outputStream	= null;
	
	/**
	 * Tabela Hash dos códigos de bits dos caracteres. 
	 * 
	 */
	public static String[] huffTable = new String[ Resources.ACCEPTED_CHARS ];
	
	private int buffer = Resources.DEFAULT_BUFFER;
	private int array[] ;
	
	/**
	 * Número de bits do que será o novo arquivo.
	 * 
	 */
	public int bitLength = 0;
	
	/**
	 * InputStream do arquivo de entrada e OutputStream do arquivo de saída.
	 * 
	 * @param in um stream de entrada, normalmente um InputStream. 
	 * @param out um stream de saida de dados, um OutputStream.
	 */
	public Huffman (InputStream in, OutputStream out) {
		inputStream = in;
		outputStream = out;
	}
	
	/**
	 * Este método tem a finalidade de apartir de um <i>stream</i> de entrada (leia-se fluxo de entradas)
	 * retornar um array (hash) com 256 posições e suas respectivas quantidade de caracteres.
	 * 
	 * @return Retorna um hash de 256 posições contendo a quantidade de aparições de cada caracter.
	 * @throws IOException
	 */
	public synchronized int[] analizeFromStream(InputStream in) throws IOException
	{
		int c = 0;
		array = new int[256];
		byte data[] = new byte[buffer];
		
		for (int i=0; i<array.length; i++) array[i] = 0;
		
		while ((c = in.read(data)) != -1) {
			for (int i=0; i<c; i++) {
				array[ ((data[i] < 0) ? (data[i] + 256) : data[i]) ]++;
			}
		}
		
		return array;
	}
	
	/**
	 * Este método tem a finalidade de apartir de um <i>stream</i> de entrada (leia-se fluxo de entradas)
	 * retornar um array (hash) com 256 posições e suas respectivas quantidade de caracteres.
	 * 
	 * @return Retorna um hash de 256 posições contendo a quantidade de aparições de cada caracter.
	 * @throws IOException
	 */
	public synchronized int[] analizeFromStream () throws IOException {
		return analizeFromStream(inputStream);
	}
	
	/**
	 * Cria uma árvore digital a partir de um vetor de frequências.
	 * 
	 * @return HuffmanDigitalTree Árvore digital de huffman.
	 * @param array array com os caracteres e suas frequências.
	 */
	public synchronized HuffmanDigitalTree createHuffmanTreeFromArray(int[] array) throws java.util.NoSuchElementException {
		HuffmanDigitalTree tree1, tree2, newtree;
		HeapCrescente heap = new HeapCrescente();
		
		for (int i=0; i<array.length; i++) {
			if (array[i] > 0) {
				heap.insert(new HuffmanDigitalTree(array[i], i));
			}
		}
		
		if (heap.size() == 0) {
			throw new java.util.NoSuchElementException();
		}
		
		while (heap.size() > 1) {
			tree1 = (HuffmanDigitalTree) heap.remove();
			tree2 = (HuffmanDigitalTree) heap.remove();
			newtree = new HuffmanDigitalTree(tree1.root.count + tree2.root.count);
			newtree.root.Right	= tree1.root;
			newtree.root.Left 	= tree2.root;
			heap.insert(newtree);
		}
		
		return (HuffmanDigitalTree) heap.remove();
	}
	
	/**
	 * Cria a árvore digital de Huffman a partir de um InputStream.
	 * 
	 * @return HuffmanDigitalTree Árvore digital de huffman.
	 * @param in um fluxo de entrada.
	 */
	public synchronized HuffmanDigitalTree createHuffmanTreeFromStream(InputStream in) throws IOException
	{
		return createHuffmanTreeFromArray(analizeFromStream(in));
	}
	
	/**
	 * A partir da árvore ele constrói recursivamente a tabela dos caracteres com seus respectivos
	 * códigos binários, após isso basta fazer apenas a conversão dos caracteres para o código binário
	 * e realizar a gravação no fluxo de saída.
	 * 
	 * @param N Raiz da árvore.
	 * @param code Código inicial, normalmente vazio, é util apenas a recursividade, use buildHuffmanTable( Node N )
	 */
	public void buildHuffmanTable ( Node N , String code ) {
        if (N.Left == null && N.Right == null) {
        	if (code.equals("")) code = "0";
            huffTable[((N.symbol < 0) ? (N.symbol + 256) : N.symbol)] = new String( code );
            return;
        }

        if (N.Left != null)
            buildHuffmanTable ( N.Left, code + "0"  );

        if (N.Right != null)
            buildHuffmanTable ( N.Right, code + "1" );
    }
	
	/**
	 * A partir da árvore ele constrói recursivamente a tabela dos caracteres com seus respectivos
	 * códigos binários, após isso basta fazer apenas a conversão dos caracteres para o código binário
	 * e realizar a gravação no fluxo de saída, a diferença deste para o buildHuffmanTable é que este
	 * faz uma conta para determinar o número de bits do novo arquivo.
	 * 
	 * @param N Raiz da árvore.
	 * @param code Código inicial, normalmente vazio, é util apenas a recursividade, use buildHuffmanTable( Node N )
	 */
	public void buildHuffmanTableWithBitLength ( Node N , String code ) {
        if (N.Left == null && N.Right == null) {
        	if (code.equals("")) code = "0";
            huffTable[((N.symbol < 0) ? (N.symbol + 256) : N.symbol)] = new String( code );
            bitLength += code.length() * array[((N.symbol < 0) ? (N.symbol + 256) : N.symbol)];
            return;
        }

        if (N.Left != null)
        	buildHuffmanTableWithBitLength ( N.Left, code + "0"  );

        if (N.Right != null)
        	buildHuffmanTableWithBitLength ( N.Right, code + "1" );
    }
    
	/**
	 * A partir da árvore ele constrói recursivamente a tabela dos caracteres com seus respectivos
	 * códigos binários, após isso basta fazer apenas a conversão dos caracteres para o código binário
	 * e realizar a gravação no fluxo de saída.
	 * 
	 * @param N Raiz da árvore.
	 */
	public void buildHuffmanTable ( Node N ) {
    	buildHuffmanTable( N, "" );
    }
	
	/**
	 * A partir da árvore ele constrói recursivamente a tabela dos caracteres com seus respectivos
	 * códigos binários, após isso basta fazer apenas a conversão dos caracteres para o código binário
	 * e realizar a gravação no fluxo de saída, a diferença deste para o buildHuffmanTable é que este
	 * faz uma conta para determinar o número de bits do novo arquivo.
	 * 
	 * @param N Raiz da árvore.
	 */
	public void buildHuffmanTableWithBitLength ( Node N ) {
		buildHuffmanTableWithBitLength( N, "" );
    }
	
	/**
	 * Retorna o objeto InputStream
	 * 
	 * @return InputStream retorna o objeto InputStream que objeto esta utilizando.
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	
	/**
	 * Seta o objeto InputStream
	 * 
	 * @param in fluxo de entrada do tipo InputStream
	 */
	public void setInputStream(InputStream in) {
		inputStream = in;
	}
	
	/**
	 * Retorna o objeto getOutputStream
	 * 
	 * @return um fluxo de saída.
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * Seta o objeto OutputStream
	 * 
	 * @param out fluxo de saida do tipo OutputStream
	 */
	public void setOutputStream(OutputStream out) {
		outputStream = out;
	}
	
	/**
	 * Seta o buffer do fluxo de entrada e saída.
	 * 
	 * @param buffer Tamanho em bytes do buffer de entrada e saida.
	 */
    public void setBuffer ( int buffer ) {
        this.buffer = buffer;
    }
	
    /**
     * Tamanho do buffer de entrada e saida.
     * 
     * @return retorna o tamanho em bytes do buffer de entrada e saída.
     */
    public int getBuffer() {
    	return buffer;
    }
    
	/**
	 * Auxilio na criaçao de uma String numa cadeia de bytes, utilizada pela String2Bytes
	 * 
	 * @param b 
	 * @param n
	 * @return o byte convertido
	 */
    private byte bitSet(byte b, int n)
    {
        b |= 1 << (n - 1) ;
        return b;
    }
    
    /**
     * Converte uma String e uma cadeia de bytes.
     * 
     * @param s String que deseja a conversão.
     * @param barray O vetor de bytes por referência.
     */
    protected void String2Bytes(String s, byte[] barray)
    {
        int i , j , l = s.length() / 8;
        for (j=0;j<l;j++) {
            for (i=0;i<8;i++) {
                if (s.charAt(i + (j*8)) == '1') 
                	barray[j] = bitSet(barray[j], 8-i);
            }
        }
        
        l = s.length() % 8;
        for (i=0;i<l;i++) {
            if (s.charAt(i + (j*8)) == '1')
            	barray[j] = bitSet(barray[j], 8-i);
        }
    }
}