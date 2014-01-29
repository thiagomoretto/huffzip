package moretto;

public class Resources 
{
	// -- Default Configuration
	public static final int DEFAULT_BUFFER		= 256;
	public static final int ACCEPTED_CHARS		= 256;
	
	// -- Header
	public static final String START_DELIMITER 	= "|";
	public static final String CHAR_DIVISOR		= "*";
	public static final String GROUP_DIVISOR	= "@";
	public static final String END_DELIMITER 	= "-|";
	
	// -- Textos
	public static final String ABOUT = 
						"Huffzip 0.2\n\nAutor: Thiago Galves Moretto\n" +
						"E-mail: thiago@moretto.eng.br\n" +
                        "\n" +
                        "Vers�o do software: 0.2\n\n" +
                        "Este software � de carater acad�mico, seu uso geral �\n" +
                        "extremamente n�o recomendado, utiliza-se t�cnicas  de\n" +
                        "prop�sito de apreendizado, utilize algoritmos e soft-\n" +
                        "wares melhores se quiser um software de compacta��o.\n\n" +
                        "Licen�a GPL, pode ser distribuido para quem desejar\n" +
                        "respeitando os direitos  do autor como  descrito na\n" +
                        "licen�a.";
	
	public static final String HOWTO = "Como compactar?\n\n" +
    					"V� em Arquivo, escolha Compactar, selecione o arquivo e\n" +
    					"depois escolha onde ser� salvo o arquivo compactado.\n\n" +
    					"Como descompactar?\n\n" +
    					"V� em Arquivo, Descompactar, escolha o arquivo compactador\n" +
    					"por este mesmo software e depois escolha onde salvar o ar-\n" +
    					"quivo descompactado.\n\n" +
    					"Qualquer outra d�vida pode ser enviado um e-mail para:\n" +
    					"Thiago Moretto <thiago@moretto.eng.br>";
	
	public static final String HELP = "Prioridade: Quanto menor, menor :-)\n" +
						"Buffer, recomenda-se 256 ou 512, mas  pode variar o desempenho\n" +
						"se escolhido outros, depende de m�quina, processamento\n" +
						"e o tamanho do arquivo.\n\n";
	
	public static final String PROCESS_RUNING = "J� h� um processo em execu��o";
	
	public static final String INVALID_FILE = "Arquivo n�o v�lido.";
	
    public static final String PROCESSED_OK = "Arquivo processado\n" +
    					"Dica: Utilize o software md5sum para checagem de integridade de arquivos.";
    
    public static final String PROCESSED_FAIL = "Arquivo n�o processado com �xito";
}