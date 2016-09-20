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

		String code = porg.getKey("code");
		
		String path = porg.getKey("path");
		echo("python /root/macd/"
				+ path +" "+code);
		try {
			Map<String, Object> pMap = findPyRun();
			if(pMap==null){
				super.setHtml("");
				return;
			}
			
			
			String ip = pMap.get("ip").toString();
			String user = pMap.get("name").toString();
			String pwd = pMap.get("passwd").toString();
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
			stdout += "@";
			
			stdout += new Shell.Plain(shell).exec("python /root/macd/"
					+ path +" hs300");
			
			super.setHtml(stdout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> findPyRun() {
		String iid = porg.getKey("iid");
		String format = "SELECT * FROM `strategy_info`  where id='%s' limit 1";
		String sql = String.format(format, iid);
		
		Map<String, Object> res = FetchOne(sql);
		
		return res;
		
	}
}
