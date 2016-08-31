package com.lib.plug;

import java.io.IOException;

import org.ppl.BaseClass.BaseSurface;

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
		String iid = porg.getKey("iid");
		String path = porg.getKey("path");
		echo("python /root/macd/"
				+ path +" "+code);
		try {
			shell = new SSHByPassword("192.168.122.151", 22, "root", "!@#qazwsx");

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
}
