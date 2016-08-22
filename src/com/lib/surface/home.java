package com.lib.surface;

import org.ppl.BaseClass.BaseSurface;
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
		
		setRoot("trade", ucl.Url("trade"));
		setRoot("risk", ucl.Url("risk"));
		setRoot("arbitrage", ucl.Url("arbitrage"));
		setRoot("strategy", ucl.Url("strategy"));
		
		
		setRoot("root_var", "var a=1;");
		
		super.View();
	}
}
