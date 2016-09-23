package com.lib.surface;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

public class home extends BaseSurface{
	private String className = null;
	
	public home() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		
		setRoot("istrategy_url", ucl.Url("strategy"));
		setRoot("itrade_url", ucl.Url("trade"));
		setRoot("irisk_url", ucl.Url("risk"));
		setRoot("iarbitrage_url", ucl.Url("arbitrage"));
		
		
		setRoot("root_var", "var a=1;");
		
		int is = isLogin();
		if(is<1){
			setRoot("IndexTopBar", "1");
			String salt = getSalt();
			
			setRoot("Salt", salt);
			setRoot("ilogin_action_uri", ucl.Url("loginAction"));
		}else {
			ShowMessage sm = ShowMessage.getInstance();
			
			sm.Redirect("uindex");
		}
		
		super.View();
	}
}
