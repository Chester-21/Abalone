/**
 * Code based on Labyrinth.java by Fotis Psomopoulos
 * Data Structures project, year 2006 - 2007
 * This is the panel that corresponds to the board.
 */

package gr.auth.ee.dsproject.abalone.util;

import gr.auth.ee.dsproject.abalone.Cell;
import gr.auth.ee.dsproject.abalone.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	/**
	 * <p>Title: DataStructures2010</p>
	 *
	 * <p>Description: Data Structures project: year 2010-2011</p>
	 *
	 * <p>Copyright: Copyright (c) 2010</p>
	 *
	 * <p>Company: A.U.Th.</p>
	 *
	 * @author Vivia Nikolaidou
	 * @version 1.0
	 */

	private static final long serialVersionUID = 1L;

	private static int padding = 0;
	private int rows;
	private int columns;
	private Cell[][] board;
	private int cellSize = 40;
	private int WIDTH;
	private int HEIGHT;


	public Dimension getPreferredSize() {
		//This is our preferred size
		return new Dimension(WIDTH + 2 * padding, HEIGHT);
	}

	public BoardPanel(Game game) {
		super();
		init(game);
	}
	
	public int getCellSize() {
		return cellSize;
	}

	public void init(Game game) {
		//Initialize the board's dimensions
		int[] dimensions = {game.getX(), game.getY()};
		rows = dimensions[0];
		columns = dimensions[1];
		board = game.getBoard();
		WIDTH = cellSize * columns;
		HEIGHT = cellSize * rows;
		setSize(WIDTH + 2 * padding, HEIGHT);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.lightGray);
		for(int x = 1 ; x < 10 ; x++){
			for(int y = 1; y < 10 ; y++){
				if (board[10-x][y].isInBoard()) {
					drawNeck(g,y*cellSize+((x-4)*cellSize/2),x*cellSize*0.8+60,cellSize/2+2,6);
					if (board[10-x][y].getColor()!=null) {
						highlightDisc(g,10-x,y,board[10-x][y].getColor());
					}
					//g.setColor(Color.red);
					//g.drawString(""+(10-x)+","+(y), y*cellSize+((x-4)*cellSize/2), (int) (x*cellSize*0.8));
					///g.setColor(Color.lightGray);
				}
				
			}
		}
	}
	
	public void highlightDisc(Graphics g, int x, int y, Color c) {
		if (c==null) c = Color.lightGray;
		g.setColor(c);
		x=10-x;
		g.fillOval(y*cellSize+((x-5)*cellSize/2)+2, (int)(x*cellSize*0.8-0.5*cellSize+2+60), cellSize-4, cellSize-4);
		g.setColor(Color.lightGray);
	}
	
	private void drawNeck(Graphics g, double x, double y, double r, int n){
		Polygon p = new Polygon();
		for (int i = 0; i < n; i++)
			p.addPoint((int)Math.ceil(x + r*Math.sin(i*2*Math.PI/n)),
					(int)Math.ceil(y + r*Math.cos(i*2*Math.PI/n)));
		g.fillPolygon(p);
		g.setColor(Color.gray);
		g.drawPolygon(p);
		g.setColor(Color.lightGray);
	}

}
