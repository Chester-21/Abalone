package gr.auth.ee.dsproject.abalone;

import java.awt.Color;
import java.util.Arrays;
import java.util.Vector;
import gr.auth.ee.dsproject.abalone.event.*;

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
public class Game implements Runnable {
	/**
	 * This class represents one game.
	 * Fields:
	 * x, y: The size of the board, including one surrounding line for moves "outside" the board.
	 * board: An array of cells that represents the board.
	 * blackPlayer, whitePlayer: The two players.
	 * next: A pointer to the next player.
	 * isCopy: Set to true, when the game is a copy of another game object (Used in Minimax)
	 */

	//
	// Fields
	//

	int delayBetweenMovesInMillis = 1000; //Change this to change game speed!!!!!
	int x=11;
	int y=11;
	boolean isCopy=false;
	Cell[][] board;
	Player next;
	Player blackPlayer;
	Player whitePlayer;
	Thread thread;
	private MovePlayedEventListener movePlayedListener = new MovePlayedEventListener() {
		public void movePlayed(MovePlayedEvent e) {
			getNextPlayerMove();
		}
	};

	//
	// Constructors
	//
	public Game () { 
		this(false);
	}

	public Game(boolean isCopy) {
		this.isCopy = isCopy;
		board = new Cell[x][y];
		for (int i=0;i<x;i++) {
			for (int j=0;j<y;j++) {
				board[i][j] = new Cell(i,j);
			}
		}
		blackPlayer = new Player("Human",true);
		whitePlayer = new Player("Human",false);
		reset();
	};

	//
	// Methods
	//


	//
	// Accessor methods
	//

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

	/**
	 * Get the value of Board
	 * @return the value of Board
	 */
	public Cell[][] getBoard ( ) {
		return board;
	}

	/**
	 * Set the value of next
	 * @param newVar the new value of next
	 */
	public void setNext ( Player newVar ) {
		next = newVar;
	}

	/**
	 * Get the value of next
	 * @return the value of next
	 */
	public Player getNext ( ) {
		return next;
	}
	
	/**
	 * Get current player
	 * @return current player
	 */
	public Player getCurrent ( ) {
		if (next==blackPlayer) return whitePlayer;
		else return blackPlayer;
	}

	/**
	 * Set the value of blackPlayer
	 * @param newVar the new value of blackPlayer
	 */
	public void setBlackPlayer ( Player newVar ) {
		blackPlayer = newVar;
	}

	/**
	 * Get the value of blackPlayer
	 * @return the value of blackPlayer
	 */
	public Player getBlackPlayer ( ) {
		return blackPlayer;
	}

	/**
	 * Set the value of whitePlayer
	 * @param newVar the new value of whitePlayer
	 */
	public void setWhitePlayer ( Player newVar ) {
		whitePlayer = newVar;
	}

	/**
	 * Get the value of whitePlayer
	 * @return the value of whitePlayer
	 */
	public Player getWhitePlayer ( ) {
		return whitePlayer;
	}


	/**
	 * Get the value of isCopy
	 * @return the value of isCopy
	 */
	public boolean isCopy( ) {
		return isCopy;
	}

	//
	// Other methods
	//

	/**
	 * Method needed for our thread (in order to not let the game freeze the GUI)
	 * Also calls the first player's move.
	 */
	public void run() {
		//Make sure the GUI refreshes while the game progresses
		addMovePlayedEventListener(movePlayedListener);
		//Initial condition for first player
		getNextPlayerMove();
	}

	/**
	 * Reset the board: clear all colors, reset dead pieces for both players, remove next
	 */
	public void reset(  )
	{
		for (int i=0;i<x;i++) {
			for (int j=0;j<y;j++) {
				board[i][j].setColor(null);
			}
		}
		blackPlayer.resetDeadPieces();
		whitePlayer.resetDeadPieces();
		next = null;
		if (thread != null) thread.stop();

	}

	private void makeDaisyAround(int x, int y, Color color) {
		board[x][y].setColor(color);
		Cell[] neighbors = board[x][y].getNeighbors(board);
		for (int i=0;i<neighbors.length;i++) {
			neighbors[i].setColor(color);
		}
	}


	/**
	 * Checks whether the move given in the argument is valid and plays it.
	 * This method is a wrapper for the playMoveAndFeedback method below.
	 * playMoveAndFeedback returns true or false, according to whether the move was played.
	 * playMove checks the return status and fires the according event.
	 * @param        newMove
	 */
	public void playMove( Move newMove )
	{
		boolean played = playMoveAndFeedback(newMove);
		if (played) {
			if (!isCopy) {
				fireMovePlayedEvent(new MovePlayedEvent(newMove));
			} else {
				fireMovePreviewEvent(new MovePreviewEvent(newMove));
			}
		}
	}
	
