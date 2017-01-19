package com.lib.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.db.MGDB;

import com.alibaba.fastjson.JSON;

public class getTuShare extends BaseSurface {
	private String className = null;
	//private int getStockDay = 660;

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
		List<String> base = new ArrayList<String>();
		base.add("hs300");
		base.add("sh");
		base.add("sz");
		base.add("zx");
		base.add("cy");
		
		if (base.contains(code) != true) {
			String date = (String) Data.get(0).get("date");
			
			List<Map<String, Object>> Datahfp = getData(code+"_hfq");
			int hlen = Datahfp.size();
			int len=0;
			String hdate = "";
			for (len=0; len<hlen; len++){
				 hdate = (String) Datahfp.get(len).get("date");
				
				if (hdate.equals(date)){
					break;
				}
			}

			List<Map<String, Object>> nData = Datahfp.subList(len, hlen);
			
			d.put("hfq", nData);
		}

		d.put("data", Data);

		Json = JSON.toJSONString(d);

		super.setHtml(Json);
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getData(String code) {
		//String filed = code;

		MGDB mgdb = new MGDB();
		mgdb.DBEnd();
		mgdb.SetCollection("stockDB");

		Map<String, Object> cMap = new HashMap<>();

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
		if (s == false)
			return null;

		List<Map<String, Object>> ok = mgdb.GetValue();

		mgdb.Close();
		return (List<Map<String, Object>>) ok.get(0).get(code);
	}
}
