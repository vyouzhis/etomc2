package com.lib.Quartz;

import java.io.IOException;

import org.ppl.etc.globale_config;
import org.ppl.plug.Quartz.CronQuartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class RealTimeStockDB extends CronQuartz implements Job {
	public RealTimeStockDB() {
		// TODO Auto-generated constructor stub
		String className = null;
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub

		if(globale_config.allSession.size() == 0)return;
		
		String sDB = getStockDB();
		try {
			for (String id : globale_config.allSession.keySet()) {

				globale_config.allSession.get(id).getBasicRemote()
						.sendText(sDB);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return "Group_" + SliceName(stdClass);
	}

	@Override
	public String cronSchedule() {
		// TODO Auto-generated method stub
		String cron = mConfig.GetValue("Quartz.RealTimeStockDB");
		cron = cron.replace("\"", "");
		return cron;
	}

	@Override
	public int isRun() {
		// TODO Auto-generated method stub
		return mConfig.GetInt("Quartz.RealTimeStockDB.isRun");
	}

	private String getStockDB() {
		Shell shell = null;
		String out = "";
		try {
			shell = new SSHByPassword("192.168.122.151", 22, "root",
					"!@#qazwsx");

			out = new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo_realtime.py");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

}
