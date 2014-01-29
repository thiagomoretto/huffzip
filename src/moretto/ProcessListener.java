package moretto;

interface ProcessListener
{
	/**
	 * É chamado quando o processo é terminado.
	 *  
	 * @param success true se o processo foi executado com êxito, false caso contrário.
	 */
    public void finish( boolean success );
    
    /**
     * É chamado quando acontece um erro no processo.
     * 
     * @param error Mensagem de erro.
     */
    public void reporterror( String error );
}