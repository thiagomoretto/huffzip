package moretto;

public class HuffmanDigitalTree 
	implements Comparable
{
	/**
	 * Raiz da árvore.
	 * 
	 */
	public Node root;
  
	/**
	 * Constrói um nó.
	 * 
	 * @param rootCount Número de ocorrências do simbolo.
	 * @param rootSymbol Código ASCII do simbolo.
	 */
    public HuffmanDigitalTree( int rootCount, int rootSymbol ) {
        root = new Node();
        root.count = rootCount;
        root.symbol = rootSymbol;
    }

	/**
	 * Constrói um nó.
	 * 
	 * @param rootCount Número de ocorrências do simbolo, geralmente soma de ocorrências.
	 */
    public HuffmanDigitalTree( int rootCount ) {
        root = new Node();
        root.count = rootCount;
    }

    public int compareTo(Object k) {
    	if (root.count  > ((HuffmanDigitalTree)k).root.count) return  1;
    	if (root.count  < ((HuffmanDigitalTree)k).root.count) return -1;
    	return 0;
    }
}
