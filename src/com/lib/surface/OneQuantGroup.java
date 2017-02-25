package com.lib.surface;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.UrlClassList;

public class OneQuantGroup extends BaseSurface{
	private String className = null;
	public OneQuantGroup() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();

		setRoot("istrategy_url", ucl.Url("strategy"));
		setRoot("OneQuantGroup", ucl.Url("one"));
		setRoot("QuantGroupList", ucl.Url("grouplist"));
		setRoot("iarbitrage_url", ucl.Url("arbitrage"));

		setRoot("root_var", "var a=1;");

		super.View();
	}
}
