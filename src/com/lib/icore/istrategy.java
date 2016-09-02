package com.lib.icore;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.db.MGDB;


public class istrategy extends BaseSurface {
	private String className = null;

	public istrategy() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
		int islogin = isLogin();
		//echo("islogin:"+islogin);
		setRoot("islogin", islogin);
				
		
		setRoot("strategy_name", "default strategy");
		
		setRoot("AllStock", "s");
		
		super.View();
	}
	
	private void getAllStock() {
		MGDB mgdb = new MGDB();
		
		mgdb.DBEnd();
		mgdb.SetCollection("etomc2_AllStockClass");

		boolean s = mgdb.FetchList();
		if (s) {
			List<Map<String, Object>> res = mgdb.GetValue();

			Map<String, Object> TMap = new TreeMap<>();
			TMap.putAll(res.get(0));
			JSONList.put(c, TMap);
		}
	
	// echo(mgdb.getErrorMsg());
	mgdb.Close();
	}
}
