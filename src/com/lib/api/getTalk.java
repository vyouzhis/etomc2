package com.lib.api;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.Page;

import com.alibaba.fastjson.JSON;

public class getTalk extends BaseSurface {
	private String className = null;
	private int limit = 5;
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
		String Listli = page.getDefPage(url, p, tol, limit, "getPage");
		
		
		Map<String, Object> pageInfo = new HashMap<>();

		pageInfo.put("data", talks());

		pageInfo.put("page", Listli);
		
		String json = JSON.toJSONString(pageInfo);
		
		super.setHtml(json);

	}
	
	private int  getTol() {
		
		int sid = toInt(porg.getKey("sid"));
		String code = porg.getKey("code");
		String where = "";
		if(sid!=0){
			where = "t.sid="+sid;
		}else if (code != null) {
			where = "t.code="+code;
		}else {
			return 0;
		}
		
		String format = "SELECT count(*) as cou FROM `stock_user_talk` t where %s limit 1";

		String sql = String.format(format, where);
		
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
		if(sid!=0){
			where = "t.sid="+sid;
		}else if (code != null) {
			where = "t.code="+code;
		}else {
			return null;
		}
		
		String sql = String.format(format, where , offset, limit);
		
		List<Map<String, Object>> res = null;
		try {
			res = FetchAll(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
}