	//Checks whether the move is valid, plays it on the board.
	private boolean playMoveAndFeedback(Move newMove) {

		if (!newMove.isValid(board)) return false;
		Cell from1 = newMove.getFrom1();
		Cell from2 = newMove.getFrom2();
		Cell to2 = newMove.getTo2();
		if (from1==from2) { //One stone
			to2.setColor(from1.getColor());
			from1.setColor(null);
			return true;
		}
		//Two consequent stones
		if (new Vector<Cell>(Arrays.asList(from1.getNeighbors(board))).contains(from2)) {
			Vector<Cell> from1Neighbors = new Vector<Cell>(Arrays.asList(from1.getNeighbors(board)));
			int direction = from1Neighbors.indexOf(from2);
			//Check if we are pushing a stone
			if (from2.getNeighbors(board)[direction]==to2) { //Inline move
				//Check if we can push
				if (to2.getColor()!=null &&
						to2.getColor()!=from1.getColor()) {
					//Pushing
					Cell afterTo2 = to2.getNeighbors(board)[direction];
					if (!(afterTo2.isInBoard())) 
						getPlayerForColor(to2.getColor()).addDeadPiece();
					else afterTo2.setColor(to2.getColor());
				}
				to2.setColor(from2.getColor());
				from1.setColor(null);
				return true;
			}
			Vector<Cell> from2Neighbors = new Vector<Cell>(Arrays.asList(from2.getNeighbors(board)));
			direction = from2Neighbors.indexOf(to2);
			Cell to1 = from1.getNeighbors(board)[direction];
			to2.setColor(from2.getColor());
			to1.setColor(from1.getColor());
			from1.setColor(null);
			from2.setColor(null);
			return true;
		}
		//Three stones
		Color color = from2.getColor();
		Cell intermediate = null;
		for (int i=0;i<3;i++) {
			//Check for the intermediate cell
			if (from1.getNeighbors(board)[i]==from2.getNeighbors(board)[i+3]) 
				intermediate = from1.getNeighbors(board)[i];
			if (from2.getNeighbors(board)[i]==from1.getNeighbors(board)[i+3])
				intermediate = from2.getNeighbors(board)[i];
		}
		if (intermediate == null) return false; //Shouldn't happen
		//Check for inline or broadside move
		Vector<Cell> from1Neighbors = new Vector<Cell>(Arrays.asList(from1.getNeighbors(board)));
		int direction = from1Neighbors.indexOf(intermediate);
		Vector<Cell> from2Neighbors = new Vector<Cell>(Arrays.asList(from2.getNeighbors(board)));
		if (from2Neighbors.elementAt(direction)!=to2) { //Broadside move
			direction = from2Neighbors.indexOf(to2);
			Cell to1 = from1.getNeighbors(board)[direction];
			Cell to15 = intermediate.getNeighbors(board)[direction];
			to2.setColor(color);
			to1.setColor(color);
			to15.setColor(color);
			from1.setColor(null);
			from2.setColor(null);
			intermediate.setColor(null);
			return true;
		}
		//Only inline moves can reach this point
		Cell afterTo2 = to2.getNeighbors(board)[direction];
		if (to2.getColor()!=null) { //Pushing
			if (afterTo2.getColor()!=null) {
				//Pushing 2
				Cell gap = afterTo2.getNeighbors(board)[direction];
				if (!(gap.isInBoard()))
					getPlayerForColor(to2.getColor()).addDeadPiece();
				else gap.setColor(to2.getColor());
			} else {
				//Pushing 1
				//afterTo2.setColor(to2.getColor());
				if (!(afterTo2.isInBoard()))
					getPlayerForColor(to2.getColor()).addDeadPiece();
				else afterTo2.setColor(to2.getColor());
			}
		}
		to2.setColor(color);
		from1.setColor(null);
		return true;

	}

