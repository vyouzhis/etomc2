package org.ppl.plug.Quartz;

import org.ppl.core.PObject;

public abstract  class CronQuartz extends PObject {
	
	public abstract String getGroup();		
	public abstract String cronSchedule();
	public abstract int isRun();
	public String getTrigger() {
		// TODO Auto-generated method stub
		return "Trigger_"+SliceName(stdClass);
	}
}
