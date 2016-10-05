package com.lib.api;

import java.io.IOException;

import org.ppl.BaseClass.BaseSurface;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class EMC2WebSocketHttp  extends BaseSurface{
	private String className = null;
	public EMC2WebSocketHttp() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		setAjax(true);
		
		String init = getStockDB();
		super.setHtml(init);
	}
	
	
	private String getStockDB() {
		Shell shell = null;
		String out = "";
		try {
			shell = new SSHByPassword(mConfig.GetValue("pythonIp"), 22,
					mConfig.GetValue("pythonUser"), "!@#qazwsx");

			out = new Shell.Plain(shell)
					.exec("python "+mConfig.GetValue("pythonPath")+"/tushare_mongo_realtime.py");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}
