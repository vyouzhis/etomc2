package org.ppl.core;

import java.util.HashMap;
import java.util.Map;

import org.ppl.BaseClass.BaseRapidThread;
import org.ppl.common.function;
import org.ppl.etc.globale_config;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class ThreadRapidRun extends function implements Runnable {
	private String myKey;
	private Object myMsg;

	public ThreadRapidRun(String key, Object message) {
		// TODO Auto-generated constructor stub
		myKey = key;
		myMsg = message;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		rapidRun();
	}

	private void rapidRun() {
	//	Injector injector = globale_config.injector;
//		BaseRapidThread rapid = (BaseRapidThread) injector.getInstance(Key.get(
//				BaseRapidThread.class, Names.named(myKey)));
//		boolean stop = rapid.Stop();
//		Map<String, Object> arg;
//		int munber = 0;
//		int rtime = time();
//
//		if (globale_config.RapidList.containsKey(myKey)) {
//			arg = globale_config.RapidList.get(myKey);
//			stop = (boolean) arg.get("stop");
//			munber = toInt(arg.get("munber"));
//		} else {
//			arg = new HashMap<String, Object>();
//			arg.put("stop", stop);
//			arg.put("name", rapid.title());
//			synchronized (globale_config.RapidList) {
//				globale_config.RapidList.put(myKey, arg);
//			}
//		}
//
//		synchronized (globale_config.RapidList) {
//			globale_config.RapidList.get(myKey).put("rtime", rtime);
//			globale_config.RapidList.get(myKey).put("isrun", 1);
//		}
//
//		rapid.mailbox(myMsg);
//		try {
//			rapid.Run();
//			synchronized (globale_config.RapidList) {
//				globale_config.RapidList.get(myKey).put("munber", munber);
//				globale_config.RapidList.get(myKey).put("isrun", 0);
//			}
//		} finally {
//			rapid.free();
//		}
//		rapid = null;
//		echo("rapid end!!");
	}
}
