package com.lib.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.db.MGDB;

import com.alibaba.fastjson.JSON;

public class getTuShare extends BaseSurface {
	private String className = null;

	public getTuShare() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);
		String code = porg.getKey("code");
		if (code == null || code.length() == 0) {
			super.setHtml("");
			return;
		}

		String Json = "";
		MGDB mgdb = new MGDB();
		mgdb.DBEnd();
		mgdb.SetCollection("stockDB");

		Map<String, Integer> cMap = new HashMap<>();
		cMap.put(mgdb.exists, 1);

		Map<String, Object> wMap = new HashMap<>();
		wMap.put(code, cMap);
		String JsonMap = JSON.toJSONString(wMap);
		mgdb.JsonWhere(JsonMap);
		echo(JsonMap);
		boolean s = mgdb.FetchList();
		if (s) {
			List<Map<String, Object>> res = mgdb.GetValue();
									
			Json = JSON.toJSONString(res.get(0));
		}

		mgdb.Close();

		super.setHtml(Json);
	}
}
