package com.lib.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.db.MGDB;

import com.alibaba.fastjson.JSON;

public class getStockCode extends BaseSurface {
	private String className = null;

	public getStockCode() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);
		String codeext = porg.getKey("codeext");
		if (codeext == null || codeext.length() == 0) {
			super.setHtml("");
			return;
		}

		getCodeExt();
	}

	private void getCodeExt() {
		MGDB mgdb = new MGDB();

		mgdb.DBEnd();
		mgdb.SetCollection("AllStockClass");
		String codeext = porg.getKey("codeext");
		codeext = codeext.replace("_", "");
		
		Map<String, Object> jsonWhere = new HashMap<>();
		Map<String, String> reqMap = new HashMap<>();
		reqMap.put(mgdb.regex, "^"+codeext);
		jsonWhere.put("code", reqMap);		
		String json = JSON.toJSONString(jsonWhere);
		mgdb.JsonWhere(json);
		
		Map<String, Integer> sort = new HashMap<>();
		sort.put("code", -1);
		mgdb.JsonSort(JSON.toJSONString(sort));
		
		mgdb.setLimit(10);

		boolean s = mgdb.FetchList();
		
		if (s) {
			List<Map<String, Object>> res = mgdb.GetValue();
		
			super.setHtml(JSON.toJSONString(res));
		}
		
		// echo(mgdb.getErrorMsg());
		mgdb.Close();
	}
}
