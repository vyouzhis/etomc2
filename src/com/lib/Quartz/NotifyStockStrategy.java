package com.lib.Quartz;

import org.ppl.plug.Quartz.CronQuartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @author vyouzhi
 *  定时运行 策略 并通过邮件通知用户，第二期开发
 *
 */
public class NotifyStockStrategy extends CronQuartz implements Job{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		String className = null;
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cronSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isRun() {
		// TODO Auto-generated method stub
		return 0;
	}

}
