package com.lib.icore;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.common.Page;
import org.ppl.etc.UrlClassList;

public class iarbitrage extends BaseiCore {
	private String className = null;
	private int limit = 5;
	public iarbitrage() {
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
		} else {
			int islogin = isLogin();
			setRoot("islogin", islogin);

			setRoot("strategy_name", "default strategy");

			setRoot("Step_Type", mConfig.GetInt("Step.strategy"));

			setRoot("istrategy_url", ucl.Url("strategy"));
			setRoot("itrade_url", ucl.Url("trade"));
			setRoot("irisk_url", ucl.Url("risk"));
			setRoot("iarbitrage_url", ucl.Url("arbitrage"));
			
			showNowStockInfo(0);
			showNowStockInfo(1);
			ipage();
			super.View();
		}
	}
	
	private void showNowStockInfo(int i) {
		String format = "SELECT DISTINCT code,name FROM `stock_strategy` WHERE isstop=%d and uid=%d ORDER BY `code` ASC ";
		String sql = String.format(format,i, igetUid());
		
		List<Map<String, Object>> res = null;
		
		try {
			res = FetchAll(sql);
			if(res!=null){
				setRoot("NowStockInfo"+i,res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void ipage() {
		int p = toInt(porg.getKey("p"));
		String url = "";
		int tol = getTol();
		
		Page page = new Page();
		String Listli = page.getDefPage(url, p, tol, limit, "getPage");
		
		setRoot("ipage", Listli);
	}
	
	private int getTol() {
		String format = "SELECT count(*) as cou FROM `stock_strategy`  where  isstop=1 and uid=%d limit 1";

		String sql = String.format(format, toInt(porg.getKey("cid")));

		Map<String, Object> res = FetchOne(sql);
		if (res != null) {
			return Integer.valueOf(res.get("cou").toString());
		} else {
			return 0;
		}
	}
}
