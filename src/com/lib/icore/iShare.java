package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.etc.UrlClassList;

public class iShare extends BaseiCore {
	private String className = null;

	public iShare() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		if (super.Init() == -1) {

			String err_url = ucl.BuildUrl("login", "");

			TipMessage(err_url, _CLang("error_passwd_or_name"));
		} else {
			setRoot("dd", "dd");
			super.View();
		}
	}
}
