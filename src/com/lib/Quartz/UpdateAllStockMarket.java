package com.lib.Quartz;

import org.ppl.plug.Quartz.CronQuartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UpdateAllStockMarket extends CronQuartz implements Job {
	String nowTime = null;
	public UpdateAllStockMarket() {
		// TODO Auto-generated constructor stub
		String className = null;
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		
		//int week = toInt(DateFormat((long)time(), "W"));
		
		nowTime = DateFormat((long)time(), "yyyy-MM-dd");
		echo("UpdateAllStockMarket ...."+nowTime);
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return "Group_"+SliceName(stdClass);
	}

	@Override
	public String cronSchedule() {
		// TODO Auto-generated method stub		
		String cron =  mConfig.GetValue("Quartz.UpdateAllStockMarket");
		cron = cron.replace("\"", ""); 
		return cron;
		 
		//return "0 */5 * * 1-5 ?";
	}

	@Override
	public int isRun() {
		// TODO Auto-generated method stub
		return 0;
	}

}
