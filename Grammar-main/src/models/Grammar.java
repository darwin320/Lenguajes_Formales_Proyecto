package models;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase encargada de el manejo de todas las clases del modelo
 * aqui se instancian cada una de ellas y esta clase es la que va al controlador
 */

public class Grammar {

	private ArrayList<String> terminals, noTerminals;
	private String axiomticSymbol;
	private ArrayList<Production> productions;
	private GrammarTree grammarTree;

	/**
	 * Metodo constructor de la clase
	 * @param terminals lista de simbolos terminales
	 * @param noTerminals lista de simbolos no terminales
	 * @param axiomticSymbol simbolo inicial axiomatico
	 * @param productions lista de producciones de la gramatica
	 */
	public Grammar(ArrayList<String> terminals, ArrayList<String> noTerminals, String axiomticSymbol,
			ArrayList<Production> productions) {
		super();
		this.terminals = terminals;
		this.noTerminals = noTerminals;
		this.axiomticSymbol = axiomticSymbol;
		this.productions = productions;
		grammarTree = new GrammarTree(axiomticSymbol, noTerminals, productions);
	}

	public String getAxiomticSymbol() {
		return axiomticSymbol;
	}

	public ArrayList<String> getNoTerminals() {
		return noTerminals;
	}

	public ArrayList<Production> getProductions() {
		return productions;
	}

	public ArrayList<String> getTerminals() {
		return terminals;
	}

	/**
	 * metodo fachada del metodo de buscar una palabra
	 * @param word palabra a buscar
	 */
	public boolean searchWord(String word) {
		return grammarTree.searchWord(word);
	}

	/**
	 * metodo encargado de creal el arbol particular para una palabra
	 * Toma la ruta de la palabra, la voltea y la agrea al arbol de derivacion
	 * particular de  la palabra
	 * @return el arbol de derivacion particular de la palabra
	 */
	public TreeWord getTree() {
		ArrayList<String> pathWord = grammarTree.getPathWord();
			Collections.reverse(pathWord);
		TreeWord treeWord = new TreeWord();
		for (String pw : pathWord) {
			if (pw.length() == 1) {
				treeWord.add("" + pw.charAt(0), "", pathWord.get(0));
			} else {
				if (noTerminal(pw).equals("")) {
					treeWord.add( "" + pw, "", noTerminal(pw));
				} else {
					treeWord.add( "" + pw.charAt(0), "" + pw.charAt(1), noTerminal(pw));
				}
			}
		}
		return treeWord;
	}
	
	/**
	 * Metodo que busca el simbolo no terminal en una produccion
	 * @param word produccion
	 * @return retorna el simbolo no terminal de la produccion
	 */

	private String noTerminal(String word) {
		for (String nt : noTerminals) {
			for (int i = 0; i < word.length(); i++) {
				if (nt.equals("" + word.charAt(i))) {
					return nt;
				}
			}
		}
		return "";
	}


	/**
	 * Metodo que hace get a la clase que crea el arbol de la gramatica
	 * @return instancia de la clase que crea arbol de la gramatica
	 */
	public GrammarTree getGrammarTree() {
		return grammarTree;
	}

}
