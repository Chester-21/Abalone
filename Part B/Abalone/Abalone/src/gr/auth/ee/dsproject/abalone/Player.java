
package gr.auth.ee.dsproject.abalone;

import java.awt.Color;
import java.util.Vector;


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
public class Player {

	//
	// Fields
	//

	String type="Human";
	boolean isBlack;
	int deadPieces;
	static String[] availableTypes={"Human", "Random", "Heuristic"};

	//
	// Constructors
	//
	/**
	 * @param        isComputer
	 * @param        isBlack
	 */
	public Player( String type, boolean isBlack )
	{
		this.type=type;
		this.isBlack=isBlack;
	}

	//
	// Methods
	//


	//
	// Accessor methods
	//

	/**
	 * Set the value of isComputer
	 * @param newVar the new value of isComputer
	 */
	public void setType ( String newVar ) {
		type = newVar;
	}

	/**
	 * Get the value of isComputer
	 * @return the value of isComputer
	 */
	public String getType ( ) {
		return type;
	}

	/**
	 * Set the value of isBlack
	 * @param newVar the new value of isBlack
	 */
	public void setIsBlack ( boolean newVar ) {
		isBlack = newVar;
	}

	/**
	 * Get the value of isBlack
	 * @return the value of isBlack
	 */
	public boolean isBlack ( ) {
		return isBlack;
	}

	/**
	 * Set the value of deadPieces
	 * @param newVar the new value of deadPieces
	 */
	public void setDeadPieces ( int newVar ) {
		deadPieces = newVar;
	}

	/**
	 * Get the value of deadPieces
	 * @return the value of deadPieces
	 */
	public int getDeadPieces ( ) {
		return deadPieces;
	}

	/**
	 * Get the value of availableTypes
	 * @return the value of availableTypes
	 */
	public static String[] getAvailableTypes() {
		return availableTypes;
	}

	/**
	 * Set the value of availableTypes
	 * @param newVar the new value of availableTypes
	 */
	public static void setAvailableTypes(String[] newVar) {
		availableTypes = newVar;
	}

	//
	// Other methods
	//

	/**
	 * @param        game
	 */
	public Move getNextMove( Game game )
	{
		Vector<Move> moves = findAllMoves(game);
		if (type=="Heuristic") return getNextMoveHeuristic(game, moves);
		if (type=="Human") System.out.println("Warning: asked human's next move! Returning random");
		return getNextMoveRandom(game, moves);
	}

	private Move getNextMoveRandom (Game game, Vector<Move> moves) {
		int nmoves = moves.size();
		int rand = (int)Math.floor(nmoves*Math.random());
		return moves.get(rand);	
	}

	private Move getNextMoveHeuristic (Game game, Vector<Move> moves) {
		int nmoves = moves.size();
		int imax=0;
		double max=-2000;
		for (int i=0;i<nmoves;i++) {
			Game gcopy = game.copy();
			Cell newFrom1 = gcopy.getBoard()[moves.get(i).getFrom1().getX()][moves.get(i).getFrom1().getY()];
			Cell newFrom2 = gcopy.getBoard()[moves.get(i).getFrom2().getX()][moves.get(i).getFrom2().getY()];
			Cell newTo2 = gcopy.getBoard()[moves.get(i).getTo2().getX()][moves.get(i).getTo2().getY()];
			Move newMove = new Move(newFrom1, newFrom2, newTo2);
			gcopy.playMove(newMove);
			double eval = evaluate(gcopy);
			if (eval>max) {
				imax=i;
				max=eval;
			}
		}
		return moves.get(imax);
	}
	
