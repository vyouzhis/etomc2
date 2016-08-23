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
		setRoot("d", "d");
		if (isLogin() > 0) {
			
		} else {
			
			
		}
		
		super.View();
	}
}
