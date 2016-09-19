package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.UrlClassList;

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
		UrlClassList ucl = UrlClassList.getInstance();

		// echo("islogin:"+islogin);
		setRoot("islogin", islogin);

		setRoot("strategy_name", "default strategy");

		setRoot("Step_Type", mConfig.GetInt("Step.strategy"));

		setRoot("istrategy_url", ucl.Url("strategy"));
		setRoot("itrade_url", ucl.Url("trade"));
		setRoot("irisk_url", ucl.Url("risk"));
		setRoot("iarbitrage_url", ucl.Url("arbitrage"));
		
		super.View();
	}

}