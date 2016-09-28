package com.lib.Quartz;

import java.io.IOException;

import org.ppl.plug.Quartz.CronQuartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class UpdateAllStockMarket extends CronQuartz implements Job {
	String nowTime = null;

	public UpdateAllStockMarket() {
		// TODO Auto-generated constructor stub
		String className = null;
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);

		nowTime = DateFormat((long) time(), "yyyy-MM-dd");
		echo("UpdateAllStockMarket start ...." + nowTime);
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		nowTime = DateFormat((long) time(), "yyyy-MM-dd");
		echo("UpdateAllStockMarket execute  ...." + nowTime);
		Shell shell = null;
		try {
			shell = new SSHByPassword(mConfig.GetValue("pythonIp"), 22,
					mConfig.GetValue("pythonUser"), "!@#qazwsx");

			String out = new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo.py");

			new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo.py hs300");
			new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo.py sh");
			new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo.py sz");
			new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo.py sz50");
			echo(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String cron = mConfig.GetValue("Quartz.UpdateAllStockMarket");
		cron = cron.replace("\"", "");
		return cron;
	}

	@Override
	public int isRun() {
		// TODO Auto-generated method stub
		return mConfig.GetInt("Quartz.UpdateAllStockMarket.isRun");
	}

}
