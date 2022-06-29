package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import models.Grammar;
import models.NodeProduction;
import models.TreeWord;
import presenters.Events;

/**
 * clase encargada de graficar el frame principal en el que se ingresará una gramática y también se imprimirá el menú
 */
public class MyJFramePrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
/**
 * @atributo terminals, noTerminals, axiomaticSymbol, productions son textos estáticos que se muestran para la construccion inicial de la gramática
 * @atributo grammarCreator, grammar son jpanels en grammarCreator se crea la gramatica y en grammar se muestran los botones para interactuar con la gramatica creada
 * @atributo jTree 
 * @atributo grammarShow Jpanel encargado de mostrar la gramática
 * @atributo treeGrammar instancia de la clase encargada de pintar un arbol
 * @atributo jPanelShowTree JPanel encargado de imprimir el arbol
 */
	private JTextField terminals, noTerminals, axiomaticSymbol, productions;
	private ActionListener l;
	private JPanel grammarCreator, grammar;
	private JTree jTree;
	private JPanel grammarShow;
	private TreeGrammar treeGrammar;
	private JPanel jPanelShowTree;
	private JPanel containerJPanel;

	/**
	 * 
	 * @param l instancia del ActionListener, necesario para el uso de eventos
	 */
	public MyJFramePrincipal(ActionListener l) {
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		this.l = l;
		grammar = new JPanel();
	}

	/**
	 * método encargado de agregar una nueva gramática
	 */
	public void addGrammarCreator() {
		remove(grammar);

		terminals = new JTextField();
		setJTextField(terminals, "Ingrese los simbolos terminales separados por comas (,)  :");
		noTerminals = new JTextField();
		setJTextField(noTerminals, "Ingrese los simbolos no terminales separados por comas (,)  :");
		axiomaticSymbol = new JTextField();
		setJTextField(axiomaticSymbol, "Ingrese el simbolo inicial axiomatico  :");
		productions = new JTextField();
		setJTextField(productions, "Ingrese las producciones serparadas por comas (,) formato :(T>>a)");

		grammarCreator = new JPanel();
		grammarCreator.setLayout(new GridLayout(5, 1, 10, 10));
		grammarCreator.add(terminals);
		grammarCreator.add(noTerminals);
		grammarCreator.add(axiomaticSymbol);
		grammarCreator.add(productions);
		grammarCreator.add(editButton("Crear gramatica", Events.CREATE_GRAMMAR.name()));
		add(grammarCreator);

		revalidate();
		repaint();
	}

	/**
	 * método encargado de agregar un nuevo panel de gramatica
	 */
	public void addPanelGrammar() {
		remove(grammarCreator);
		configurePanelGrammarCreator();
	}

	/**
	 * método encargado de agregar un panel para agregar una gramática
	 * @param jPanel jpanel en el que se espera que se incluyan los componentes para agregar una nueva gramática
	 */
	public void addPanelGrammar(JPanel jPanel) {
		remove(jPanel);
		configurePanelGrammarCreator();
	}

	/**
	 * método encargado de configurar el panel principal para imprimir el menú
	 */
	private void configurePanelGrammarCreator() {
		grammar = new JPanel();
		grammar.setLayout(new GridLayout(5, 1));
		grammar.add(editButton("Mostrar gramatica", Events.SHOW_GRAMMAR.name()));
		grammar.add(editButton("Mostrar arbol de la gramatica", Events.SHOW_GRAMMAR_TREE.name()));
		grammar.add(editButton("Buscar palabra", Events.SEARCH_WORD.name()));
		grammar.add(editButton("Crear nueva gramatica", Events.CREATE_NEW_GRAMMAR.name()));
		grammar.add(editButton("Salir", Events.EXIT.name()));
		add(grammar);
		revalidate();
		repaint();
	}
	
	/**
	 * método encargado de imprimir la gramática
	 * @param grammar instancia de la una clase Grammar de la cual se obtienen los simbolos y las producciones
	 */
	public void showGrammar(Grammar grammar) {
		remove(this.grammar);
		grammarShow = new JPanel();
		grammarShow.setLayout(new GridLayout(5,1));
		grammarShow.add(editLabel(grammar.getTerminals().toString(), "Simbolos terminales"));
		grammarShow.add(editLabel(grammar.getNoTerminals().toString(), "Simbolos no terminales"));
		grammarShow.add(editLabel(grammar.getAxiomticSymbol(), "Simbolo inicial Axiomtico"));
		grammarShow.add(editLabel(grammar.getProductions().toString(), "Producciones"));
		grammarShow.add(editButton("Regresar",Events.EXIT_TO_SHOW.name() ));
		add(grammarShow);
		revalidate();
		repaint();
	}
	
	/**
	 * método encargado de imprimir el arbol particular de una palabra mediante la clase TreeGrammar
	 * @param grammar instancia de la clase grammar de la cual obtendremos el arbol particular de una palabra
	 */
	public void showWordTree(TreeWord grammar) {
		remove(this.grammar);
		containerJPanel = new JPanel();
		containerJPanel.setLayout(new BorderLayout());
		treeGrammar = new TreeGrammar(grammar);
		containerJPanel.add(treeGrammar, BorderLayout.CENTER);
		containerJPanel.add(editButton("Regresar al menu",Events.EXIT_TO_SHOW_WORD_TREE.name()),BorderLayout.SOUTH);
		add(containerJPanel);
		revalidate();
		repaint();
	}
	
	/**
	 * agrega el arbol n-ario de las producciones 
	 * @param nodeProduction instancia de la clase NodeProduction, la cual genera un arbol n-ario especial de la gramática
	 */
	public void addJtree(NodeProduction nodeProduction) {
		remove(grammar);
		jPanelShowTree = new JPanel();
		jPanelShowTree.setLayout(new BorderLayout());
		paintTree(nodeProduction);
		jPanelShowTree.add(new JScrollPane(jTree),BorderLayout.CENTER);
		jPanelShowTree.add(editButton("Regresar", Events.EXIT_TO_SHOW_TREE.name()),BorderLayout.SOUTH);
		add(jPanelShowTree);
		revalidate(); 
		repaint();
	}
	
	/**
	 * método encargado de construir el JTree para imprimir el arbol de una gramática
	 * @param iNode instancia de la clase NodeProduction funciona como un nodo raiz del que se construiá todo el arbol
	 */
	public void paintTree(NodeProduction iNode) {
		jTree = new JTree();
		DefaultMutableTreeNode visualRoot = new DefaultMutableTreeNode(iNode.getProduction());
		for (NodeProduction child : iNode.getChilds()){
			addChild(visualRoot, child);
		}
		DefaultTreeModel treeModel = new DefaultTreeModel(visualRoot);
		jTree.setModel(treeModel);

		expandAll();
		jTree.repaint();
	}

	/**
	 * el siguiente método se encarga de agregar un nodo hijo, es usado para la construccion del JTree
	 * @param father instancia de la clase DefaultMutableTreeNode la cual se usa para indicar el nodo padre
	 * @param base instancia de la clase NodeProduction es usada para asignar un nodo hijo
	 */
	private void addChild(DefaultMutableTreeNode father, NodeProduction base) {
		DefaultMutableTreeNode visualNode = new DefaultMutableTreeNode(base.getProduction());
		father.add(visualNode);
		for (NodeProduction child : base.getChilds()) {
			addChild(visualNode, child);
		}
	}

	/**
	 * método encargado de expandir el JTree
	 */
	private void expandAll() {
		for (int i = 0; i < jTree.getRowCount(); i++) {
			jTree.expandRow(i);
		}
	}

	/**
	 * 
	 */
	public void exitToMainShow() {
		addPanelGrammar(grammarShow);
	}
	
	/**
	 * 
	 */
	public void exitToShowTreeWord() {
		addPanelGrammar(containerJPanel);
	}
	
	/**
	 * 
	 */
	public void exitToShowTree() {
		addPanelGrammar(jPanelShowTree);
	}
	
	/**
	 * 
	 * @param text
	 * @param title
	 * @return
	 */
	private JLabel editLabel(String text, String title) {
		JLabel jLabel = new JLabel(text);
		jLabel.setBorder(BorderFactory.createTitledBorder(title));
		return jLabel;
	}
	
	/**
	 * 
	 * @param name
	 * @param commandName
	 * @return
	 */
	private JButton editButton(String name, String commandName) {
		JButton btn = new JButton(name);
		btn.setActionCommand(commandName);
		btn.addActionListener(l);
		return btn;
	}

	/**
	 * el siguiente método cambia las areas en las que el usuario ingresa informacion
	 * @param jTextField
	 * @param title
	 */
	private void setJTextField(JTextField jTextField, String title) {
		jTextField.setBorder(BorderFactory.createTitledBorder(title));
	}

	/**
	 * método que retorna los símbolos terminales
	 * @return 
	 */
	public String getTerminals() {
		return terminals.getText().replaceAll("\\s", "");
	}

	/**
	 * método que retorna los símbolos noterminales
	 * @return
	 */
	public String getNoTerminals() {
		return noTerminals.getText().replaceAll("\\s", "");
	}

	/**
	 * método que retorna los símbolos axiomáticos
	 * @return
	 */
	public String getAxiomatic() {
		return axiomaticSymbol.getText().replaceAll("\\s", "");
	}

	/**
	 * método que retorna las producciones
	 * @return
	 */
	public String getProductions() {
		return productions.getText().replaceAll("\\s", "");
	}


}
