package moretto;

/**
 * Defini��o de um n� da �rvore bin�ria digital de Huffman.
 *  
 * @author Thiago Moretto
 */
public class Node 
{

    /**
     * N�mero de ocorr�ncias.
     * 
     */
	public int count;
    
    /**
     * C�digo ASCII do simbolo.
     * 
     */
	public int symbol = -1;
    
    /**
     * Filho esquerdo
     * 
     */
	public Node Left = null;
    

    /**
     * Filho direito
     * 
     */
	public Node Right = null;
    
}
