package com.lib.common;

import org.ppl.BaseClass.BaseModule;
import org.ppl.etc.UrlClassList;

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
		setRoot("icore", ucl.BuildUrl("icore", time() + ""));
		setRoot("ilogout", ucl.Url("logout"));
		setRoot("about", ucl.Url("about"));

		setRoot("trade", ucl.Url("trade"));
		setRoot("risk", ucl.Url("risk"));
		setRoot("arbitrage", ucl.Url("arbitrage"));
		setRoot("strategy", ucl.Url("strategy"));

		
		
		int islogin = isLogin();
		setRoot("islogin", islogin);
		if (islogin>0) {
			setRoot("rshare", ucl.BuildUrl("s", igetUid()+""));
			setRoot("UserName", igetName());
		}else {
			setRoot("rshare", "");
		}
		
		super.View();
	}

}
