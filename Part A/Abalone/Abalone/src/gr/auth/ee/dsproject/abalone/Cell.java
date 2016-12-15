package gr.auth.ee.dsproject.abalone;

import java.awt.Color;
/**Αναπαράσταση ενός κελιού
  
   Δημοσθενης Μπελεβεσλης
   ΑΕΜ : 6981
   Τηλέφωνο : 6945255427
   E-mail : dimbele4@hotmail.com
   
   Αθανασιος Μεκρας
   ΑΕΜ : 7074
   Τηλέφωνο : 6943949005
   E-mail : athanamn@auth.gr


*/

public class Cell 
{
	int x,y;
	Color color;
	
	/**
	 * Get the value of color
	 * @return the value of color
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Set the value of color
	 * @param newColor the new value of color
	 */
	void setColor(Color newColor)
	{
		color=newColor;
	}
	/**
	 * Get the value of x
	 * @return the value of x
	 */
	int getX()
	{
		return x;
	}
	
	/**
	 * Set the value of x
	 * @param newVar the new value of x
	 */
	void setX(int newVar)
	{
		x=newVar;
	}
	
	/**
	 * Get the value of y
	 * @return the value of y
	 */
	int getY()
	{
		return y;
	}
	
	/**
	 * Set the value of y
	 * @param newVar the new value of y
	 */
	void setY(int newVar)
	{
		y=newVar;
	}
	
	/**
	 * Class Cell Constructor 
	 * Sets the parameters to X and Y of class
	 */
	Cell(int x, int y)
	{
		this.x=x;
		this.y=y;
		color=null;
	}
	
	/**
	 * Method getNeighbors
	 * It takes as its argument a table of cells 11x11
	 * Returns a linear list of 6 cells that
	 * contains the adjacent cells of the table
	 * @param board gives you a 11x11 table
	 */
	Cell[] getNeighbors(Cell[][] board)
	{
		Cell[] neighbors_cells= new Cell[6];
		int i,temp1, temp2;
		for(i=0;i<=5;i++)
		{
			temp1=x;
			temp2=y;
			if (i==0)
			{
				neighbors_cells[0]=board[temp1+1][temp2];
			}
			if (i==1)
			{
				
				neighbors_cells[1]=board[temp1+1][temp2+1];
			}
			if (i==2)
			{
				
				neighbors_cells[2]=board[temp1][temp2+1];
			}
			if (i==3)
			{

				
				neighbors_cells[3]=board[temp1-1][temp2];
			}
			if (i==4)
			{
				
				neighbors_cells[4]=board[temp1-1][temp2-1];
			}
			if (i==5)
			{
				
				neighbors_cells[5]=board[temp1][temp2-1];
			}
		}
		return neighbors_cells;
	}
	
	/**
	 * Method isInBoard
	 * Returns true if the is cell is located inside the table
	 * Returns false if the is cell outside the table
	 */
		public boolean isInBoard()
		{
			
			if (x==0)
			{
				return false;
			}
			if (x==1)
			{
				if ((y>=1) && (y<=5))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
		
			if (x==2)
			{
				if ((y>=1) && (y<=6))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==3)
			{
				if ((y>=1) && (y<=7))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==4)
			{
				if ((y>=1) && (y<=8))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==5)
			{
				if ((y>=1) && (y<=9))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==6)
			{
				if ((y>=2) && (y<=9))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==7)
			{
				if ((y>=3) && (y<=9))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==8)
			{
				if ((y>=4) && (y<=9))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==9)
			{
				if ((y>=5) && (y<=9))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (x==10) 
			{
				return false;
			}
			else
				return false;
			
		}
}
	