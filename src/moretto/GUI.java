/**
 * @(#)GUI.java	0.1 28/11/05 Thiago Galves Moretto
 * 
 * @author Thiago Moretto
 * @version 0.7
 * @since 22/09/2005
 *
 * Classe que descreve as funcionalidades da parte gráfica.
 * 
 * Foi modificada para funcionar com a J2SE 1.4.2, onde anteriormente
 * foi desenvolvida e testa na base da J2SE 1.5.0 em que o int era compativel
 * com o objeto Integer.
 * 
 * Na modificacao, foram mantidos os objetos Integer mas foi utilizando o metodo
 * intValue() para conversao.
 */

package moretto;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI implements ProcessListener
{
	private static GUI GUIInstance = null;
    
    private static JFrame mainFrame 		= null;
    private static JDesktopPane Desktop 	= null;   
    private static JLabel label 		= null;
    private static JRadioButtonMenuItem p[];
    private static JRadioButtonMenuItem b[];
    
    private static String selectedInputFile = "";
    private static String selectedOutputFile = "";
    private boolean processing = false;
    
    private Integer Priority = new Integer(Thread.MIN_PRIORITY);
    private Integer Buffer = new Integer(256);
    
    public GUI ( ) {
        mainFrame = new JFrame("Huffzip");
        mainFrame.setSize(300, 100);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); // Centralizar
        mainFrame.setLocation((d.width - 300) / 2, (d.height - 100) / 2);
                
        JMenu menu = new JMenu("Arquivo");
        JMenu config = new JMenu("Configurações");
        JMenu about = new JMenu("Sobre");
        menu.setMnemonic( 'A' );
        config.setMnemonic ( 'C' );
        about.setMnemonic ( 'S' );
        
        label = new JLabel("   Huffzip 0.2");
        
        //
        // Arquivo
        //
        JMenuItem comp = new JMenuItem("Compactar");
        JMenuItem decomp = new JMenuItem("Descompactar");
        JMenuItem close = new JMenuItem("Fechar");
        
        //
        // Configurações
        //
        JMenu prioridade = new JMenu("Prioridade");
        p = new JRadioButtonMenuItem[ 10 ];
        PriorityItemHandler p_itemHandler = new PriorityItemHandler();
        
        for (int i=0; i<10; i++) {
            p[i] = new JRadioButtonMenuItem("" + (i+1) );
            p[i].addActionListener(p_itemHandler);
            prioridade.add( p[i] );
        }
        p[0].setSelected(true);
        
        
        Double pow;
        BufferItemHandler b_itemHandler = new BufferItemHandler();
        JMenu buffer = new JMenu("Buffer");
        b = new JRadioButtonMenuItem[ 7 ];
        for (int i=0; i<7; i++) {
            pow = new Double(Math.pow(2, (6 + i)));
            b[i] = new JRadioButtonMenuItem("" + (pow.intValue()));
            b[i].addActionListener(b_itemHandler);
            buffer.add(b[i]);
        }
        
        b[2].setSelected(true);
        config.add(prioridade);
        config.add(buffer);
        config.addSeparator();
        
        JMenuItem help = new JMenuItem("Ajuda");
        help.addActionListener(
                new ActionListener() {
                    public void actionPerformed ( ActionEvent event ) {
                        JOptionPane.showMessageDialog(mainFrame, Resources.HELP );
                    }
                }
        );
        config.add(help);
        
        //
        // Sobre
        //
        JMenuItem author = new JMenuItem("Autor");
        JMenuItem howto = new JMenuItem("Como fazer");
        
        about.add(howto);
        about.add(author);
        
        howto.addActionListener(
                new ActionListener() {
                    public void actionPerformed ( ActionEvent event ) {
                        JOptionPane.showMessageDialog(mainFrame, Resources.HOWTO );
                    }
                }
        );
        
        author.addActionListener(
                new ActionListener() {
                    public void actionPerformed ( ActionEvent event ) {
                        JOptionPane.showMessageDialog(mainFrame, Resources.ABOUT );
                    }
                }
        );
        
        close.addActionListener(
                new ActionListener() {
                    public void actionPerformed ( ActionEvent event ) {
                        System.exit(0);
                    }
                }
        );
        
        menu.add(comp);
        menu.add(decomp);
        menu.addSeparator();
        menu.add(close);
        JMenuBar bar = new JMenuBar();
        bar.add(menu); 
        bar.add(config);
        bar.add(about);
        mainFrame.setJMenuBar (bar);
        
        //
        // Desktop pane
        //
        Desktop = new JDesktopPane();
        mainFrame.getContentPane().add(Desktop);
        
        //
        // Internal frame
        //
        comp.addActionListener(
            new ActionListener() {
                public void actionPerformed( ActionEvent event ) {
                    if (processing) {
                         JOptionPane.showMessageDialog(mainFrame, Resources.PROCESS_RUNING);
                         return ;
                    }

                    selectedInputFile = selectFile();
                    
                    if (selectedInputFile.equals("")) {
                        return ;
                    }
                    else {
                        // Arquivo selecionado.
                        selectedOutputFile = selectFile();

                    	synchronized (this) {
	                        new Thread() {
	                        	public void run() {
	                        		setPriority(Priority.intValue());
	                        		try {
		                        		HuffmanEncode huffmanEncode = new HuffmanEncode(
			                        				new FileInputStream(selectedInputFile),
			                        				new FileOutputStream(new File(selectedOutputFile)));
			                        	huffmanEncode.setInputfilename(selectedInputFile);
			                        	huffmanEncode.setListener(GUIInstance);
			                        	huffmanEncode.setBuffer(Buffer.intValue());

			                            label.setText("   Processando "+ selectedInputFile +"...");
			                            processing = true;
			                            
			                        	huffmanEncode.compress();
	                        		} catch (FileNotFoundException e) {
	                        			reporterror("Arquivo não encontrado.");
	                        		}
	                        	}
	                        }.start();
                    	}
                    }
                }
            }
        );
        
        decomp.addActionListener(
            new ActionListener() {
                public void actionPerformed( ActionEvent event ) {
                    if (processing) {
                         JOptionPane.showMessageDialog(mainFrame, Resources.PROCESS_RUNING);
                         return ;
                    }
                    
                    selectedInputFile = selectFile();
                    if (selectedInputFile.equals("")) {
                         return;
                    } else {
                        try {
                            selectedOutputFile = selectFile();
                        	synchronized (this) {
	                             new Thread(){
	                            	 public void run() {
	                            		 setPriority(Priority.intValue());
	                            		 try {
		                            		 HuffmanDecode huffmanDecode = new HuffmanDecode(
		                            				 	new FileInputStream(selectedInputFile),
				                        				new FileOutputStream(new File(selectedOutputFile)));
		                            		 
		                            		 huffmanDecode.setInputfilename(selectedInputFile);
		                            		 huffmanDecode.setListener(GUIInstance);
		                            		 huffmanDecode.setBuffer(Buffer.intValue());

		                            		 label.setText("   Processando "+ selectedInputFile +"...");
		                                     processing = true;
		                            		 
		                            		 huffmanDecode.decompress();
	                            		 } catch (FileNotFoundException e) {
	                            			 
	                            		 }
	                            	 }
	                             }.start();
                        	}
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(mainFrame, Resources.INVALID_FILE);
                            return;
                        }
                    }
                }
            }
        );
        
        try {
        	// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        	e.printStackTrace();
        }

        mainFrame.getContentPane().add(label);
        mainFrame.setVisible(true);
    }
    
    public void finish( boolean success ) 
    {
        if (success) 
            JOptionPane.showMessageDialog(mainFrame, Resources.PROCESSED_OK );
        else
            JOptionPane.showMessageDialog(mainFrame, Resources.PROCESSED_FAIL );
        
        label.setText("   Processado "+ selectedInputFile);
        processing = false;
    }
    
    public void reporterror( String error ) {
        if ((error instanceof String) && error.length() > 0)
        {
            JOptionPane.showMessageDialog(mainFrame, "Erro: \n" + error);
        }
    }
    
    private static String selectFile ( ) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        chooser.setCurrentDirectory( new File(selectedInputFile) );
        
        if (chooser.showOpenDialog( mainFrame ) == JFileChooser.CANCEL_OPTION)
            return "";
        
        File file = chooser.getSelectedFile();
        return file.getAbsolutePath();
    }
        
    private class PriorityItemHandler implements ActionListener {
        public void actionPerformed( ActionEvent e ) {
            for(int i=0;i<10;i++) {
                if (e.getSource() == p[i]) {
                    Priority = new Integer(p[i].getText());
                    return;
                } else {
                    p[i].setSelected(false);
                }
            }
        } 
    }
    
    private class BufferItemHandler implements ActionListener {
        public void actionPerformed( ActionEvent e ) {
            for (int i=0; i<7; i++) {
                if (e.getSource() == b[i]) {
                    Buffer = new Integer(b[i].getText());
                    return;
                } else {
                    b[i].setSelected(false);
                }
            }
        } 
    }
    
    public static void main(String args[]) {
    	GUIInstance = new GUI ( );
    }
}