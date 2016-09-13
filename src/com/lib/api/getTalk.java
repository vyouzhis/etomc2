package com.lib.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.Page;
import org.ppl.db.MGDB;

import com.alibaba.fastjson.JSON;

public class getTalk extends BaseSurface {
	private String className = null;
	private int limit = 10;

	public getTalk() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);

		int tol = getTol();
		int p = toInt(porg.getKey("p"));
		String url = "";

		Page page = new Page();
		String Listli = page.getDefPage(url, p, tol, limit, "getTalkPage");
	
		Map<String, Object> pageInfo = new HashMap<>();
				
		if(porg.getKey("code")!=null){
			List<Map<String, Object>> stock_info = StockInfo(porg.getKey("code"));
			pageInfo.put("StockInfo", stock_info);
		}
		
		pageInfo.put("data", talks());

		pageInfo.put("page", Listli);

		String json = JSON.toJSONString(pageInfo);
		
		super.setHtml(json);

	}

	private int getTol() {

		int sid = toInt(porg.getKey("sid"));
		String code = porg.getKey("code");
		String where = "";
		if (sid != 0) {
			where = "t.sid=" + sid;
		} else if (code != null) {
			where = "t.code=" + code;
		} else {
			return 0;
		}

		String format = "SELECT count(*) as cou FROM `stock_user_talk` t where %s limit 1";

		String sql = String.format(format, where);
		//echo(sql);
		Map<String, Object> res = FetchOne(sql);
		if (res != null) {
			return toInt(res.get("cou").toString());
		} else {
			return 0;
		}
	}

	private List<Map<String, Object>> talks() {
		String format = "SELECT t.id,t.pid,t.sid,t.code,t.msg,t.ctime,t.reply,t.top,i.nickname FROM `stock_user_talk` t, stock_user_info i "
				+ " WHERE  %s  AND i.uid = t.uid ORDER BY t.top DESC,t.ctime DESC limit %d, %d";
		int p = toInt(porg.getKey("p"));

		int offset = 0;

		if (p != 0) {
			offset = (p - 1) * limit;
		}
		int sid = toInt(porg.getKey("sid"));
		String code = porg.getKey("code");
		String where = "";
		if (sid != 0) {
			where = "t.sid=" + sid;
		} else if (code != null) {
			where = "t.code=" + code;
		} else {
			return null;
		}

		String sql = String.format(format, where, offset, limit);

		List<Map<String, Object>> res = null;
		try {
			res = FetchAll(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	private List<Map<String, Object>> StockInfo(String code) {
		List<String> Info = new ArrayList<>();
		List<Map<String, Object>> Infos = new ArrayList<>();
		//Info.add("basics");
		Info.add("report");
		Info.add("profit");
		Info.add("operation");
		Info.add("growth");
		Info.add("debtpaying");
		Info.add("cashflow");
		
	
		for (int i = 0; i < Info.size(); i++) {
			Infos.add(getInfo(Info.get(i), code));
		}
		Infos.add(getBasics(code));
		
		return Infos;			
	}
	
	private Map<String, Object> getInfo(String itype, String code) {
		MGDB mgdb = new MGDB();
		mgdb.DBEnd();
		mgdb.SetCollection("stockInfo");

		Map<String, String> wMap = new HashMap<>();
		wMap.put(itype+".code", code);

		String JsonMap = JSON.toJSONString(wMap);
		
		mgdb.JsonWhere(JsonMap);

		Map<String, Integer> cMap = new HashMap<>();
		cMap.put(itype+".$", 1);
		cMap.put("_id", 0);

		String JsonColu = JSON.toJSONString(cMap);
		
		mgdb.JsonColumn(JsonColu);

		boolean s = mgdb.FetchList();
		List<Map<String, Object>> res = null;
		if (s) {
			res = mgdb.GetValue();
			
		}else {
			
			mgdb.Close();
			return null;
		}
		mgdb.Close();
		
		return res.get(0);			
	}
	
	private Map<String, Object> getBasics(String code) {
		MGDB mgdb = new MGDB();
		mgdb.DBEnd();
		mgdb.SetCollection("stockInfo");


		Map<String, Integer> cMap = new HashMap<>();
		cMap.put("basics."+code, 1);
		cMap.put("_id", 0);

		String JsonColu = JSON.toJSONString(cMap);
		
		mgdb.JsonColumn(JsonColu);

		boolean s = mgdb.FetchList();
		List<Map<String, Object>> res = null;
		if (s) {
			res = mgdb.GetValue();
			
		}else {
			
			mgdb.Close();
			return null;
		}
		mgdb.Close();
		
		return res.get(0);			
	}
}
