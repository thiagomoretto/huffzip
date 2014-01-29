package moretto;

/**
 * Definição de um nó da árvore binária digital de Huffman.
 *  
 * @author Thiago Moretto
 */
public class Node 
{

    /**
     * Número de ocorrências.
     * 
     */
	public int count;
    
    /**
     * Código ASCII do simbolo.
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
