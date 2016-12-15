package gr.auth.ee.dsproject.abalone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Vector;

import gr.auth.ee.dsproject.abalone.event.*;
import gr.auth.ee.dsproject.abalone.util.*;

/**
 * Class Platform
 * 
 * This class is in charge of all GUI-related functions. 
 */
public class Platform extends javax.swing.JFrame {
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -4925817723073216190L;

	/** Creates new form Platform */
	public Platform() {
		game = new Game();
		initComponents();
		initEvents();
		boardPanel.repaint();
	}

	/**
	 * Initialize the game players according to the user's choice and disable the
	 * comboboxes. Called right before the game starts.
	 */
	private void preparePlayers() {
		//Initialize player types
	}

	/**
	 *  Reconstruct the four panels corresponding to the four players.
	 */
	private void refreshPlayers() {

		blackScoreLabel.setText(game.blackPlayer.getDeadPieces()+" marbles out");
		whiteScoreLabel.setText(game.whitePlayer.getDeadPieces()+" marbles out");
		repaint();
	}

	/**
	 * Reconstruct the inventory corresponding to the current player
	 * (The clickable one at the bottom)
	 */
	private void repaintCurrent() {

		//currentPanel.doLayout();
		refreshPlayers();
	}

	/**
	 * This method adds the necessary listeners to the board's events and calls the
	 * appropriate methods for each event.
	 * It also adds event listeners to the GUI elements.
	 */
	private void initEvents() {

		motion_listener = new BoardMouseMotionListener();
		mouse_listener = new BoardMouseListener();


		//New game started
		game.addGameStartedEventListener(new GameStartedEventListener() {
			public void gameStarted(GameStartedEvent e) {
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				boardPanel.repaint();
				repaintCurrent();
				refreshPlayers();
				fromCell1 = null;
				fromCell2 = null;
				toCell2 = null;
				if (game.getNext().getType()=="Human") {
					boardPanel.addMouseMotionListener(motion_listener);
					boardPanel.addMouseListener(mouse_listener);
				} else {
					boardPanel.removeMouseMotionListener(motion_listener);
					boardPanel.removeMouseListener(mouse_listener);
				}
				statusBar.setText("Black to play. Select marble 1 (start of range)");
			}
		}
		);

		//Game is over
		game.addGameStoppedEventListener(new GameStoppedEventListener() {
			public void gameStopped(GameStoppedEvent e) {
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
				boardPanel.removeAll();
				boardPanel.doLayout();
				boardPanel.repaint();
				fromCell1 = null;
				fromCell2 = null;
				toCell2 = null;

				blackType.setEnabled(true);
				whiteType.setEnabled(true);

				boardPanel.removeMouseMotionListener(motion_listener);
				boardPanel.removeMouseListener(mouse_listener);
				statusBar.setText("Game stopped");
			}
		}
		);

		//Move played
		game.addMovePlayedEventListener(new MovePlayedEventListener() {
			public void movePlayed(MovePlayedEvent e) {
				boardPanel.removeMouseMotionListener(motion_listener);
				boardPanel.removeMouseListener(mouse_listener);
				fromCell1 = null;
				fromCell2 = null;
				toCell2 = null;
				boardPanel.repaint();
				repaintCurrent();
				if (game.getNext().getType()=="Human") {
					boardPanel.addMouseMotionListener(motion_listener);
					boardPanel.addMouseListener(mouse_listener);
				} else {
					boardPanel.removeMouseMotionListener(motion_listener);
					boardPanel.removeMouseListener(mouse_listener);
				}
				if (game.getNext().isBlack()) {
					statusBar.setText("White to play. Select marble 1 (start of range)");
				} else {
					statusBar.setText("Black to play. Select marble 1 (start of range)");
				}
			}
		}
		);

		//Move previewed
		game.addMovePreviewEventListener(new MovePreviewEventListener() {
			public void movePlayed(MovePreviewEvent e) {
				statusBar.setText("Invalid move: check console for details");
				boardPanel.repaint();
			}
		});

		//User request to start a new game
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preparePlayers();
				game.newGame();
			}
		});

		//User request to end the current game
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.gameOver();
			}
		});
	}

	private void cellRightClicked() {
		// Just cancel all moves
		fromCell1 = null;
		fromCell2 = null;
		toCell2 = null;
		boardPanel.repaint();
		if (game.getNext().isBlack()) {
			statusBar.setText("Black to play. Select marble 1 (start of range)");
		} else {
			statusBar.setText("White to play. Select marble 1 (start of range)");
		}
	}
	
	private void cellClicked(int x, int y) {
		if (fromCell1 == null) {
			if ((game.board[x][y].getColor() == Color.black && game.getNext().isBlack()) ||
					(game.board[x][y].getColor() == Color.white && !game.getNext().isBlack())) {
				fromCell1 = game.board[x][y];
				boardPanel.highlightDisc(boardPanel.getGraphics(), x, y, Color.blue);
				statusBar.setText("Select marble 2 (end of range) or right-click to cancel move");
			} else {
				return;
			}
		} else if (fromCell2 == null) {
			if ((game.board[x][y].getColor() == Color.black && game.getNext().isBlack()) ||
					(game.board[x][y].getColor() == Color.white && !game.getNext().isBlack())) {
				fromCell2 = game.board[x][y];
			} else {
				return;
			}
			Vector<Cell> highlights = getFromToCells(fromCell1, fromCell2);
			int cells = highlights.size();
			switch(cells) {
			case 0:
				boardPanel.highlightDisc(boardPanel.getGraphics(), fromCell1.getX(), fromCell1.getY(), fromCell1.getColor());
				boardPanel.highlightDisc(boardPanel.getGraphics(), x, y, Color.blue);
				fromCell2 = fromCell1;
				fromCell1 = null;
				break;
			case 1:
				break;
			case 2:
				boardPanel.highlightDisc(boardPanel.getGraphics(), x, y, Color.blue);
				break;
			case 3:
				boardPanel.highlightDisc(boardPanel.getGraphics(), x, y, Color.blue);
				Cell intermediate = highlights.elementAt(1);
				boardPanel.highlightDisc(boardPanel.getGraphics(), intermediate.getX(), intermediate.getY(), Color.blue);
				break;
			}
			statusBar.setText("Select destination of marble 2 or right-click to cancel move");
		} else {
			toCell2 = game.board[x][y];
			Move newMove = new Move(fromCell1, fromCell2, toCell2);
			game.playMove(newMove);
			fromCell1 = null;
			fromCell2 = null;
			toCell2 = null;
			boardPanel.repaint();

		}

	}

	private Vector<Cell> getFromToCells(Cell from, Cell to) {
		Vector<Cell> returnme = new Vector<Cell>();
		if (from == to) {
			returnme.add(from);
		} else if (new Vector<Cell>(Arrays.asList(from.getNeighbors(game.board))).contains(to)) {
			returnme.add(from);
			returnme.add(to);
		} else {
			Cell intermediate = null;
			for (int i=0;i<3;i++) {
				//Check for the intermediate cell
				if (from.getNeighbors(game.board)[i]==to.getNeighbors(game.board)[i+3]) 
					intermediate = from.getNeighbors(game.board)[i];
				if (to.getNeighbors(game.board)[i]==from.getNeighbors(game.board)[i+3])
					intermediate = to.getNeighbors(game.board)[i];
			}
			if (intermediate == null) return returnme;
			returnme.add(from);
			returnme.add(intermediate);
			returnme.add(to);
		}
		return returnme;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 */
	private void initComponents() {

		game = new Game();
		boardPanel = new BoardPanel(game);
		
		topPanel = new javax.swing.JPanel();
		bottomPanel = new javax.swing.JPanel();
		blackPanel = new javax.swing.JPanel();
		whitePanel = new javax.swing.JPanel();
		bigPanel = new javax.swing.JPanel();
		line = new javax.swing.JSeparator();
		blackScoreLabel = new javax.swing.JLabel(" marbles out");
		whiteScoreLabel = new javax.swing.JLabel(" marbles out");
		startButton = new javax.swing.JButton("Start game");
		stopButton = new javax.swing.JButton("Stop game");
		stopButton.setEnabled(false);
		blackLabel = new javax.swing.JLabel("Black player");
		whiteLabel = new javax.swing.JLabel("White player");
		blackTypeLabel = new javax.swing.JLabel("Type:");
		whiteTypeLabel = new javax.swing.JLabel("Type:");
		blackType = new javax.swing.JComboBox(availablePlayers);
		whiteType = new javax.swing.JComboBox(availablePlayers);
		statusBar = new javax.swing.JLabel("Game stopped");

		blackType.setSelectedItem(game.getPlayerForColor(java.awt.Color.black).getType());
		whiteType.setSelectedItem(game.getPlayerForColor(java.awt.Color.white).getType());

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		blackPanel.setLayout(new java.awt.BorderLayout());
		whitePanel.setLayout(new java.awt.BorderLayout());
		blackPanel.add(blackLabel,BorderLayout.NORTH);
		blackPanel.add(blackTypeLabel,BorderLayout.WEST);
		blackPanel.add(blackType,BorderLayout.EAST);
		blackPanel.add(blackScoreLabel,BorderLayout.SOUTH);
		whitePanel.add(whiteLabel,BorderLayout.NORTH);
		whitePanel.add(whiteTypeLabel,BorderLayout.WEST);
		whitePanel.add(whiteType,BorderLayout.EAST);
		whitePanel.add(whiteScoreLabel,BorderLayout.SOUTH);

		bottomPanel.setLayout(new java.awt.BorderLayout());
		bottomPanel.add(blackPanel,java.awt.BorderLayout.WEST);
		bottomPanel.add(whitePanel,java.awt.BorderLayout.EAST);
		topPanel.setLayout(new java.awt.FlowLayout());
		topPanel.add(startButton);
		topPanel.add(stopButton);
		bigPanel.setLayout(new java.awt.BorderLayout());
		bigPanel.add(topPanel, java.awt.BorderLayout.NORTH);
		bigPanel.add(boardPanel, java.awt.BorderLayout.CENTER);
		bigPanel.add(bottomPanel, java.awt.BorderLayout.SOUTH);
		getContentPane().add(bigPanel, java.awt.BorderLayout.NORTH);
		getContentPane().add(line, java.awt.BorderLayout.CENTER);
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);

		boardPanel.repaint();

		pack();
		this.setResizable(false);
		this.setTitle("Abalone, Data Structures 2010-11, THMMY AUTH");
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				//Create a new GUI
				new Platform().setVisible(true);
			}
		});
	}


	//Variables declaration
	private javax.swing.JPanel topPanel;
	private javax.swing.JPanel bottomPanel;
	private javax.swing.JPanel blackPanel;
	private javax.swing.JPanel whitePanel;
	private javax.swing.JPanel bigPanel;
	private javax.swing.JSeparator line;
	private javax.swing.JLabel blackScoreLabel;
	private javax.swing.JLabel whiteScoreLabel;
	private BoardPanel boardPanel;
	private javax.swing.JButton startButton;
	private javax.swing.JButton stopButton;
	private javax.swing.JLabel blackLabel;
	private javax.swing.JLabel whiteLabel;
	private javax.swing.JLabel blackTypeLabel;
	private javax.swing.JLabel whiteTypeLabel;
	private javax.swing.JComboBox blackType;
	private javax.swing.JComboBox whiteType;
	private String[] availablePlayers = { "Human" };
	private Game game;
	private Cell fromCell1;
	private Cell fromCell2;
	private Cell toCell2;
	private BoardMouseMotionListener motion_listener;
	private BoardMouseListener mouse_listener;
	private javax.swing.JLabel statusBar;
	// End of variables declaration

	//Classes used for grabbing clicks on the board and fetching the cursor's position
	private class BoardMouseMotionListener extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {

		}
	}

	private class BoardMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			
			if (e.getButton()==MouseEvent.BUTTON1) {
				int c = boardPanel.getCellSize();
				double click_pos = ((360-e.getY())/(0.8*c));
				int box_x = (int)Math.floor(click_pos+1);
				int box_y = (int)(((e.getX()-120.0)/c)+(0.5*(box_x-1))+1);
				//System.out.println(box_x+","+box_y);
				if (click_pos < 0 || click_pos > 9 || box_y < 0 || box_y > 9 ) {
					statusBar.setText("Please click inside the board");
					return;
				}
				else if (!game.board[box_x][box_y].isInBoard()) {
					statusBar.setText("Please click inside the board");
					return;
				}
				else if (click_pos%1>0.68) {
					statusBar.setText("Please click closer to the center of the cell");
					return;
				}
				cellClicked(box_x, box_y);
			} else if (e.getButton()==MouseEvent.BUTTON3){
				cellRightClicked();
			}
		}
		public void mouseExited(MouseEvent e) {
			//game.revertTemp();
		}

	}
}
