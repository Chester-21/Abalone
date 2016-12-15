package gr.auth.ee.dsproject.abalone.event;

import java.util.EventObject;

public class GameStoppedEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2006325841850479521L;

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

	public GameStoppedEvent(gr.auth.ee.dsproject.abalone.Game g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

}
