package com.lib.surface;

import java.io.IOException;

import org.ppl.BaseClass.BaseSurface;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class CodeRun extends BaseSurface {
	private String className = null;

	public CodeRun() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub

		String action = porg.getKey("action");
		
		
		if (action.equals("createPro")) {
			CreatePro();
		}else if (action.equals("saveCode")) {
			SaveCode();
		}else if (action.equals("coderun")) {
			RodeRun();
		}else if (action.equals("readCode")) {
			ReadCode();
		}

	}
	
	private void CreatePro() {
		String name = porg.getKey("data");
		Shell shell = null;
		try {
			shell = new SSHByPassword("192.168.1.248", 22, "root", "qazwsx");
			//String stdout = new Shell.Plain(shell).exec("Rscript s.r");
			String stdout = new Shell.Plain(shell).exec("mkdir "+name);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void SaveCode() {
		Shell shell = null;
		
		String code = porg.getKey("data");
		String name = porg.getKey("name");
		String pro = porg.getKey("pro");
		try {
			shell = new SSHByPassword("192.168.1.248", 22, "root", "qazwsx");
			//code = code.replace("'", "\\'");
			code = code.replace("\"", "\\\"");
			echo(code);
			echo("=========================");
			String out = new Shell.Plain(shell).exec("echo \""+code+"\" > ./"+pro+"/"+name);
			echo(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void RodeRun() {
		Shell shell = null;
		super.setAjax(true);

		String name = porg.getKey("name");
		String pro = porg.getKey("pro");
		
		
		try {
			shell = new SSHByPassword("192.168.1.248", 22, "root", "qazwsx");
			
			String stdout = new Shell.Plain(shell).exec("python "+pro+"/"+name);
			
			super.setHtml(stdout);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void ReadCode() {
		Shell shell = null;
		String name = porg.getKey("name");
		String pro = porg.getKey("pro");
		super.setAjax(true);
		try {
			shell = new SSHByPassword("192.168.1.248", 22, "root", "qazwsx");
			
			String stdout = new Shell.Plain(shell).exec("cat "+pro+"/"+name);
			
			super.setHtml(stdout);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
