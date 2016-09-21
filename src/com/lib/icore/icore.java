package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.etc.UrlClassList;
import org.ppl.plug.wryip.IPSeeker;

import com.lib.plug.IptoAddr;

public class icore extends BaseiCore {
	private String className = null;

	public icore() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		if (super.Init() == -1) {

			
			String err_url = ucl.BuildUrl("login", "");

			TipMessage(err_url, _CLang("error_passwd_or_name"));
		}else {
			setRoot("icore", ucl.BuildUrl("icore", time() + ""));
			setRoot("balance", ucl.Url("icore"));
			setRoot("face", ucl.Url("face"));
			
			
			setRoot("istrategy_url", ucl.Url("strategy"));
			setRoot("itrade_url", ucl.Url("trade"));
			setRoot("irisk_url", ucl.Url("risk"));
			setRoot("iarbitrage_url", ucl.Url("arbitrage"));
			
			setRoot("nickname", igetName());
			setRoot("phone",igetKey("phone"));
			setRoot("email",igetKey("email"));
			
			IptoAddr iAddr = new IptoAddr();
			setRoot("addree",iAddr.ia(igetKey("ip")));
			
			
			//echo(iAddr.ia(porg.GetIP()));
			super.View();
		}
		
	}
}