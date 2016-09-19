package com.lib.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseiCore;

import com.alibaba.fastjson.JSON;

public class iArbInfo extends BaseiCore{
	private String className = null;
	public iArbInfo() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);

		if (super.Init() == -1) {

			super.setHtml("");
		} else {
			int act = toInt(porg.getKey("act"));
			switch (act) {
			case 0:
				selling();
				break;
			case 1:
				StockDesc();
				break;
			default:
				break;
			}
			//super.setHtml("ok");
		}
	}
	
	private void selling() {
		String code = porg.getKey("code");
		String format = "UPDATE `stock_strategy` SET isstop = 1 WHERE code='%s' AND isstop=0 and uid=%d;";
		String sql = String.format(format, code, igetUid());
		
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.setHtml("0");
	}
	
	private void StockDesc() {
		String code = porg.getKey("code");
		String format = "SELECT s.code,s.name,t.title,s.isstop FROM `stock_strategy` s, strategy_stock t WHERE s.sid = t.id AND s.code='%s' and s.uid=%d ORDER BY `code` ASC ";
		String sql = String.format(format, code, igetUid());
		
		List<Map<String, Object>> res = null;
		String json = "";
		try {
			res = FetchAll(sql);
			if(res != null){
				json = JSON.toJSONString(res);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.setHtml(json);
	}
	
}
