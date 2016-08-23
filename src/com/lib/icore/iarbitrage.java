package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.etc.UrlClassList;

public class iarbitrage extends BaseiCore {
	private String className = null;

	public iarbitrage() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		if (super.Init() == -1) {

			UrlClassList ucl = UrlClassList.getInstance();
			String err_url = ucl.BuildUrl("login", "");

			TipMessage(err_url, _CLang("error_passwd_or_name"));
		} else {
			setRoot("d", "d");
			super.View();
		}
	}
}
