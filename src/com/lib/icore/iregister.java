package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;

public class iregister extends BaseSurface {
	private String className = null;

	public iregister() {
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
		
		Encrypt en = Encrypt.getInstance();
		String salt = en.MD5(String.valueOf(time()));

		SessAct.SetSession(globale_config.SessSalt, salt);
		setRoot("Salt", salt);
		
		salt = en.MD5(String.valueOf(time()+10));
		setRoot("register_action_uri", ucl.Url("register_ok/"+salt));
		
		super.View();
	}
}