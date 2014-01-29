package moretto.ed;

import java.util.Vector;

/**
 * Classe que implementa um lista de prioridade, ou chamada de Heap.
 * 
 * @author Thiago Moretto
 * @version 1.1
 */
public class HeapCrescente extends Vector
{
	/**
	 * Inicia uma Heap vazia.
	 *
	 */
	public HeapCrescente( ) {
	}

	/**
	 * Inicia uma Heap com um vetor de objetos definido.
	 * 
	 * @param obj O vetor de objetos que implementam a inteface Comparable.
	 * @see Comparable
	 */
	public HeapCrescente( Comparable[] obj ) 
	{
		super();
		setSize(obj.length);
		
		for (int i = 0; i < obj.length; i++)
		    setElementAt(obj[i], i);
		
		for (int i = (int)Math.floor(size() / 2) - 1; i >= 0; i--)
		    down(i);
	}

	/**
	 * Remove e retorna o menor elemento da Heap.
	 * 
	 * @return O menor elemento da heap.
	 */
	public synchronized Object remove() 
	{
		Object element;
		element = (Object) elementAt(0);
		
		setElementAt(lastElement(), 0);
		removeElementAt(size() - 1);
		down( 0 );
		
		return element;
	}	
	
    private synchronized void down(int i)
    {
		int left = left(i);
		int right = right(i);
		int small;

		if (left < size() &&
			    ((Comparable)elementAt(left)).compareTo(elementAt(i)) <= 0)
				small = left;
			else
				small = i;

			if (right < size() &&
				((Comparable)elementAt(right)).compareTo(elementAt(small)) <= 0)
				small = right;
			
			if (small != i) {
				troca(i, small);
				down(small);
			}
    }


    /**
     * Insere um objeto na Heap.
     * 
     * @param k Objeto que implemente o Comparable
     * @see Comparable
     */
    public synchronized void insert(Comparable k)
    {
		int i = size();
		setSize(size() + 1);

		while (i > 0 && ((Comparable)elementAt(parent(i))).compareTo(k) > 0) {
		    setElementAt(elementAt(parent(i)), i);
		    i = parent(i);
		}
		
		setElementAt(k, i);
    }
    
    private synchronized void troca(int i, int j)
    {
    	Comparable temp = (Comparable) elementAt(j);
		setElementAt(elementAt(i), j);
		setElementAt(temp, i);
    }
    
    private int left(int i) {
    	return ((i + 1) << 1) - 1;
    }

    private int right(int i) {
    	return ((i + 1) << 1);
    }

    private int parent(int i) {
    	return ((i + 1) >> 1) - 1;
    }
}