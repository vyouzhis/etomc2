package com.lib.api;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.globale_config;

public class checkVerify extends BaseSurface {
	private String className = null;

	public checkVerify() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		String kaptcha = porg.getKey("kaptchaImage");
		String sess_kaptcha = SessAct.GetSession(globale_config.KaptchSes);
		String stdout = "0";
		if (kaptcha.equals(sess_kaptcha)) {

			stdout = "1";
		}

		super.setHtml(stdout);
	}
}
