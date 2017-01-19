package com.lib.surface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

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
		setRoot("itrade_url", ucl.Url("trade"));
		setRoot("irisk_url", ucl.Url("risk"));
		setRoot("iarbitrage_url", ucl.Url("arbitrage"));
		
		
		setRoot("root_var", "var a=1;");
		
		int is = isLogin();
		if(is<1){
			setRoot("IndexTopBar", "1");
			String salt = getSalt();
			
			setRoot("Salt", salt);
			setRoot("ilogin_action_uri", ucl.Url("loginAction"));
						
			listStrategy();
		}else {
			ShowMessage sm = ShowMessage.getInstance();
			
			sm.Redirect("uindex");
		}
		
		super.View();
	}
	
	private void listStrategy() {
		String sql = "SELECT title,sdesc FROM `strategy_stock` order by id desc limit 6 ";
				//+ " UNION(SELECT title, sdesc FROM `strategy_stock` WHERE cid=2 order by id desc limit 4)  ";
				//+ " UNION (SELECT title,SUBSTRING(sdesc,1,50) as sdesc FROM `strategy_stock` WHERE cid=3 limit 2)";
		
		List<Map<String, Object>> res = null;
		
		try {
			res = FetchAll(sql);
			if(res!=null){
				setRoot("listStrategy", res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
