package com.lib.icore;

import java.net.URL;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;
import org.ppl.BaseClass.BaseSurface;
import org.ppl.io.ProjectPath;



public class istrategy extends BaseSurface {
	private String className = null;

	public istrategy() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub

		int islogin = isLogin();
		// echo("islogin:"+islogin);
		setRoot("islogin", islogin);

		setRoot("strategy_name", "default strategy");

		setRoot("Step.strategy", mConfig.GetInt("Step.strategy"));
		

		super.View();
	}

	
	
}