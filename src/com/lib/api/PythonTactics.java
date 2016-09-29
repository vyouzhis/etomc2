package com.lib.api;

import java.io.IOException;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.io.DesEncrypter;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class PythonTactics extends BaseSurface {
	private String className = null;

	public PythonTactics() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		setAjax(true);
		
		Code();
		
	}

	private void Code() {
		Shell shell = null;
		super.setAjax(true);

		int act = toInt(porg.getKey("act"));
		if(act == 1){
			String init = getStockDB();
			super.setHtml(init);
			return;
		}
		
		String code = porg.getKey("code");
		
		int id = toInt(porg.getKey("id"));
		Map<String, Object> si = getScriptInfo(id);
				
		if(si==null){
			super.setHtml("");
			return;
		}
				
		String path = si.get("path").toString();

		try {
							
			String ip = si.get("ip").toString();
			String user = si.get("name").toString();
			String pwd = si.get("passwd").toString();
			try {
				DesEncrypter de = new DesEncrypter();
				pwd = de.decrypt(pwd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			shell = new SSHByPassword(ip, 22, user, pwd);

			String stdout = new Shell.Plain(shell).exec("python /root/macd/"
					+ path +" "+code);
			
			super.setHtml(stdout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> findPyRun(int iid) {
		
		String format = "SELECT * FROM `strategy_info`  where id='%d' limit 1";
		String sql = String.format(format, iid);
		
		Map<String, Object> res = FetchOne(sql);
		
		return res;
		
	}
	
	private String getStockDB() {
		Shell shell = null;
		String out = "";
		try {
			shell = new SSHByPassword(mConfig.GetValue("pythonIp"), 22,
					mConfig.GetValue("pythonUser"), "!@#qazwsx");

			out = new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo_realtime.py");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	
	private Map<String, Object> getScriptInfo(int id) {
		String format = "SELECT s.path, i.*  FROM `strategy_stock` s, strategy_info i where i.id = s.iid AND s.id=%s limit 1";
		String sql = String.format(format, id);
		
		Map<String, Object> res = FetchOne(sql);
		
		return res;
	}
}
