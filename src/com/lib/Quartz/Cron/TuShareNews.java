package com.lib.Quartz.Cron;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.db.HikariConnectionPool;
import org.ppl.io.TimeClass;
import org.ppl.plug.Quartz.CronQuartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;
import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class TuShareNews extends CronQuartz implements Job {
	public TuShareNews() {
		// TODO Auto-generated constructor stub
		String className = null;
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub

		HikariConnectionPool hcp = HikariConnectionPool.getInstance();
		hcp.GetCon();
		
	//	String nowTime = DateFormat((long) time(), "yyyy-MM-dd HH:mm:ss");
		//echo("TuShareNews execute  ...." + nowTime);

		Shell shell = null;
		try {
			shell = new SSHByPassword(mConfig.GetValue("pythonIp"), 22,
					mConfig.GetValue("pythonUser"), "!@#qazwsx");

			String out = new Shell.Plain(shell).exec("python "
					+ mConfig.GetValue("pythonPath") + "/tushare_news.py");
			
			out = out.substring(out.indexOf("{"));
			
			if (out != null && out.length() > 10) {
				Map<String, Object> news = JSON.parseObject(out, Map.class);
				String format = "('%d', '%d', '%s','%d', '%s', '%d')";
				int pid = 0;
				int sid = 0;
				int uid = 7;
				String Html = "";
				
				TimeClass tClass = TimeClass.getInstance();
				String Year = DateFormat( tClass.time(), "yyyy");
				String sql = "";
				String RealTime = "";
				
				for (String key : news.keySet()) {

					Map<String, List<String>> Cont = (Map<String, List<String>>) news
							.get(key);
					
					if(Cont==null){
						//echo(news.get(key));
						continue;
					}
					
					if(!Cont.containsKey("url") || !Cont.containsKey("title") || !Cont.containsKey("content")){
						//echo(Cont);
						continue;
					}
					
					for (int i = 0; i < Cont.get("url").size(); i++) {
						if(Cont.get("url").get(i)==null || Cont.get("title").get(i)==null || Cont.get("content").get(i)==null){
							//echo(Cont);
							continue;
						}
												
						if (sql.length() > 0) {
							sql += ",";
						}
						
						Html = "<a  target=\"_blank\" href=\""
								+ Cont.get("url").get(i) + "\"><h3>"
								+ Cont.get("title").get(i) + "</h3></a><br>"
								+ Cont.get("content").get(i).replace("\"", "\\\"");

						//echo(Cont.get("title").get(i));
						RealTime = Year+"-"+Cont.get("time").get(i)+":00";
						
						int now = Integer.valueOf(tClass.DateToTimeStamp(RealTime) + "");
						
						sql += String.format(format, pid, sid, key, uid, Html,
								now);
						
					}

				}
				if (sql.length() > 20) {
					Save(sql);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//echo("msg: "+e.getMessage());
		} finally {
			hcp.free();
		}
		//echo("end ...");
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return "Group_" + SliceName(stdClass);
	}

	@Override
	public String cronSchedule() {
		// TODO Auto-generated method stub
		String cron = mConfig.GetValue("Quartz.TuShareNews");
		cron = cron.replace("\"", "");
		return cron;
	}

	@Override
	public int isRun() {
		// TODO Auto-generated method stub
		return mConfig.GetInt("Quartz.TuShareNews.isRun");
	}

	private void Save(String sql) {
		String format = "INSERT INTO `stock_user_talk` (`pid`, `sid`,`code`, `uid`, `msg`, `ctime`) VALUES "
				+ sql;
				
		try {
			insert(format);
			CommitDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
