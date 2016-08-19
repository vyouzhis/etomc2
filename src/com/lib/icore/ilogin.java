package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;

public class ilogin extends BaseSurface {
	private String className = null;
	
	public ilogin() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		Encrypt en = Encrypt.getInstance();
		String salt = en.MD5(String.valueOf(time()));
		UrlClassList ucl = UrlClassList.getInstance();

		SessAct.SetSession(globale_config.SessSalt, salt);
		setRoot("Salt", salt);
		
		setRoot("ilogin_action_uri", ucl.Url("loginAction"));
		super.View();
	}
}
