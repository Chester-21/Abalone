package gr.auth.ee.dsproject.abalone;

import java.util.Arrays;
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
public class Move {
	/**
	 * This class represents one move. Fields:
	 * from1 and from2 indicate the start and end of range of marbles to move
	 * to2 indicates the desctination of marble from2
	 */

  //
  // Fields
  //

  Cell from1;
  Cell from2;
  Cell to2;
  
  //
  // Constructors
  //
  public Move (Cell from1, Cell from2, Cell to2) {
	  this.from1=from1;
	  this.from2=from2;
	  this.to2=to2;
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of from1
   * @param newVar the new value of from1
   */
  public void setFrom1 ( gr.auth.ee.dsproject.abalone.Cell newVar ) {
    from1 = newVar;
  }

  /**
   * Get the value of from1
   * @return the value of from1
   */
  public gr.auth.ee.dsproject.abalone.Cell getFrom1 ( ) {
    return from1;
  }

  /**
   * Set the value of from2
   * @param newVar the new value of from2
   */
  public void setFrom2 ( gr.auth.ee.dsproject.abalone.Cell newVar ) {
    from2 = newVar;
  }

  /**
   * Get the value of from2
   * @return the value of from2
   */
  public gr.auth.ee.dsproject.abalone.Cell getFrom2 ( ) {
    return from2;
  }

  /**
   * Set the value of to1
   * @param newVar the new value of to1
   */
  public void setTo2 ( gr.auth.ee.dsproject.abalone.Cell newVar ) {
    to2 = newVar;
  }

  /**
   * Get the value of to1
   * @return the value of to1
   */
  public gr.auth.ee.dsproject.abalone.Cell getTo2 ( ) {
    return to2;
  }

  //
  // Other methods
  //

  /**
   * This method checks whether the move is valid on the board given as an argument.
   * 
   * @return       boolean
   */
  public boolean isValid(Cell[][] board)
  {
	  //Check if we are inside board
	  if (!to2.isInBoard()) {
		  System.out.println("Moving outside board");
		  return false;
	  }
	  //from2 and to2 must be neighbors
	  if (!(new Vector<Cell>(Arrays.asList(from2.getNeighbors(board))).contains(to2))) {
		  System.out.println("From2 and to2 aren't neighbors");
		  return false;  
	  }
	  //Cannot move to a space occupied by our own stones
	  if (to2.getColor()==from1.getColor()) {
		  System.out.println("Cannot move to a space occupied by ourself");
		  return false;
	  }
	  //If we are selecting one stone and moving it into a gap, the move is valid
	  //One stone only cannot push
	  if (from1==from2) {
		  //Must move into a gap
		  if (to2.getColor() == null) {
			  //System.out.println("One stone, OK");
			  return true;
		  }
		  else {
			  System.out.println("Moving one stone to an occupied space");
			  return false;
		  }
	  }
	  //Two consequent stones
	  if (new Vector<Cell>(Arrays.asList(from1.getNeighbors(board))).contains(from2)) {
		  Vector<Cell> from1Neighbors = new Vector<Cell>(Arrays.asList(from1.getNeighbors(board)));
		  int direction = from1Neighbors.indexOf(from2);
		  //Check if we are pushing a stone
		  if (from2.getNeighbors(board)[direction]==to2) { //Inline move
			  if (to2.getColor()==null) {
				  //System.out.println("Valid 2 inline");
				  return true;
			  }
			  //We already know that to2 is not empty, so if we are here, it means it's the opponent's color
			  //Check if we can push it
			  if ((to2.getNeighbors(board)[direction]).getColor()==null) {
				  //System.out.println("Pushing one stone");
				  return true;
			  }
		  } else { //Broadside move, both must be empty
			  Vector<Cell> from2Neighbors = new Vector<Cell>(Arrays.asList(from2.getNeighbors(board)));
			  direction = from2Neighbors.indexOf(to2);
			  Cell to1 = from1.getNeighbors(board)[direction];
			  if (to1.getColor()==null && to1.isInBoard() && to2.getColor()==null) {
				  //System.out.println("Valid broadside 2");
				  return true;
			  }
			  System.out.println("Broadside 2 to a non-empty space");
			  return false; //Broadside move to a non-empty space
		  }
	  }
	  //Check for three consequent stones
	  Cell intermediate = null;
	  for (int i=0;i<3;i++) {
		  //Check for the intermediate cell
		  if (from1.getNeighbors(board)[i]==from2.getNeighbors(board)[i+3]) 
			  intermediate = from1.getNeighbors(board)[i];
		  if (from2.getNeighbors(board)[i]==from1.getNeighbors(board)[i+3])
			  intermediate = from2.getNeighbors(board)[i];
	  }
	  if (intermediate == null) {
		  System.out.println("Cells are not consecutive");
		  return false;
	  }
	  //If we are here, we are moving three consequent stones
	  //Check colors
	  if (from1.getColor()!=intermediate.getColor()||from2.getColor()!=from1.getColor()) {
		  System.out.println("We don't have the same color");
		  return false;  
	  }
	  //If we are here, we are moving three consequent stones of the same color
	  //If we are here, it means we are moving to a non-empty cell - only possible when pushing
	  //We already know that to2 cannot be of our own color (checked in the beginning)
	  //So it is occupied by the opponent
	  //Check for inline or broadside move
	  Vector<Cell> from1Neighbors = new Vector<Cell>(Arrays.asList(from1.getNeighbors(board)));
	  int direction = from1Neighbors.indexOf(intermediate);
	  Vector<Cell> from2Neighbors = new Vector<Cell>(Arrays.asList(from2.getNeighbors(board)));
	  if (from2Neighbors.elementAt(direction)!=to2) { //Broadside move
		  direction = from2Neighbors.indexOf(to2);
		  //Make sure all are empty
		  if (!(to2.isInBoard() && from1.getNeighbors(board)[direction].isInBoard() && intermediate.getNeighbors(board)[direction].isInBoard())) {
			  System.out.println("Broadside 3 outside the board");
			  return false;
		  }
		  if (to2.getColor()!=null) {
			  System.out.println("Broadside 3 to a non-empty space");
			  return false;
		  }
		  if (from1.getNeighbors(board)[direction].getColor()!=null) {
			  System.out.println("Broadside 3 to a non-empty space");
			  return false;
		  }
		  if (intermediate.getNeighbors(board)[direction].getColor()!=null) {
			  System.out.println("Broadside 3 to a non-empty space");
			  return false;
		  }
		  //System.out.println("Broadside 3 OK");
		  return true;
	  }
	  //Only inline moves where to1 is enemy can reach this point
	  if (to2.getColor()==null) {
		  //System.out.println("Three inline empty");
		  return true;
	  }
	  Cell afterTo2 = to2.getNeighbors(board)[direction];
	  if (afterTo2.getColor()==null) {
		  //System.out.println("Three pushing one");
		  return true; //Pushing one stone
	  }
	  if (afterTo2.getColor()==from1.getColor()) {
		  System.out.println("Cannot push, we are occupying it");
		  return false; //Cannot push, we are occupying it
	  }
	  //Inline move followed by two stones of the opposite color. Must move into gap.
	  Cell gap = afterTo2.getNeighbors(board)[direction];
	  if (gap.getColor()==null) {
		  //System.out.println("Three pushing two");
		  return true;
	  }
	  System.out.println("Three pushing three");
	  return false;
  }


}
