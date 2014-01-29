package moretto;

public class HuffmanDigitalTree 
	implements Comparable
{
	/**
	 * Raiz da �rvore.
	 * 
	 */
	public Node root;
  
	/**
	 * Constr�i um n�.
	 * 
	 * @param rootCount N�mero de ocorr�ncias do simbolo.
	 * @param rootSymbol C�digo ASCII do simbolo.
	 */
    public HuffmanDigitalTree( int rootCount, int rootSymbol ) {
        root = new Node();
        root.count = rootCount;
        root.symbol = rootSymbol;
    }

	/**
	 * Constr�i um n�.
	 * 
	 * @param rootCount N�mero de ocorr�ncias do simbolo, geralmente soma de ocorr�ncias.
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
