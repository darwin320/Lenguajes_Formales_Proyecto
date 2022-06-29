package models;

import java.util.ArrayList;

/**
 * Clase encargada de crear los nodos que se agregan a los arboles n-arios, en los cuales se generan los arboles
 * general de la gramatica y particular de las palabras 
 */

public class NodeProduction {

	private String production;
	private String producer;
	private ArrayList<NodeProduction> childs;

	/**
	 * Crear los nodos del arbol general de la gramatica
	 * @param production produccion generada por el producer
	 */
	public NodeProduction(String production) {
		this.production = production;
		this.childs = new ArrayList<NodeProduction>();
	}
	
	/**
	 * Crear lo nodos del arbol de la palabra particular
	 * @param production produccion generada por el producer
	 * @param producer simbolo no terminal que genera producciones
	 */
	public NodeProduction(String production, String producer) {
		this.production = production;
		this.producer = producer;
		this.childs = new ArrayList<NodeProduction>();
	}
	
	/**
	 * Metodo encargado de agregar un hijo al arbol general de derivacion
	 * @param production produccion generada por el producer
	 */
	public void addChild(String production) {
		childs.add(new NodeProduction(production));
	}
	
	/**
	 * Metodo encargado de  agregar un hijo al arbol particular de una palabra
	 * @param production produccion generada por el producer
	 * @param producer simbolo no terminal que genera producciones
	 */
	public void addChild(String production, String producer) {
		childs.add(new NodeProduction(production,producer));
	}
	
	public ArrayList<NodeProduction> getChilds() {
		return childs;
	}
	
	/**
	 * Retorna el simbolo que genera una produccion
	 * @return simbolo que genera una produccion
	 */
	public String getProduction() {
		return production;
	}
	
	/**
	 * retorna una produccion
	 * @return produccion
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * Metodo encargado de buscar un nodo hijo segun el id del nodo
	 * @param id parametro de busqueda e identificador del nodo
	 * @return si el nodo existe o no
	 */
	public boolean searchChild(NodeProduction id) {
		for (NodeProduction nodeProduction : childs) {
			if (nodeProduction.equals(id)) {
				return true;
			}
		}
		return false;
	}
	

}
