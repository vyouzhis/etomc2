package com.lib.common;

import org.ppl.BaseClass.BaseModule;
import org.ppl.etc.UrlClassList;

public class SurfaceFooter extends BaseModule {
	private String className = null;

	public SurfaceFooter() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub

		UrlClassList ucl = UrlClassList.getInstance();
		setRoot("istrategy_url", ucl.Url("strategy"));
		setRoot("itrade_url", ucl.Url("trade"));
		setRoot("irisk_url", ucl.Url("risk"));
		setRoot("iarbitrage_url", ucl.Url("arbitrage"));

		super.View();
	}

}
