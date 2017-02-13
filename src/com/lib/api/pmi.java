package com.lib.api;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.net.cUrl;

public class pmi extends BaseSurface {
	private String className = null;
	public pmi() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);
		cUrl curl = new cUrl();
		String url = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=hgyd"
				+ "&rowcode=zb&colcode=sj&wds=%5B%5D&dfwds=%5B%7B%22wdcode%22%3A%22zb%22%2C%22valuecode%22%3A%22A1901%22%7D%5D"
				+ "&k1=1486438515120";
		
		
		String json = curl.httpGet(url);
				
		super.setHtml(json);
	}
}
