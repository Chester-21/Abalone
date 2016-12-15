package gr.auth.ee.dsproject.abalone.event;

import java.util.EventListener;

public interface GameStoppedEventListener extends EventListener {
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
	public void gameStopped(GameStoppedEvent e);
}
