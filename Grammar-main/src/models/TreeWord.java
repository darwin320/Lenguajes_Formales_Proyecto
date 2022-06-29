package models;

/**
 * Clase encargada de crear el arbol de una palabra
 * @author user
 *
 */
public class TreeWord {

	private NodeTreeWord root;
	private NodeTreeWord actual;
	private int altura;
	private int RTendence = 0;
	private int LTendence = 0;

	/**
	 * Metodo constuctor de la clase, inicializa la altura y la raiz
	 */
	public TreeWord() {
		altura = 1;
		root = null;
	}
	
/**Metodo encargado de agregar al arbol cada una
 * de las ramificaciones necesarias para generar el arbol particular de 
 * una palabra
 * @param dataIzq El dato que se agrega en la hoja izquierda
 * @param dataDer El dato que se agrega en la hoja derecha
 * @param puntero El simbolo que genero las producciones que se agregan a derecha
 * e izquierda
 */
	public void add( String dataIzq, String dataDer, String puntero) {
		altura++;
		if (root == null) {
			root = new NodeTreeWord(puntero);
			actual = root;
		} else {
				actual.insert(dataIzq, dataDer);
				if (getNoTerminal(actual, puntero) != null) {
					actual = getNoTerminal(actual, puntero);
				}else {
					actual.insert(dataIzq, dataDer);					
			}
		}
		if(dataIzq!=null)
			this.LTendence ++;
		if(dataDer!=null)
			this.RTendence ++;
	}

	/**
	 * Metodo que retorna el nodo siempre y cuando sea un simbolo no terminal para agregarle a el las nuvas composiciones
	 * @param nodeTreeWord simbolo no terminal actual
	 * @param puntero que va guardando la referencia del ultimo nodo padre
	 * @return nodo siempre  y cuando sea un simbolo no terminal
	 */
	public NodeTreeWord getNoTerminal(NodeTreeWord nodeTreeWord, String puntero) {
		if (nodeTreeWord.getDer() != null && nodeTreeWord.getDer().getData().equals(puntero)) {
			return nodeTreeWord.getDer();
		} else if (nodeTreeWord.getIzq() != null) {
			return nodeTreeWord.getIzq();
		}
		return null;
	}

	/**
	 * metodo get de lka raiz del arbol
	 * @return la raiz del arbol
	 */
	public NodeTreeWord getRoot() {
		return root;
	}

	/**
	 * Metodo que extrae la altura del arbol
	 * @return altura del arbol
	 */
	public int getAltura() {
		return altura;
	}

	public int getTendence(){
		int value=0;
		if(this.LTendence < this.RTendence)
			value = 1;
		if(this.LTendence > this.RTendence)
			value = -1;
		if(this.LTendence == this.RTendence)
			value = 0;
		return value;
	}

}
