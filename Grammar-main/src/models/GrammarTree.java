package models;

import java.util.ArrayList;

/**
 * Clase encargada de generar el arbol de la gramatica buscar una palabra en la
 * gramatica
 * 
 * @author jhona
 *
 */
public class GrammarTree {

	private NodeProduction root, rootTreeWord;
	private ArrayList<Production> productions;
	private ArrayList<String> noTerminals;
	private ArrayList<String> pathWord;
	private String initialSymbol;
	private boolean entrar;

	/**
	 * Metodo constructor de la clase
	 * 
	 * @param initialSymbol es el simbolo inicial axiomatico
	 * @param noTerminals   lista de simbolos no terminales
	 * @param productions   lista de producciones
	 */
	public GrammarTree(String initialSymbol, ArrayList<String> noTerminals, ArrayList<Production> productions) {
		this.root = new NodeProduction(initialSymbol);
		this.initialSymbol = initialSymbol;
		this.rootTreeWord = new NodeProduction(initialSymbol, initialSymbol);
		this.productions = productions;
		this.noTerminals = noTerminals;
		addProductions();
	}

	/**
	 * Metodo que retorna la raiz del arbol de la gramatica
	 * 
	 * @return la raiz del arbol de la gramatica
	 */
	public NodeProduction getRoot() {
		return root;
	}

	/**
	 * Metodo para buscar una palabra en la gramatica
	 * 
	 * @param word (palabra a buscar)
	 * 
	 *             El metodo crea una lista para guardar la composicion de la
	 *             palabra El limite es la cantidad tentatiba de generaciones que se
	 *             debe hacer para llegar a la palabra que se busca
	 * 
	 *             llama al metodo treeWord que se encarga degenerar el arbol
	 *             particular de una palabra
	 *@return true si existe la palabra             
	 */
	public boolean searchWord(String word) {
		this.pathWord = new ArrayList<String>();
		int limit = (word.length());
		//+ (word.length() * noTerminals.size())
		this.rootTreeWord = new NodeProduction(initialSymbol, initialSymbol);
		entrar = true;
		treeWord(rootTreeWord, 1, limit, word);
		return pathWord.size()==0?false:true;
	}

	/**
	 * llama al metodo recursivo de addProductions y le ingresa como datos de
	 * partida el nodo raiz, el contador inicia en 1, y se define el limite donde se
	 * va a cortar el arbol
	 */
	private void addProductions() {
		addProductions(root, 1, 5);
	}

	/**
	 * Metodo para generar arbol general de la gramatica
	 * 
	 * @param nodeProduction (el nodo padre al que se le agregaran los datos)
	 * @param count          (cuenta los niveles del arbol para detenerlo en un
	 *                       nivel pre establecido)
	 * @param limit          (define el limite en el cual se detiene la generacion
	 *                       del arbol)
	 * 
	 *                       recorre las producciones y cada que encuentre en los
	 *                       datos de los hijos del nodo un simbolo no terminal que
	 *                       genera una produccion, esta es agregada
	 *
	 */
	private void addProductions(NodeProduction nodeProduction, int count, int limit) {
		for (Production production : productions) {
			for (int i = 0; i < nodeProduction.getProduction().length(); i++) {
				if (production.getProducer().equals(String.valueOf(nodeProduction.getProduction().charAt(i)))) {
					String temp = nodeProduction.getProduction().replace(production.getProducer(),
							production.getProduction());
					nodeProduction.addChild(temp);
				}
			}
		}
		if (count < limit) {
			count++;
			for (NodeProduction production : nodeProduction.getChilds()) {
				addProductions(production, count, limit);
			}
		}
	}

	/**
	 * Metodo encargado de generar un arbol de la gramatica hasta que se encuentre
	 * la palabra a buscar, cuando la encuentra crea la ruta de la palabra con el
	 * metodo createPath
	 * 
	 * @param nodeProduction recibe un nodo para buscar las producciones que genera
	 * @param limit          la cantidad de veces que se ejecuta la recursividad
	 * @param word           la palabra que se esta buscando
	 */
	private void treeWord(NodeProduction nodeProduction, int count, int limit, String word) {
		for (Production production : productions) {
			for (int i = 0; i < nodeProduction.getProduction().length(); i++) {
				if (production.getProducer().equals(String.valueOf(nodeProduction.getProduction().charAt(i)))) {
					String temp = nodeProduction.getProduction().replace(production.getProducer(),
							production.getProduction());
					nodeProduction.addChild(temp, production.getProduction());
					if (temp.equals(word)&&entrar) {
						pathWord.add(production.getProduction());
						pathWord = createPath(nodeProduction);
						rootTreeWord = null;
						entrar = false;
						return;
					}
				}
			}
		}
		if (count < limit && entrar) {
			count++;
			for (NodeProduction production : nodeProduction.getChilds()) {
				treeWord(production, count, limit, word);
			}
		}
	}
//	

	/**
	 * Crea una lista con la ruta que compone la palabra, desde su composicion final
	 * hasta el simbolo inicial axiomatico
	 * 
	 * @param nodeProduction nodo desde donde se pretende armar la ruta
	 * @return una lista con la ruta que compone la palabra a buscar
	 */
	private ArrayList<String> createPath(NodeProduction nodeProduction) {
			NodeProduction aux = nodeProduction;
			while (aux != rootTreeWord) {
				pathWord.add(aux.getProducer());
				aux = searchDad(aux);
			}
			pathWord.add(rootTreeWord.getProducer());
			return pathWord;
	}

	/**
	 * Metodo que llama al recursivo searchDad
	 *
	 * @param id el nodo al que se le quiere buscar el padre
	 * @return el padre del nodo ingresado por parametro
	 */
	public NodeProduction searchDad(NodeProduction id) {
		if (rootTreeWord != null) {
			for (int i = 0; i < rootTreeWord.getChilds().size(); i++) {
				if (rootTreeWord.searchChild(id)) {
					return rootTreeWord;
				}
			}
		}
		return searchDad(rootTreeWord, id);
	}

	/**
	 * Metodo que busca el nodo padre de un nodo determinado
	 * 
	 * @param node      nodo donde se busca el nodo hijo para saber si pertenece a
	 *                  la lista de hijos de nodo en que se busca
	 * @param nodeChild nodo al que se le busca el nodo padre
	 * @return retorna el nodo padre, o null si no lo encuentra
	 */
	private NodeProduction searchDad(NodeProduction node, NodeProduction nodeChild) {
		if (node != null && !node.getChilds().isEmpty()) {
			for (NodeProduction actual : node.getChilds()) {
				if (actual.searchChild(nodeChild)) {
					return actual;
				} else {
					NodeProduction search = searchDad(actual, nodeChild);
					if (search != null) {
						return search;
					}
				}
			}
		}
		return null;

	}

	/**
	 * Metodo get de la ruta de una palabra
	 * 
	 * @return una lista con la ruta de producciones necesarias para llegar a la
	 *         palabra
	 */
	public ArrayList<String> getPathWord() {
		return pathWord;
	}

}
