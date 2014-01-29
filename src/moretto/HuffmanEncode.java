/**
 * @(#)HuffmanEncode.java	0.1 15/11/05 Thiago Galves Moretto
 * 
 * Classe de compactação, elabora a compactação através de fluxos de entradas.
 * Veja mais detalhes na classe HuffmanUtils para mais detalhes dos algoritmos.
 *
 * @author Thiago Moretto
 * @version 0.1
 * @since 22/09/2005
 * @see Huffman
 */

package moretto;

import java.io.*;

/**
 *
 * Classe que implementa a compactação pelo método de Huffman.
 * Ela extende e implementa um Thread para que o processo de compactação
 * não influêncie na inoperocionalização do restante do software.
 *
 * @author Thiago Galves Moretto
 * @version 0.1
 * @since 23-09-20045
 */
public class HuffmanEncode extends Huffman
{    
    private int array[]							= null;
    private ProcessListener processListener		= null;
    private String file;
    
    /**
     * Construtor 
     * 
     * @param in Stream (fluxo) de entrada.
     * @param out Stream (fluxo) de saida.
     */
    public HuffmanEncode(InputStream in, OutputStream out) {
    	super(in, out);
    }

    /**
     * Recebe um objeto que implementa a interface ProcessListener, destina
     * a criar um suporte para que a linha de execução da compactação/descompactação
     * de um arquivo avise a quem chamou.
     * 
     * @param processListener Objeto que implementa o ProcessListener.
     */
    public void setListener (ProcessListener processListener) {
    	this.processListener = processListener;
    }
    
    /**
     * Seta o nome do arquivo de entrada, o para compactar.
     * 
     * @param fileName Nome do arquivo de entrada.
     */
    public void setInputfilename(String fileName) {
    	this.file = fileName;
    }

    /**
     * Executa a operação de descompactação do arquivo.
     *
     * @see Huffman
     */
    public synchronized void compress() 
    {
    	int count;
    	
        try {
        	array = analizeFromStream();
        	
        	try {
        		buildHuffmanTableWithBitLength (createHuffmanTreeFromArray(array).root); 
        	} catch (java.util.NoSuchElementException e) {
        		  processListener.finish(false);
                  processListener.reporterror("Arquivo vazio ou sem permissão para leitura");
                  return;
        	}
        	
        	//
        	// header
        	//
            String header = new String();
            header += "|";
            char t;
            int total = 0;
            for (int i=0; i<array.length; i++) {
                if (array[i] > 0) {
                	total ++;
                    t = (char) i;
                    header += new String(t + "*" + array[i] + "@");
                }
            }
            header += "-|" + (bitLength%8);
            
            OutputStream out = getOutputStream();
            for (int i=0; i<header.length();i++ ) {
                out.write((char ) header.charAt(i));
            }
            
            
            // -- Auxiliars
            String carry = new String();
            byte output [];
            int len = 0;

            /**
             * com NIO
             * 
             * buffer: 256 prioridade maxima
             * 
             * algoritmo: 469 ms
             * finalização: 7813 ms
             * 
             * 1mb - 2
             * finalização: 13625 ms
             *
            StringBuffer bitMap = new StringBuffer();
            FileInputStream in	= new FileInputStream(file);
            
            FileChannel fc = in.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate( getBuffer() ); 
            
            byte idx;
            while (fc.read(buffer) != -1)
            {
            	bitMap.delete(0, bitMap.length());
                bitMap.append(rest);
                
                for (int i=0; i < buffer.limit(); i++) {
                	idx = buffer.get(i);
                	bitMap.append(huffTable[((idx < 0) ? (idx + 256) : idx)]);
                }

                len = bitMap.length();
                rest = bitMap.substring(len - (len % 8), len);
                
                output = new byte[(len/8)];
                String2Bytes( bitMap.substring(0, len - (len % 8)) , output );
                out.write(output);
                
            	buffer.clear();
            }
            
             *
             *
             *
             */

            
            /**
             * Sem NIO
             * 
             * buffer: 256 prioridade maxima
             * 
             * algoritmo: 438 ms
             * finalização: 7563 ms 
             * 
             * finalização: 12328 ms
             */
            StringBuffer bitMap = new StringBuffer();
            FileInputStream in	= new FileInputStream(file);
            
            // -- Writing file
            byte[] buf = new byte[getBuffer()];
            
            // -- Writing offset
            for(int i=0;i < 8 - (bitLength%8); i++) carry+="0";
            
            while ((count = in.read(buf)) != -1) 
            {
                bitMap.delete(0, bitMap.length());
                bitMap.append(carry);
                
                for (int i=0; i < count; i++) {
                	bitMap.append(huffTable[((buf[i] < 0) ? (buf[i] + 256) : buf[i])]);
                }

                len = bitMap.length();
                carry = bitMap.substring(len - (len % 8), len);
                
                output = new byte[(len/8)];
                String2Bytes( bitMap.substring(0, len - (len % 8)) , output );
                out.write(output);
            }
            /**
             *
             */


            out.close();
            in.close();
            
            processListener.finish(true);
        } 
        catch (EOFException e) 
        {
            processListener.finish(false);
            processListener.reporterror(e.getMessage());
        } 
        catch (Exception e)
        {
            processListener.finish(false);
            processListener.reporterror(e.getMessage());
        }
    }
}       