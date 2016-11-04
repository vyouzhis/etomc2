package com.lib.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.db.MGDB;
import org.ppl.io.TimeClass;

import com.alibaba.fastjson.JSON;

public class getTuShare extends BaseSurface {
	private String className = null;
	private int getStockDay = 660;

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
		List<Map<String, Object>> Data = getData(code, 0);
		if (!code.equals("hs300")) {
			List<Map<String, Object>> Datahfp = getData(code, 1);
			d.put("hfq", Datahfp);
		}

		d.put("data", Data);

		Json = JSON.toJSONString(d);

		super.setHtml(Json);
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getData(String code, int Ishfq) {
		String filed = code;

		MGDB mgdb = new MGDB();
		mgdb.DBEnd();
		mgdb.SetCollection("stockDB");

		Map<String, Object> cMap = new HashMap<>();
		if (Ishfq == 0) {
			// no hfq
			int now = time();
			now = now - getStockDay * TimeClass.OneDaySceond;
			TimeClass tc = TimeClass.getInstance();
			String date = tc.TimeStamptoDate((long) now, "yyyy-MM-dd");
			cMap.put(mgdb.gte, date);

		} else {
			// is hfq
			int now = time();
			now = now - getStockDay * TimeClass.OneDaySceond;
			cMap.put(mgdb.gte, now);
			filed += "_hfq";
		}

		Map<String, Object> wMap = new HashMap<>();
		wMap.put(filed + ".date", cMap);
		String JsonMap = JSON.toJSONString(wMap);
		mgdb.JsonWhere(JsonMap);

		Map<String, Object> _idMap = new HashMap<>();
		_idMap.put("_id", 0);

		String Jsonid = JSON.toJSONString(_idMap);
		mgdb.JsonColumn(Jsonid);

		Map<String, Object> _sortMap = new HashMap<>();
		_sortMap.put(filed + ".date", 1);
		String JsonSort = JSON.toJSONString(_sortMap);
		mgdb.JsonSort(JsonSort);

		boolean s = mgdb.FetchList();
		if (s == false)
			return null;

		String time = "", time2 = "";
		List<Map<String, Object>> ok2 = new ArrayList<>();
		Map<String, Object> map = null;
		while ((map = mgdb.GetValueLoop()) != null) {
			List<Map<String, Object>> a = (List<Map<String, Object>>) map
					.get(filed);
			if (time.length() == 0) {
				time = a.get(a.size() - 1).get("date").toString();
			} else {
				time2 = time;
				time = a.get(0).get("date").toString();
				if (time.equals(time2)) {
					a.remove(0);
				}
			}
			ok2.addAll(a);

		}

		mgdb.Close();
		return ok2;
	}
}
