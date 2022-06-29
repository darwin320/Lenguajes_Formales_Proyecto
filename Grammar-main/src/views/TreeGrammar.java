package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import models.NodeTreeWord;
import models.TreeWord;


/*
 * esta clase se encarga de graficar el arbol particular de una palabra
 */
public class TreeGrammar extends JPanel {

	private static final long serialVersionUID = 1L;
	/*
	 * el siguiente atributo se encarga de darle un diametro a cada nodo del arbol
	 */
	private static final int DIAMETER_OVAL = 30;
	private TreeWord treeWord;
	private int x;

	/*
	 * metodo constructor de la clase TreeGrammar
	 */
	public TreeGrammar(TreeWord treeWord) {
		this.treeWord = treeWord;
	}

	
	/*
	 * el siguiente método se encarga de graficar 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		x = 0;
		if (!(treeWord.getRoot() == null)) {
				x = getWidth()/2;
			int y = getHeight()/20;
			printTree(treeWord.getRoot(), x, 0, y, g);
		}
	}

	/**
	 * el siguiente método se encarga de graficar nodo a nodo mediante recursividad
	 * @param node parámetro que recibe el nodo a pintar
	 * @param x parámetro que indica la posicion en x que tomará el nodo que se graficará
	 * @param xRoot parámetro que recibe la posicion x del nodo padre
	 * @param y parámetro que indicará la altura del nodo que se graficará
	 * 
	 */

	private void printTree(NodeTreeWord node, int x, int xRoot, int y, Graphics g) {
		int heightvar = treeWord.getAltura();
		if (node != null) {
			g.setColor(Color.ORANGE);
			if(node.getDer()==null&&node.getIzq()==null)
				g.setColor(Color.PINK);
			g.fillOval(x, y, DIAMETER_OVAL, DIAMETER_OVAL);
			g.setColor(Color.BLACK);
			g.drawString(node.getData() + "", x + (DIAMETER_OVAL / 3), y + (2 * (DIAMETER_OVAL / 3)));
			g.setColor(Color.GREEN);
			if (node.getIzq() != null) {
				g.drawLine(x + (DIAMETER_OVAL / 2), y + DIAMETER_OVAL,
						x - ((getWidth() / treeWord.getAltura())/2) + (DIAMETER_OVAL / 2), y + getHeight() / heightvar);
			}
			if (node.getDer() != null) {
				g.drawLine(x + (DIAMETER_OVAL / 2), y + DIAMETER_OVAL, x + ((getWidth() / treeWord.getAltura())/2),
						y + getHeight() / heightvar);
			}
			printTree(node.getIzq(), x - ((getWidth() / treeWord.getAltura())/2), x, y + getHeight() / heightvar, g);
			printTree(node.getDer(), x + ((getWidth() / treeWord.getAltura())/2), x, y + getHeight() / heightvar, g);
		}
	}

}
