/**
 * @(#)HuffmanDecode.java	0.1 15/11/05 Thiago Galves Moretto
 * 
 * @author Thiago Moretto
 * @version 0.1
 * @since 22/09/2005
 *
 * Classe de decompactação
 */

package moretto;

import java.io.*;

public class HuffmanDecode extends Huffman
{
	private DataInputStream dataInput 		= null;
    private ProcessListener processListener 	= null;
    private String file;
  
    /**
     * Fluxo de entrada para o arquivo de entrada compactado e escreve a saida no
     * fluxo de saida.
     * 
     * @param in Stream (fluxo) de entrada.
     * @param out Stream (fluxo) de saida.
     */
    public HuffmanDecode(InputStream in, OutputStream out) {
    	super(in, out);
    }


    /**
     * Seta o nome do arquivo de entrada, o compactado.
     * 
     * @param fileName Nome do arquivo de entrada.
     */
    public void setInputfilename(String fileName) {
    	this.file = fileName;
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
     * Executa a operação de descompactação do arquivo.
     *
     * @see Huffman
     */
    public synchronized void decompress()
    {
        int headerbytes = 0;
        
        try {
            int d, a = 0;
            boolean start = false;
            String header = new String();

            // -- Open inputStream
            dataInput = new DataInputStream(new FileInputStream(file));
            
            // -- Get Header
            while ((d = dataInput.readUnsignedByte()) != -1) 
            {
                headerbytes ++;
                
                if (d == Resources.START_DELIMITER.charAt(0) && a == '-' && start) {
                    break; // -- MARK #01 end of header 
                }

                if (d != Resources.START_DELIMITER.charAt(0) && !start) {
                    processListener.finish(false);
                    processListener.reporterror("Arquivo não válido\nCertifique-se que este é um arquivo compactado pelo Huffzip");
                    return;
                }
                if (d == Resources.START_DELIMITER.charAt(0) && !start) {
                    start = true;
                }
                else {
                    if (start) {
                        header += (char) d;
                    }
                }
                
                a = d;
              }
            
            // -- Lê mais um caracter, que é o offset
            //	  O funcionamento do mesmo é descrito no artigo junto à este aplicativo
            int offset = dataInput.read();
            offset = 8 - Integer.parseInt(""+ (char) offset);
            
            header = header.substring(0, header.length()-1);
            
            // -- Parse Header
            int j;
            int[] array = new int[ Resources.ACCEPTED_CHARS ];
            String num = new String();
            for (int i=0; i < header.length(); i++) 
            {
                num = "0";
                j = i + 2;
                array [ header.charAt(i) ] = 0;
                while ( true ) {
                    if ( header.charAt(j) != Resources.GROUP_DIVISOR.charAt(0) ) {
                       num += (char) header.charAt(j);
                       j++;
                   } else break;
                }
                array [ header.charAt(i) ] += new Integer(num).intValue();
                i = j;
            }

            // -- Mark: close dataInput
            dataInput.close();
            dataInput = null;
            
            // -- MARK: Open OutputStream
            OutputStream out = getOutputStream();

            // -- MARK: call createHuffmanTreeFromArray
            HuffmanDigitalTree hf;
            try {
            	hf = createHuffmanTreeFromArray(array);
	            buildHuffmanTable (hf.root);
	    	} catch (java.util.NoSuchElementException e) {
	    		processListener.finish(false);
	    		processListener.reporterror("Arquivo vazio ou sem permissão para leitura");
	    		return ;
	    	}

            
            // -- MARK: descompress
            int count;
            byte[] data = new byte [ getBuffer() ];
            InputStream in = new FileInputStream(file);
            
            // -- MARK: substituição
            int base = 128;
            int r, s;
            Node it = hf.root;

            // -- Start benchmark
            in.skip(headerbytes + 1); // Ignorando cabeçalho
            
            while ((count = in.read(data)) != -1)
            {
            	for (int i=0; i < count; i++) 
            	{
            		r = (data [i] < 0) ? data[i] + 256 : data[i];

            		for (int k=0; k <= 7; k++) 
            		{ 
            			if (offset == 0) // offset 
            			{
	            			if (it.Right == null && it.Left == null) {
	            				out.write((byte) it.symbol);
	               				it = hf.root; // volta para a raiz
	            			}
	            			
	            			s = (r & (base >> k)) == (base >> k) ? 1 : 0;
	            			
	            			if (s == 1) {
	            				it = it.Right;
	            			}
	            			else if (s == 0) {
	            				it = it.Left;
	            			}
            			} else  {
            				offset --;
            			} // end if 3
                	} // end for 2
            	} // end for 1
            } // end while

            // fim de leitura, se a descida estiver em uma folha
            if (it instanceof Node) { // proteção para único nó
				if (it.Right == null && it.Left == null) {
					out.write((byte) it.symbol);
	   				it = hf.root; // volta para a raiz
				}
            }
            
            in.close();
            out.close();
            
            processListener.finish(true);
        }
        catch (IOException e) 
        {
        	processListener.reporterror("IOException " + e.getStackTrace().toString());
        	processListener.finish(false);
        }
        catch (Exception e) 
        {
        	processListener.reporterror("Exception " + e.getStackTrace().toString());
        	processListener.finish(false);
        }
    }
}