package org.ppl.core;

import java.util.HashMap;
import java.util.Map;

import org.ppl.BaseClass.BaseCronThread;
import org.ppl.common.function;
import org.ppl.etc.globale_config;
import org.ppl.io.TimeClass;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class ThreadCronRun extends function implements Runnable {
	private String tpKey;
	private int sTime = 0;
	private boolean isRun=false;
	private BaseCronThread cron=null;
	private String title;
	public ThreadCronRun(String key, int now) {
		// TODO Auto-generated constructor stub
		tpKey = key;
		sTime = now;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//echo("i am run!");
		ThreadRuns();
	}
	
	public int etime() {
//		isRun=false;
//		//System.out.println("i am etime!");
//		TimeClass tc = TimeClass.getInstance();
//		int now = time();
//		Map<String, Object> arg;
//		
//		int nowHour = Integer.valueOf(tc.TimeStamptoDate(tc.time(), "hh"));
//		int nowDay = Integer.valueOf(tc.TimeStamptoDate(tc.time(), "dd"));
//		Injector injector = globale_config.injector;
//		cron = (BaseCronThread) injector.getInstance(Key.get(
//				BaseCronThread.class, Names.named(tpKey)));
//		
//		title = SliceName(cron.getBindName());
//		
//		int minu = cron.minute();
//		int hour = cron.hour();
//		int day = cron.day();
//		boolean isStop = cron.isStop();
//		
//		if(globale_config.CronListQueue.containsKey(title)){
//			 minu = (int)globale_config.CronListQueue.get(title).get("minute");
//			 hour = (int)globale_config.CronListQueue.get(title).get("hour");
//			 day = (int)globale_config.CronListQueue.get(title).get("day");
//			 isStop = (boolean)globale_config.CronListQueue.get(title).get("isStop");
//			 synchronized (globale_config.CronListQueue) {															
//				if(globale_config.CronListQueue.get(title).get("name").toString().length()==0){
//					String name = cron.title();
//					if(name!=null && name.length() > 0)
//						globale_config.CronListQueue.get(title).put("name", name);
//				}
//			}
//		}else{
//			arg = new HashMap<String, Object>();
//			arg.put("name", "");
//			arg.put("minute", minu);
//			arg.put("hour", hour);
//			arg.put("day", day);
//			arg.put("isStop", isStop);
//			arg.put("rtime", time());
//			arg.put("munber", 0);
//			
//			synchronized (globale_config.CronListQueue) {				
//				globale_config.CronListQueue.put(title, arg);
//			}
//		}
//		
//		if (isStop == true) {
//			cron.free();
//			return 0;
//		}
//
//		int sleepTime = sTime;
//		
//		int newTime = 0;
//		if (day == 0 && hour == 0 && sleepTime < now) {
//			newTime = now + minu * 60;
//		} else if (day == 0 && hour == nowHour && sleepTime < now) {
//			newTime = now + minu * 60 + hour * 60 * 60;
//		} else if (day == nowDay && sleepTime < now) {
//			newTime = now + hour * 60 * 60 + minu * 60 + 86400;
//		} else {
//			cron.free();
////			echo("continue key:" + tpKey + " sleepTime:"
////					+ sleepTime + " day:" + day + " hour:" + hour + " now:"
////					+ now);
//			return 0;
//		}
//
//		isRun = true;
		return 0;
	}
	
	private void ThreadRuns() {
//		if(isRun && cron!=null){
//			synchronized (globale_config.CronListQueue) {		
//				globale_config.CronListQueue.get(title).put("rtime", time());
//				int munber = (int) globale_config.CronListQueue.get(title).get("munber");
//				globale_config.CronListQueue.get(title).put("munber", munber+1);												
//			}
//			cron.Run();
//			cron.free();
//		}
//		cron = null;
//		else {
//			echo("no run!");
//		}
	}

}
