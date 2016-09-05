package com.lib.api;

import org.ppl.BaseClass.BaseSurface;

public class getTalk extends BaseSurface {
	private String className = null;

	public getTalk() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);

		super.setHtml("ok");

	}
}
