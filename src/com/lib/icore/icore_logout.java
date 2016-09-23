package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

public class icore_logout extends BaseiCore {
	private String className = null;

	public icore_logout() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		iLogout();
		ShowMessage ms = ShowMessage.getInstance();
		UrlClassList ucl = UrlClassList.getInstance();
		String url = ucl.BuildUrl("login", "");

		ms.Redirect(url);
	}
}
