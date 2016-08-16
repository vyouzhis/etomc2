package org.ppl.BaseClass;

import org.ppl.db.DBSQL;
import org.ppl.db.HikariConnectionPool;

public abstract class BaseRapidThread extends DBSQL {		
	public BaseRapidThread() {
		// TODO Auto-generated constructor stub
		HikariConnectionPool hcp = HikariConnectionPool.getInstance();
		hcp.GetCon();
	}
	public abstract String title();
	public abstract void Run();
	public abstract boolean isRun();
	public abstract boolean Stop();
	public abstract void mailbox(Object o);
		
}
