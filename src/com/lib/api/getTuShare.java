package com.lib.plug;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

		String[] codes = code.split(",");
		Map<String, Object> JSONList = new TreeMap<>();
		String Json = "";
		MGDB mgdb = new MGDB();

		for (String c : codes) {
			mgdb.DBEnd();
			mgdb.SetCollection(c.trim());

			boolean s = mgdb.FetchList();
			if (s) {
				List<Map<String, Object>> res = mgdb.GetValue();

				Map<String, Object> TMap = new TreeMap<>();
				TMap.putAll(res.get(0));
				JSONList.put(c, TMap);
			}
		}
		// echo(mgdb.getErrorMsg());
		mgdb.Close();

		Json = JSON.toJSONString(JSONList);
		super.setHtml(Json);
	}
}
