package gr.auth.ee.dsproject.abalone.event;

import java.util.EventObject;

public class MovePreviewEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5295421485407732788L;

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

	public MovePreviewEvent(gr.auth.ee.dsproject.abalone.Move m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

}
