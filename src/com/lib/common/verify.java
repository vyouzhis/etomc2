package com.lib.common;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.KaptchaAction;

public class verify extends BaseSurface {
	private String className = null;
	public verify() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		isAutoHtml = false;
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
		KaptchaAction ka = new KaptchaAction();
		ka.getVerify();
	}
}
