package org.ppl.core;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.ppl.Module.ModuleBind;
import org.ppl.db.HikariConnectionPool;
import org.ppl.etc.globale_config;
import org.ppl.plug.Quartz.RunQuartz;
import org.ppl.plug.R.Rlan;

import com.alibaba.fastjson.JSON;
import com.google.inject.Guice;

/*
 * Thread safe are (when we do not use SingleThreadModel):
 * - request, response parameters, local parameters (inside doXXX() method)
 *
 * Thread safe are (when we use SingleThreadModel):
 * - request, response parameters, local parameters (inside doXXX() method), instance parameters (inside servlet class) 
 * 
 */
@WebListener
public class ServletApplicationLifeListener extends PObject implements
		ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

		HikariConnectionPool.getInstance();
		globale_config.GDB = new HashMap<>();
		// globale_config.RengineJava = new Rengine(new String[] { "--no-save"
		// }, false, null);

		InitPackList();



		new Rlan();

//		globale_config.RapidListQueue = new ArrayList<>();
//		globale_config.RapidList = new HashMap<>();
//		globale_config.CronListQueue = new HashMap<>();
		
		int autorun = mConfig.GetInt("autorun");
		if (autorun == 1) {
			// Thread dt = new Thread(new RapidThread(), "dt_");
			// dt.start();
			//
			// Thread cron = new Thread(new CronThread(), "cron_");
			// cron.start();
			RunQuartz rq = new RunQuartz();
			rq.CronQuartz();
		}

	}

	// @SuppressWarnings("unchecked")
	// private void Auto() {
	// String runJson = mConfig.GetValue("rum.module");
	// List<String> runList = JSON.parseObject(runJson, List.class);
	// Injector injector = Guice.createInjector(new ModuleBind());
	// for (String rl : runList) {
	// BaseRapidThread libLan = (BaseRapidThread) injector.getInstance(Key.get(
	// BaseRapidThread.class, Names.named(rl)));
	// libLan.Run();
	// }
	// }

	@SuppressWarnings("unchecked")
	private void InitPackList() {

		String packs = mConfig.GetValue("base.packs");
		if (packs == null) {
			echo("error InitPackList !");
			System.exit(-1);
			return;
		}
		List<String> pList = JSON.parseObject(packs, List.class);
		for (String p : pList) {
			String ps = this.getClass().getResource("/").getPath() + p;
			findPack(ps);
		}

		globale_config.injector = Guice.createInjector(new ModuleBind());
	}

}
