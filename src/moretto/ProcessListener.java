package moretto;

interface ProcessListener
{
	/**
	 * � chamado quando o processo � terminado.
	 *  
	 * @param success true se o processo foi executado com �xito, false caso contr�rio.
	 */
    public void finish( boolean success );
    
    /**
     * � chamado quando acontece um erro no processo.
     * 
     * @param error Mensagem de erro.
     */
    public void reporterror( String error );
}