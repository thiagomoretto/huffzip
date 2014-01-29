/**
 * @(#)CommandLine.java	0.1 28/11/05 Thiago Galves Moretto
 * 
 * @author Thiago Moretto
 * @version 0.1
 * @since 28/11/2005
 *
 * Versão simples para linha de comando.
 */

package moretto;

import java.io.*;

public class CommandLine implements ProcessListener
{
	private String[] argumentos;
	
	public ProcessListener commandLine;
	
	public CommandLine(String[] args) 
	{
		commandLine = this;
		this.argumentos = args;
		if (args.length > 2) 
		{ 
			if (!new File(args[1]).exists()) {
				exit( "O Arquivo de entrada deve existir" );
			} else {
				if (args[0].equals("-x")) {
					try {
	                		 HuffmanDecode huffmanDecode = new HuffmanDecode(
                    				 	new FileInputStream( argumentos[1] ),
                        				new FileOutputStream(new File( argumentos[2] )));
                    		 
                    		 huffmanDecode.setInputfilename( argumentos[1] );
                    		 huffmanDecode.setListener(commandLine);
                    		 huffmanDecode.setBuffer(512);
                    		 huffmanDecode.decompress();
               		 } catch (FileNotFoundException e) {
               			 exit (e.getMessage());
               		 }
				}
				
				if (args[0].equals("-c")) {
            		try {
                		HuffmanEncode huffmanEncode = new HuffmanEncode(
                    				new FileInputStream(argumentos[1]),
                    				new FileOutputStream(new File(argumentos[2])));
                    	huffmanEncode.setInputfilename( argumentos[1] );
                    	huffmanEncode.setListener(commandLine);
                    	huffmanEncode.setBuffer( 512 );
                    	huffmanEncode.compress();
            		} catch (FileNotFoundException e) {
            			reporterror("Arquivo não encontrado.");
            		}
				}
			}
		} else {
			showHelp ();
		}
	}
	

	private static void showHelp() {
		System.out.println("Huffzip 0.2 - CommandLine program");
		System.out.println("Usage: huffzip [option] [inputfile] [outputfile]");
		System.out.println("Options:");
		System.out.println("	-c		Compress inputfile and print output in outputfile");
		System.out.println("	-x		Decompress inputfile and print output in outputfile");
		System.out.println();
	}
	
	private static void exit(String message) 	{
		System.err.println(message);
		System.exit(0);
	}
	
	public static void main(String args[]) {
		new CommandLine(args);
	}
		
	public void finish(boolean unconditional) { 
		if (!unconditional) {
			exit ("Processo não executando com êxito.");
		}
	}
	public void reporterror(String message) { 
		System.err.println(message);
	}
}
