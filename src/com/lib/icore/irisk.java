package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.etc.UrlClassList;

public class irisk extends BaseiCore {
	private String className = null;

	public irisk() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		if (super.Init() == -1) {

			
			String err_url = ucl.BuildUrl("login", "");

			TipMessage(err_url, _CLang("error_passwd_or_name"));
		} else {
			int islogin = isLogin();
			setRoot("islogin", islogin);

			setRoot("istrategy_url", ucl.Url("strategy"));
			setRoot("itrade_url", ucl.Url("trade"));
			setRoot("irisk_url", ucl.Url("risk"));
			setRoot("iarbitrage_url", ucl.Url("arbitrage"));
		
			setRoot("Step_strategy", mConfig.GetInt("Step.strategy"));
			
			super.View();
		}
	}
}
