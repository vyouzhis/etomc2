package com.lib.icore;


import org.ppl.BaseClass.BaseSurface;


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
		echo("islogin:"+islogin);
		setRoot("islogin", islogin);
				
		
		setRoot("strategy_name", "default strategy");
		
		super.View();
	}
}
