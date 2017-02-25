package com.lib.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.io.DesEncrypter;

import com.jcabi.ssh.SSH;
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
		
		String code = porg.getKey("code");
		int isquota = toInt(porg.getKey("quota"));
		
		int id = toInt(porg.getKey("id"));
		Map<String, Object> si = getScriptInfo(id);
				
		if(si==null){
			super.setHtml("");
			return;
		}
				
		String path = si.get("path").toString();
		String quota = si.get("quota").toString();

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
			
			String stdout = "";
			if (isquota == 0) {
				stdout = new Shell.Plain(shell).exec("export LC_CTYPE=zh_CN.utf8 && cd "+mConfig.GetValue("pythonPath")+" && python "
				+ path +" "+code);
			}else {
				stdout = new Shell.Plain(shell).exec("export LC_CTYPE=zh_CN.utf8 && cd "+mConfig.GetValue("pythonPath") + " && python "
				+ quota +" "+code);
			}
			
						
			super.setHtml(stdout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> getScriptInfo(int id) {
		String format = "SELECT s.path,s.quota, i.*  FROM `strategy_stock` s, strategy_info i where i.id = s.iid AND s.id=%s limit 1";
		String sql = String.format(format, id);
		
		Map<String, Object> res = FetchOne(sql);
		
		return res;
	}
}
