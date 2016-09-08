package com.lib.api;

import org.ppl.BaseClass.BaseiCore;

public class ibookmark extends BaseiCore{
	private String className = null;
	public ibookmark() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);

		if (super.Init() == -1) {

			super.setHtml("");
		} else {
			
			super.setHtml("ok");
		}
		
	}
	
	private void bmset() {
		
	}
	
	private void bmget() {
		
	}
}
