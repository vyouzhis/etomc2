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
		Map<String, Object> d = new HashMap<>();
		String Json = "";
		List<Map<String, Object>> Data = getData(code);
		if (!code.equals("hs300")) {
			List<Map<String, Object>> Datahfp = getData(code + "_hfq");
			d.put("hfq", Datahfp);
		}

		d.put("data", Data);

		Json = JSON.toJSONString(d);

		super.setHtml(Json);
	}

	private List<Map<String, Object>> getData(String code) {
		MGDB mgdb = new MGDB();
		mgdb.DBEnd();
		mgdb.SetCollection("stockDB");

		Map<String, Integer> cMap = new HashMap<>();
		cMap.put(mgdb.exists, 1);

		Map<String, Object> wMap = new HashMap<>();
		wMap.put(code, cMap);		
		String JsonMap = JSON.toJSONString(wMap);
		mgdb.JsonWhere(JsonMap);

		Map<String, Object> _idMap = new HashMap<>();
		_idMap.put("_id", 0);
		String Jsonid = JSON.toJSONString(_idMap);
		mgdb.JsonColumn(Jsonid);
				
		boolean s = mgdb.FetchList();
		List<Map<String, Object>> res = null;
		if (s) {
			res = mgdb.GetValue();
		}

		mgdb.Close();
		return res;
	}
}
