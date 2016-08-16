package com.lib.common;

import org.ppl.BaseClass.BaseModule;

public class Header extends BaseModule {
	private String className = null;

	public Header() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		
		super.View();
	}
	
	public void SetTitle(String title) {
		setRoot("TITLE", title);
	}

}
