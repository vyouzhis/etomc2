package com.lib.api;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.Page;

import com.alibaba.fastjson.JSON;

public class StraPage extends BaseSurface {
	private String className = null;
	private int limit = 5;

	public StraPage() {
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

		pageInfo.put("data", getStra());

		pageInfo.put("page", Listli);

		String json = JSON.toJSONString(pageInfo);

		super.setHtml(json);
	}

	private List<Map<String, Object>> getStra() {
		String format = "SELECT s.id, s.title,s.sdesc,s.integral, s.follow, i.nickname, s.cid"
				+ " FROM `strategy_stock` s, stock_user_info i"
				+ " where i.uid=s.uid  ORDER by s.integral limit %d, %d";
		int p = toInt(porg.getKey("p"));

		int offset = 0;

		if (p != 0) {
			offset = (p - 1) * limit;
		}

		String sql = String.format(format, offset, limit);
		
		List<Map<String, Object>> res = null;
		try {
			res = FetchAll(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;

	}

	private int getTol() {
		String format = "SELECT count(*) as cou FROM `strategy_stock` limit 1";

		Map<String, Object> res = FetchOne(format);
		if (res != null) {
			return Integer.valueOf(res.get("cou").toString());
		} else {
			return 0;
		}
	}
}
