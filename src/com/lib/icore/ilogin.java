package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

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

		UrlClassList ucl = UrlClassList.getInstance();

		if (isLogin() > 0) {

			ShowMessage ms = ShowMessage.getInstance();
			String url = ucl.BuildUrl("icore", time() + "");

			ms.ShowMsg(url);

			return;
		}

		String salt = getSalt();
		
		setRoot("Salt", salt);

		setRoot("istrategy_url", ucl.Url("strategy"));
		setRoot("itrade_url", ucl.Url("trade"));
		setRoot("irisk_url", ucl.Url("risk"));
		setRoot("iarbitrage_url", ucl.Url("arbitrage"));
		
		setRoot("ilogin_action_uri", ucl.Url("loginAction"));
		super.View();
	}
}
