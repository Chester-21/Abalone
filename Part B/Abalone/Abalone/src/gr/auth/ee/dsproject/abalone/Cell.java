package gr.auth.ee.dsproject.abalone;

import java.awt.Color;


/**
 * Class Cell
 */
public class Cell {

	//
	// Fields
	//

	Color color = null;
	int x;
	int y;

	//
	// Constructors
	//

	/**
	 * @param        x
	 * @param        y
	 */
	public Cell( int x, int y )
	{
		this.x=x;
		this.y=y;
	}

	//
	// Methods
	//


	//
	// Accessor methods
	//

	/**
	 * Set the value of color
	 * @param newVar the new value of color
	 */
	public void setColor ( Color newVar ) {
		if (newVar != Color.black && newVar != Color.white && newVar != null) {
			System.out.println("Unknown color, setting it to null");
			newVar = null;
		}
		if (!isInBoard()) newVar = null;
		color = newVar;
	}

	/**
	 * Get the value of color
	 * @return the value of color
	 */
	public Color getColor ( ) {
		if (!isInBoard()) return null;
		return color;
	}

	/**
	 * Set the value of x
	 * @param newVar the new value of x
	 */
	public void setX ( int newVar ) {
		x = newVar;
	}

	/**
	 * Get the value of x
	 * @return the value of x
	 */
	public int getX ( ) {
		return x;
	}

	/**
	 * Set the value of y
	 * @param newVar the new value of y
	 */
	public void setY ( int newVar ) {
		y = newVar;
	}

	/**
	 * Get the value of y
	 * @return the value of y
	 */
	public int getY ( ) {
		return y;
	}

	//
	// Other methods
	//

	/**
	 * @return       Cell
	 * @param        board
	 */
	public Cell[] getNeighbors( Cell[][] board )
	{
		Cell[] neighbors = new Cell[6];
		int width = board.length;
		int height = board[0].length;
		if (x+1<width) neighbors[0]=board[x+1][y];
		else neighbors[0]=null;
		if (x+1<width && y+1<height) neighbors[1]=board[x+1][y+1];
		else neighbors[1]=null;
		if (y+1<height) neighbors[2]=board[x][y+1];
		else neighbors[2]=null;
		if (x-1<0) neighbors[3]=null;
		else neighbors[3]=board[x-1][y];
		if (x-1<0 || y-1<0) neighbors[4]=null;
		else neighbors[4]=board[x-1][y-1];
		if (y-1<0) neighbors[5]=null;
		else neighbors[5]=board[x][y-1];
		return neighbors;
	}


	/**
	 * @return       boolean
	 */
	public boolean isInBoard(  )
	{
		if (x<=0 || y<=0 || x>=10 || y>=10) return false;
		return ((x-y) > -5 && (x-y) < 5);
	}

}