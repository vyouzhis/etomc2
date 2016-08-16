package org.ppl.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ppl.BaseClass.BaseCronThread;
import org.ppl.BaseClass.LibThread;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

public class CronThread extends LibThread {
	private Map<String, Integer> cronMap;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		ListQueue();
	}

	@Override
	public void ListQueue() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
	
		cronMap = new HashMap<String, Integer>();
		Config mConfig = new Config(globale_config.Config);
		int cronDelay = mConfig.GetInt("cronDelay");
		for (String ps : ucl.getPackList()) {
			try {
				Class<?> clazz = Class.forName(ps);

				if (clazz.getSuperclass().equals(BaseCronThread.class)) {					
					String name = SliceName(ps);
					cronMap.put(name, 0);					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(cronMap.size());
		while (true) {
		//	System.out.println("start...");
			
			
			for (String key : cronMap.keySet()) {
				// System.out.print("KEY:"+key);	
				ThreadCronRun tpr = new ThreadCronRun(key, cronMap.get(key));
				cronMap.put(key, tpr.etime());
				cachedThreadPool.execute(tpr);										
			}
			
			//cachedThreadPool.shutdown();
			try {
				Thread.sleep(60 * cronDelay * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
