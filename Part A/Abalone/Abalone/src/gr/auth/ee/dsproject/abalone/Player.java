
package gr.auth.ee.dsproject.abalone;


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

  //
  // Other methods
  //

  /**
   * @param        game
   */
  public void getNextMove( gr.auth.ee.dsproject.abalone.Game game )
  {
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


  /**
   * @param        game
   */
  public void findAllMoves( gr.auth.ee.dsproject.abalone.Game game )
  {
  }


}
                                                                                                           