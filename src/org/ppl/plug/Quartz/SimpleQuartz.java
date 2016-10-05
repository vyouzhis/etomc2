package org.ppl.plug.Quartz;

import org.ppl.db.DBSQL;

public abstract class SimpleQuartz extends DBSQL {
	public SimpleQuartz() {
		// TODO Auto-generated constructor stub
		
	}
	
	public abstract String getGroup();
	public abstract int withRepeatCount();
	public abstract int withIntervalInSeconds();
	public String getTrigger() {
		// TODO Auto-generated method stub
		return "Trigger_" + SliceName(stdClass) + time();
	}
	public String getArg() {
		return "Json";
	}
}
