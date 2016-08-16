package com.lib.common;

import org.ppl.BaseClass.BaseModule;

public class Footer extends BaseModule {
	private String className = null;

	public Footer() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		
		super.View();
	}

}
