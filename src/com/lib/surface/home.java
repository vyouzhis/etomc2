package com.lib.surface;

import org.ppl.BaseClass.BaseSurface;

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
		setRoot("user", "Big Joe");
		
		super.View();
	}
}