	/**Ευρεση και αξιολογηση διαθεσιμων κινησεων
	  
	   Δημοσθενης Μπελεβεσλης
	   ΑΕΜ : 6981
	   Τηλέφωνο : 6945255427
	   E-mail : dimbele4@hotmail.com
	   
	   Αθανασιος Μεκρας
	   ΑΕΜ : 7074
	   Τηλέφωνο : 6943949005
	   E-mail : athanamn@auth.gr


	*/
	/**
	 * Evaluation method
	 * We use three evaluation criteria
	 * @param        game corresponds to the game
	 */
	public double evaluate (Game game) {
		
		double x1=0,x2=0,x3=0,y1=0,y2=0,y3=0,Xol=0,Yol=0;
		double E=0;
		/**
		 * Criterion Number 1
		 * Check the number of black player's DeadPieces
		 * Depending on the number of DeadPieces give us a different rating
		 */
		
		switch(game.blackPlayer.getDeadPieces())
		{	
			case 0:
				{
					x1=x1+100;
					break;
				}
			case 1:
				{
					x1=x1+90;
					break;
				}
			case 2:
				{
					x1=x1+75;
					break;
				}
			case 3:
				{
					x1=x1+55;
					break;
				}
			case 4:
				{
					x1=x1+30;
					break;
				}
			case 5:
				{
					x1=x1+10;
					break;
				}
		}
		/**
		 * Check the number of white player's DeadPieces 
		 * Depending on the number of DeadPieces give us a different rating
		 */
		switch(game.whitePlayer.getDeadPieces())
		{	
			case 0:
				{
					y1=y1+100;
					break;
				}
			case 1:
				{
					y1=y1+90;
					break;
				}
			case 2:
				{
					y1=y1+75;
					break;
				}
			case 3:
				{
					y1=y1+55;
					break;
				}
			case 4:
				{
					y1=y1+30;
					break;
				}
			case 5:
				{
					y1=y1+10;
					break;
				}
		}
		
		
		/**
		 * Criterion Number 2
		 * Check all the black marbles
		 * Depending on how close to the center is the marble, the higher rating is given
		 */
		for (int i=1;i<=9;i++)
		{
			for (int j=1;j<=9;j++)
			{
				if (game.board[i][j].getColor() == Color.black )
				{
					/**
					 * Check if the marble is located four positions away from the center
					 */
					if ((i==1) || (i==9) || (j==1) || (j==9) )
					{
						x2=x2-1;
					}
					if ( (i>5 && i<9) && (j>1 && j<5) )
					{
						x2=x2-1;
					}
					if ( (j>5 && j<9) && (i>1 && i<5) )
					{
						x2=x2-1;
					}
					/**
					 * Check if the marble is located three positions away from the center
					 */
					if ( ((i==2)&& (j>1 && j<6)) || ((i==8)&& (j>4 && j<9)))
					{
						x2=x2-0.5;
					}
					if ( ((j==2)&& (i>2 && i<6)) || ((j==8)&& (i>4 && i<8)))
					{
						x2=x2-0.5;
					}
					if ( ((i==6)&&(j==3)) || ((i==3)&&(j==6)) || ((i==7)&&(j==4)) || ((i==4)&&(j==7)))
					{
						x2=x2-0.5;
					}
					/**
					 * Check if the marble is located two positions away from the center
					 */
					if (( (i==3) && (j>2 && j<6)) || ((i==7) && (j>4 && j<8)))
					{
						x2=x2+0.5;
					}
					if (((i==4) && (j==3)) || ((i==5) && (j==3)) || ((i==6) && (j==4)) || ((i==4) && (j==6)) || ((i==5) && (j==7)) || ((i==6) && (j==7)))
					{
						x2=x2+0.5;
					}
					/**
					 * Check if the marble is located one position away from the center
					 */
					if (( (i==4) && (j>3 && j<6)) || ((i==6) && (j>4 && j<7)) || ((i==5) && (j==4)) || ((i==5) && (j==6)))
					{
						x2=x2+1;
					}
					/**
					 * Check if the marble is located in the center
					 */
					if ( (i==5) && (j==5))
					{
						x2=x2+3;
					}
				}	
					
			}
		}

		/**
		 * Check all the white marbles
		 * Depending on how close to the center is the marble, the higher rating is given
		 */
		for (int i=1;i<=9;i++)
		{
			for (int j=1;j<=9;j++)
			{
				if (game.board[i][j].getColor() == Color.white )
				{
					/**
					 * Check if the marble is located four positions away from the center
					 */
					if ((i==1) || (i==9) || (j==1) || (j==9) )
					{
						y2=y2-1;
					}
					if ( (i>5 && i<9) && (j>1 && j<5) )
					{
						y2=y2-1;
					}
					if ( (j>5 && j<9) && (i>1 && i<5) )
					{
						y2=y2-1;
					}
					/**
					 * Check if the marble is located three positions away from the center
					 */
					if ( ((i==2)&& (j>1 && j<6)) || ((i==8)&& (j>4 && j<9)))
					{
						y2=y2-0.5;
					}
					if ( ((j==2)&& (i>2 && i<6)) || ((j==8)&& (i>4 && i<8)))
					{
						y2=y2-0.5;
					}
					if ( ((i==6)&&(j==3)) || ((i==3)&&(j==6)) || ((i==7)&&(j==4)) || ((i==4)&&(j==7)))
					{
						y2=y2-0.5;
					}
					/**
					 * Check if the marble is located two positions away from the center
					 */
					if (( (i==3) && (j>2 && j<6)) || ((i==7) && (j>4 && j<8)))
					{
						y2=y2+0.5;
					}
					if (((i==4) && (j==3)) || ((i==5) && (j==3)) || ((i==6) && (j==4)) || ((i==4) && (j==6)) || ((i==5) && (j==7)) || ((i==6) && (j==7)))
					{
						y2=y2+0.5;
					}
					/**
					 * Check if the marble is located one position away from the center
					 */
					if (( (i==4) && (j>3 && j<6)) || ((i==6) && (j>4 && j<7)) || ((i==5) && (j==4)) || ((i==5) && (j==6)))
					{
						y2=y2+1;
					}
					/**
					 * Check if the marble is located in the center
					 */
					if ( (i==5) && (j==5))
					{
						y2=y2+3;
					}
				}	
					
			}
		}
		/**
		 * Criterion Number 3
		 * Check all the black marbles
		 * The more comprehensive are the marbles the higher rating is given
		 */
		for (int i=1;i<=9;i++)
		{
			for (int j=1;j<=9;j++)
			{
				if (game.board[i][j].getColor() == Color.black )
				{	/**
					 *Check the six neighbors of the cell
					 */
					for (int k=0; k<=5; k++)
					{
						/**
						 * Check if the neighbor cell is null and is located inside the board
						 */
						if ((game.board[i][j].getNeighbors(game.board)[k].getColor() == null) &&
						  (game.board[i][j].getNeighbors(game.board)[k].isInBoard()))
						{
							x3=x3+0.5;
						}
						/**
						 * Check if the neighbor cell is white and is located inside the board
						 */
						if ((game.board[i][j].getNeighbors(game.board)[k].getColor() == Color.white) &&
						  (game.board[i][j].getNeighbors(game.board)[k].isInBoard()))
						{
							x3=x3+0.25;
						}
						/**
						 * Check if the neighbor cell is black and is located inside the board
						 */
						if ((game.board[i][j].getNeighbors(game.board)[k].getColor() == Color.black) &&
						  (game.board[i][j].getNeighbors(game.board)[k].isInBoard()))
						{
							x3=x3+1;
						}
					}
				}
				/**
				 * Check all the white marbles
				 * The more comprehensive are the marbles the higher rating is given
				 */

				if (game.board[i][j].getColor() == Color.white )
				{		
					/**
					*Check the six neighbors of the cell
					*/
					for (int k=0; k<=5; k++)
					{   
						/**
						 * Check if the neighbor cell is null and is located inside the board
						 */
						if ((game.board[i][j].getNeighbors(game.board)[k].getColor() == null) &&
						  (game.board[i][j].getNeighbors(game.board)[k].isInBoard()))
						{
							y3=y3+0.5;
						}
						/**
						 * Check if the neighbor cell is black and is located inside the board
						 */
						if ((game.board[i][j].getNeighbors(game.board)[k].getColor() == Color.black) &&
						  (game.board[i][j].getNeighbors(game.board)[k].isInBoard()))
						{
							y3=y3+0.25;
						}
						/**
						 * Check if the neighbor cell is white and is located inside the board
						 */
						if ((game.board[i][j].getNeighbors(game.board)[k].getColor() == Color.white) &&
						  (game.board[i][j].getNeighbors(game.board)[k].isInBoard()))
						{
							y3=y3+1;
						}
					}
				}
			}
		}
		
		/**
		 * Add the results of the three criteria for each color
		 */
		Xol=x1+x2+x3;
		Yol=y1+y2+y3;
		/**
		 * Check if it's black player's turn in order to make the correct subtraction
		 */
		if(game.getCurrent().isBlack())
		 {
		       E=Xol-Yol;
		
	     }
		/**
		 * Check if it's white player's turn in order to make the correct subtraction
		 */	
		if(!game.getCurrent().isBlack())
		{
		       E=Yol-Xol;
		
		}
		/**
		 * Returns the evaluation of the game
		 */	
		return E;
		
        		
	}

	
	
