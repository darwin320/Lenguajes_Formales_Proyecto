package models;

/**
 * clase encargada de crear los nodos para generar los nodos para el arbol particular de una palabra
 */
public class NodeTreeWord {
	
	/**
	 * @atributo data toda la informacion contenida en NodeTreeWord
	 * @atributo izq, der nodos hijo
	 */
	private String data;
	private NodeTreeWord izq, der;
	public int weight = 0;
	
	/**
	 * método constructor de la clase NodeTreeWord
	 * @param data contiene la informacion que almacenará el nodo
	 */
	public NodeTreeWord(String data) {
		this.data = data;
		this.izq = null;
		this.der = null;
	}
	
	/**
	 * método encargado de insertar lkos nodos hijo del nodo local
	 * @param dataIzq es la información que contendrá el nodo hijo izquierdo
	 * @param dataDer es la información que contendrá el nodo hijo derecho
	 */
	public void insert(String dataIzq,String dataDer) {
		if (!dataIzq.equals("")) {
			izq = new NodeTreeWord(dataIzq);
		}
		if (!dataDer.equals("")) {
			der = new NodeTreeWord(dataDer);
		}
	}
	
	/**
	 * método encargado de retornar la informacion del nodo local
	 * @return retorna un String con la informacion
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * método encargado de retornar la informacion del nodo hijo derecho del nodo local
	 * @return retorna un String con la informacion
	 */
	public NodeTreeWord getDer() {
		return der;
	}
	
	/**
	 * método encargado de retornar la informacion del nodo hijo izquierdo del nodo local
	 * @return retorna un String con la informacion
	 */
	public NodeTreeWord getIzq() {
		return izq;
	}
}
