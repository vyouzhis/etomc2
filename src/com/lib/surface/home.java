package com.lib.surface;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.io.Encrypt;


public class home extends BaseSurface{
	private String className = null;
	
	public home() {
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
		
		int is = isLogin();
		if(is<1){
			//setRoot("IndexTopBar", "1");
			String salt = getSalt();
			Encrypt en = Encrypt.getInstance();
			setRoot("Salt", salt);
			setRoot("ilogin_action_uri", ucl.Url("loginAction"));
			String radromSalt = en.MD5(String.valueOf(time()+10));
			setRoot("register_action_uri", ucl.Url("register_act/"+radromSalt));
			setRoot("captcha_image", ucl.Url("captcha_image"));
			setRoot("captcha_check", ucl.Url("captcha_check"));
			
			listStrategy();
		}else {
			ShowMessage sm = ShowMessage.getInstance();
			
			sm.Redirect("uindex");
		}
		
		super.View();
	}
	
	private void listStrategy() {
		String sql = "SELECT s.title,s.sdesc,s.follow FROM `index_show` i, `strategy_stock` s WHERE tables LIKE 'strategy_stock' AND s.id = i.tid  limit 3";
				
		List<Map<String, Object>> res = null;
		
		try {
			res = FetchAll(sql);
			if(res!=null){
				setRoot("StrategyIndex", res.get(0));
				res.remove(0);
				setRoot("listStrategy", res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
