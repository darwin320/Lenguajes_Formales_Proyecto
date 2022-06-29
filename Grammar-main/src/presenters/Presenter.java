package presenters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JOptionPane;

import models.Grammar;
import models.Production;
import views.MyJFramePrincipal;

/**
 * Clase encargada de unir la logica del proyecto con la interfaz grafica
 */

public class Presenter implements ActionListener {

	private MyJFramePrincipal framePrincipal;
	private Grammar grammar;

	/**
	 * Metodo constructor de la clase
	 * hace instancia de la vitsa y el modelo
	 */
	public Presenter() {

		framePrincipal = new MyJFramePrincipal(this);
		framePrincipal.addGrammarCreator();
		framePrincipal.setVisible(true);
	}

	/**
	 * Metodo encargado de recibir los eventos de la GUI y realizar su respectivo algoritmo 
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Events.valueOf(e.getActionCommand())) {
		case CREATE_GRAMMAR:
			createGrammar();
			break;
		case SHOW_GRAMMAR:
			framePrincipal.showGrammar(grammar);
			break;
		case SHOW_GRAMMAR_TREE:
//			framePrincipal.addJtree(grammar.getGrammarTree().getRoot());
			grammar.getGrammarTree().getRoot();
			System.out.println("xd");
			
			System.out.println("ro" + Arrays.toString(grammar.getGrammarTree().getPathWord().toArray()));
			break;
		case CREATE_NEW_GRAMMAR:
			framePrincipal.addGrammarCreator();
			break;
		case SEARCH_WORD:
			searchWord();
			System.out.println("ro" + Arrays.toString(grammar.getGrammarTree().getPathWord().toArray()));
			break;
		case EXIT_TO_SHOW:
			framePrincipal.exitToMainShow();
			break;
		case EXIT_TO_SHOW_TREE:
			framePrincipal.exitToShowTree();
			break;
		case EXIT_TO_SHOW_WORD_TREE:
			framePrincipal.exitToShowTreeWord();
			break;
		case EXIT:
			System.exit(0);
			break;
		}
	}

	/**
	 * Este metodo fachada es el encargado de buscar para verificar si el parametro ingresado esta o no denteo del lenguajes
	 */
	private void searchWord() {
		String word = JOptionPane.showInputDialog("Palabra");
		if (grammar.searchWord(word)) {
			framePrincipal.showWordTree(grammar.getTree());
		} else {
			JOptionPane.showMessageDialog(null, "La palabra " + word + "  no pertenece al lenguaje", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Metodo fachada encargado de recibir los datos de la vista realizando su respectiva validacion y crear la gramatica
	 */
	private void createGrammar() {
		ArrayList<String> terminals = convert(framePrincipal.getTerminals());
		ArrayList<String> noTerminals = convert(framePrincipal.getNoTerminals());
		String axiomatic = framePrincipal.getAxiomatic();
		ArrayList<Production> productions = convertProductions(framePrincipal.getProductions());
//		System.out.println(Arrays.toString(productions.toArray()));
		grammarValidator(terminals, noTerminals, axiomatic, productions);
		stepProduction("aabcd", productions);
	}
	
	
	private ArrayList<Production> getFirstProduction(ArrayList<Production> productions){
		ArrayList<Production> productions2 = new ArrayList<>();
		for (Production production : productions) {
			if (production.getProducer().equals(framePrincipal.getAxiomatic())) {
				productions2.add(production);
				break;
			}
		}
		
		return productions2;
	}
		
	static int count = 0;
	
	private ArrayList<Production> stepProduction(String word,ArrayList<Production> productions){
		String producer ="";
		if (count == 0) {
			 producer = getFirstProduction(productions).get(0).getProduction();
		}
		for (Production production : productions) {
				String firstWord = findFirstUpperCaseLetter(producer);
				if (production.getProducer().equals(firstWord)) {
					String listProductions = production.getProduction();
					System.out.println(new String(producer.replace(firstWord, listProductions)));
				}
		}
		
	
		return productions;
	}
	
	
	
	
	private String findFirstUpperCaseLetter(String word ) {
		String  result = "";
		for (int i = 0; i < word.length(); i++) {
			if(Character.isUpperCase(word.charAt(i))){    
	            result = String.valueOf(word.charAt(i));
	            break;
	            }
		}
		
		return result;
	}

	/**
	 * Metodo fachada encargado de validar la gramatica
	 * @param terminals lista de simbolos terminales
	 * @param noTerminals lista de simbolos no terminales
	 * @param axiomatic axioma
	 * @param productions producciones correspondientes
	 */
	private void grammarValidator(ArrayList<String> terminals, ArrayList<String> noTerminals, String axiomatic,
			ArrayList<Production> productions) {
		if (valideSymbolsInProductions(terminals, noTerminals, productions)
				&& valideAxiomaticSymbol(noTerminals, axiomatic)) {
			grammar = new Grammar(terminals, noTerminals, axiomatic, productions);
			JOptionPane.showMessageDialog(null, "Gramatica creada con exito", "Completado", JOptionPane.PLAIN_MESSAGE);
			framePrincipal.addPanelGrammar();
		} else {
			JOptionPane.showMessageDialog(null,
					"Los simbolos de las producciones no coinciden con los simbolos terminales o no terminales"
							+ "o no hay una produccion que " + "inicie con el simbolo inicial axomatico",
					"Error en la gramatica", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Metodo fachada encargado de validar si un axioma pertenece o no a la lista de simbolos no terminales
	 * @param noTerminals lista de simbolos no terminales
	 * @param axiomatic axioma
	 * @return si el axioma pertenece o no a la lista de simbolos no terminales
	 */
	private boolean valideAxiomaticSymbol(ArrayList<String> noTerminals, String axiomatic) {
		for (String noTerminalsTemp : noTerminals) {
			if (axiomatic.equals(noTerminalsTemp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo fachada que valida los simbolos contenidos en las producciones
	 * @param terminals lista de simbolos terminales
	 * @param noTerminals lista de simbolos no terminales
	 * @param productions lista de producciones
	 * @return si los simbolos terminales o no terminales que se usan en las producciones estan en las listas de simbolos terminales y no terminales 
	 */
	private boolean valideSymbolsInProductions(ArrayList<String> terminals, ArrayList<String> noTerminals,
			ArrayList<Production> productions) {
		boolean temp = false;
		for (Production production : productions) {
			for (int i = 0; i < production.getProduction().length(); i++) {
				if ((valideTerminalsAndNoTerminals(terminals, "" + production.getProduction().charAt(i))
						|| valideTerminalsAndNoTerminals(noTerminals, "" + production.getProduction().charAt(i)))) {
					temp = true;
				} else {
					temp = false;
				}
			}
		}
		return temp;
	}

	/**
	 * Recibe un simbolo que hace parte de la produccion y valida si esta en los simbolos terminales o no terminales
	 * @param terminals lista de simbolos terminales
	 * @param symbol simbolo ingresado
	 * @return true si el simbolo existe en los simbolos terminales o no terminales y false si no existe
	 */
	private boolean valideTerminalsAndNoTerminals(ArrayList<String> terminals, String symbol) {
		for (String t : terminals) {
			if (t.equals(symbol)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Convierte un texto separado por comas en un arraylist 
	 * @param text texto a convertir en lista
	 * @return lista compuesta por el texto desglozado
	 */
	private ArrayList<String> convert(String text) {
		ArrayList<String> list = new ArrayList<String>();
		String[] listText = text.split(",");
		for (String string : listText) {
			list.add(string);
		}
		return list;
	}

	/**
	 * Recibe una cadena de texto que contiene una produccion, la converte en un arraylist,luego verifica que la produccion ese correcta o completa, de 
	 *lo contrario muestra una advertencia
	 * @param text produccion en forma de cadena de texto 
	 * @return la produccion convertida de cadena de texto a arraylist
	 */
	private ArrayList<Production> convertProductions(String text) {
		ArrayList<Production> productions = new ArrayList<Production>();
		ArrayList<String> temp = convert(text);
		for (String p : temp) {
			if (p.split(">>").length < 2) {
				JOptionPane.showMessageDialog(null, "Hay una produccion incompleta", "Error en las producciones",
						JOptionPane.ERROR_MESSAGE);
			} else {
				productions.add(new Production(p.split(">>")[0], p.split(">>")[1]));
			}
		}
		return productions;
	}
}
