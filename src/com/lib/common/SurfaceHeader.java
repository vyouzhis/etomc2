package com.lib.common;

import org.ppl.BaseClass.BaseModule;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

public class SurfaceHeader extends BaseModule {
	private String className = null;
	public SurfaceHeader() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}
	@Override
	public void filter() {
		// TODO Auto-generated method stub
		
		UrlClassList ucl = UrlClassList.getInstance();
		
		setRoot("index", ucl.Url(""));
		setRoot("register", ucl.Url("register"));
		setRoot("login", ucl.Url("login"));
		setRoot("icore", ucl.Url("icore"));
		
		super.View();
	}

}