	/**
	 * This method is called at the beginning of the game, as well as after each move
	 * is played.
	 * It checks whether the game is over (someone has 6 dead pieces), changes the next
	 * pointer accordingly, and calls getNextMove for the next player.
	 */
	public void getNextPlayerMove(  ) {
		if (blackPlayer.getDeadPieces()>=6 || whitePlayer.getDeadPieces()>=6) {
			gameOver();
			return;
		}
		if (next == blackPlayer) {
			next = whitePlayer;
		} else {
			next = blackPlayer;
		}
		if (getCurrent().getType() != "Human") {
			try {
				Thread.sleep(delayBetweenMovesInMillis);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			playMove(getCurrent().getNextMove(this));
		}
	}

	/**
	 */
	public Game copy(  )
	{
		Game clone = new Game(true);
		Cell[][] cBoard = clone.getBoard();
		for (int i=0;i<x;i++) {
			for (int j=0;j<y;j++) {
				cBoard[i][j].setColor(board[i][j].getColor());
			}
		}
		if (next == null) {
			clone.setNext(null);
		}
		else if (next.isBlack()) {
			clone.setNext(clone.getPlayerForColor(Color.black));
		} else {
			clone.setNext(clone.getPlayerForColor(Color.white));
		}
		clone.getBlackPlayer().setDeadPieces(blackPlayer.getDeadPieces());
		clone.getWhitePlayer().setDeadPieces(whitePlayer.getDeadPieces());
		return clone;
	}


	/**
	 * @return       .Player
	 * @param        color
	 */
	public Player getPlayerForColor( Color color )
	{
		if (color == Color.black) return blackPlayer;
		if (color == Color.white) return whitePlayer;
		return null;
	}

	/**
	 */
	public Player[] getPlayers(  )
	{
		Player[] returnme = {blackPlayer, whitePlayer};
		return returnme;
	}


	/**
	 */
	public void newGame(  )
	{
		System.out.println("New game");
		reset();
		makeDaisyAround(2,2,Color.black);
		makeDaisyAround(2,5,Color.white);
		makeDaisyAround(8,5,Color.white);
		makeDaisyAround(8,8,Color.black);
		next = blackPlayer;
		fireGameStartedEvent(new GameStartedEvent(this));
		thread = new Thread(this, "Players thread");
		thread.start();
	}


	/**
	 */
	public void gameOver(  )
	{
		next = null;
		fireGameStoppedEvent(new GameStoppedEvent(this));
		thread.stop();
		removeMovePlayedEventListener(movePlayedListener);
	}

	// Create the listener list
	protected javax.swing.event.EventListenerList listenerList =
		new javax.swing.event.EventListenerList();

	// This method allows classes to register for GameStartedEvents
	public void addGameStartedEventListener(GameStartedEventListener listener) {
		listenerList.add(GameStartedEventListener.class, listener);
	}

	// This method allows classes to unregister for GameStartedEvents
	public void removeGameStartedEventListener(GameStartedEventListener listener) {
		listenerList.remove(GameStartedEventListener.class, listener);
	}

	// This private class is used to fire GameStartedEvents
	void fireGameStartedEvent(GameStartedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i]==GameStartedEventListener.class) {
				((GameStartedEventListener)listeners[i+1]).gameStarted(evt);
			}
		}
	}

	// This method allows classes to register for GameStoppedEvents
	public void addGameStoppedEventListener(GameStoppedEventListener listener) {
		listenerList.add(GameStoppedEventListener.class, listener);
	}

	// This method allows classes to unregister for GameStoppedEvents
	public void removeGameStoppedEventListener(GameStoppedEventListener listener) {
		listenerList.remove(GameStoppedEventListener.class, listener);
	}

	// This private class is used to fire GameStoppedEvents
	void fireGameStoppedEvent(GameStoppedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i]==GameStoppedEventListener.class) {
				((GameStoppedEventListener)listeners[i+1]).gameStopped(evt);
			}
		}
	}

	// This method allows classes to register for MovePlayedEvents
	public void addMovePlayedEventListener(MovePlayedEventListener listener) {
		listenerList.add(MovePlayedEventListener.class, listener);
	}

	// This method allows classes to unregister for MovePlayedEvents
	public void removeMovePlayedEventListener(MovePlayedEventListener listener) {
		listenerList.remove(MovePlayedEventListener.class, listener);
	}

	// This private class is used to fire MovePlayedEvents
	void fireMovePlayedEvent(MovePlayedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i]==MovePlayedEventListener.class) {
				((MovePlayedEventListener)listeners[i+1]).movePlayed(evt);
			}
		}
	}

	// This method allows classes to register for MovePlayedEvents
	public void addMovePreviewEventListener(MovePreviewEventListener listener) {
		listenerList.add(MovePreviewEventListener.class, listener);
	}

	// This method allows classes to unregister for MovePlayedEvents
	public void removeMovePreviewEventListener(MovePreviewEventListener listener) {
		listenerList.remove(MovePreviewEventListener.class, listener);
	}

	// This private class is used to fire MovePlayedEvents
	void fireMovePreviewEvent(MovePreviewEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i]==MovePreviewEventListener.class) {
				((MovePreviewEventListener)listeners[i+1]).movePlayed(evt);
			}
		}
	}



}