	/**
	 * Finds and returns all possible moves
	 * @param        game corresponds to the game
	 */
	public Vector<Move> findAllMoves (Game game) {
		Vector<Move> moves = new Vector<Move>();
		/**
		 * We seek all board cells
		 */
			for (int i=0; i<11;i++)
				{
					for (int j=0; j<11;j++)
					{	
						/**
						 * Check if the color of the cell is the same color of the player whose turn to play
						 */
						if ((game.board[i][j].getColor() == Color.black && game.getCurrent().isBlack()== true && game.board[i][j].isInBoard()) ||
						(game.board[i][j].getColor() == Color.white && game.getCurrent().isBlack()==false && game.board[i][j].isInBoard())) 
							{
							/**
							 * To move one marble
							 * Check the six neighbors of the cell
							 */
								for(int z=0;z<=5;z++)
									{
									/**
									 * Check if the neighbor cell is null and is located inside the board
									 */
										if ((game.board[i][j].getNeighbors(game.board)[z].getColor() == null) &&
										    game.board[i][j].isInBoard() &&(game.board[i][j].getNeighbors(game.board)[z].isInBoard()))
											{
												Cell newFrom1 = game.board[i][j];
												Cell newFrom2 = game.board[i][j];
												Cell newTo2 = game.board[i][j].getNeighbors(game.board)[z];
												Move newMove = new Move(newFrom1, newFrom2, newTo2);
												moves.add(newMove);
										    }
									}		
							}
						/**
						 * We seek all board cells
						 */
						for(int z=1;z<11;z++)
						{
							for(int h=1;h<11;h++)
							{
									/**
									 * To move two marble
									 * Check the half neighbors of the cell in order to avoid duplicate moves
									 */
										for (int k=0; k<=2; k++)
											{
												/**
												 * Check if the color of the cell is the same color of the player whose turn to play
												 * Check if the neighbor cell is null and is located inside the board
												 */
												if 
												((game.board[i][j].getNeighbors(game.board)[k] == game.board[z][h] && game.board[z][h].getColor() == Color.black && 
												game.getCurrent().isBlack()==true && game.board[z][h].isInBoard() && game.board[i][j].isInBoard()) 
												|| 
												(game.board[i][j].getNeighbors(game.board)[k] == game.board[z][h] && game.board[z][h].getColor() == Color.white && 
												game.getCurrent().isBlack() ==false && game.board[z][h].isInBoard() && game.board[i][j].isInBoard()) )  
													{
														for (int n=0; n<=2;n++)
														{
															if(game.board[i][j].isInBoard()&& game.board[z][h].isInBoard() && 
															game.board[z][h].getNeighbors(game.board)[n].isInBoard())
															{
																Cell newFrom1 = game.board[i][j];
																Cell newFrom2 = game.board[z][h];
																Cell newTo2 = game.board[z][h].getNeighbors(game.board)[n];
																Move newMove = new Move(newFrom1, newFrom2, newTo2);
																/**
																* Check whether the move is valid
																* Adds the newMove to moves
																*/
																if( newMove.isValid(game.board ))
																{													
																	moves.add(newMove);
																}
															}
														}
											    	}
											}
									
								/**
								 * We seek all board cells
								 */
								for(int m=0;m<11;m++)
									{
										for(int n=0;n<11;n++)
											{
																				
													/**
													 * To move three marble
													 * Check the half neighbors of the cell in order to avoid duplicate moves
													 */
														for(int w=0; w<=2; w++)
															{
																/**
																 * Check if the color of the cell is the same color of the player whose turn to play
																 * Check if the neighbor cell is null and is located inside the board
																 */
																if ((game.board[z][h].getNeighbors(game.board)[w] == game.board[m][n] && game.board[m][n].getColor() == Color.black && 
																	game.getCurrent().isBlack()==true && game.board[m][n].isInBoard() && game.board[i][j].isInBoard()) 
																	|| 
																	(game.board[z][h].getNeighbors(game.board)[w] == game.board[m][n] && game.board[m][n].getColor() == Color.white && 
																	game.getCurrent().isBlack() ==false && game.board[m][n].isInBoard() && game.board[i][j].isInBoard()) )  
																	{
																	/**
																	 * Check the direction of the marbles
																	 */
																	if(((i==z) && (i==m))   ||   ((j==h)&&(j==n))   ||   (((i==(z+1))&&(i==(m+2)) )&& ((j==(h+1))&&(j==(n+2)) ) ))
																		{	
																			for (int s=0; s<=0;s++)
																			{
																				if(game.board[i][j].isInBoard()&& game.board[z][h].isInBoard() && 
																				game.board[m][n].getNeighbors(game.board)[s].isInBoard())
																				{	
																					Cell newFrom1 = game.board[i][j];
																					Cell newFrom2 = game.board[m][n];
																					Cell newTo2 = game.board[m][n].getNeighbors(game.board)[s];
																					Move newMove = new Move(newFrom1, newFrom2, newTo2);
																					/**
																					* Check whether the move is valid
																					* Adds the newMove to moves
																					*/
																					if ( newMove.isValid(game.board))
																					{
																						moves.add( newMove);
																					}
																				}	
																			}	
																		}
																	}
																
															}
														
											}
												
									}
										
							}
										
						}					
					
						
					}
				}
			/**
			 * Return the possible moves
			 */
				
			return moves;
		
}


	/**
	 */
	public void addDeadPiece(  )
	{
		deadPieces++;
	}

	public void resetDeadPieces( ) {
		deadPieces = 0;
	}


}
