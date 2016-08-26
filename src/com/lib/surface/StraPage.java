package com.lib.plug;

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
		

		Page page = new Page();
		int tol = getTol();
		int p = toInt(porg.getKey("p"));

		String url = "";

		String pageString = page.base_page(url, tol, p, limit, "");
		
		String[] pp = pageString.split(";");
		String Listli = "";
		String CurrPage = "1";
		for (int i = 0; i < pp.length; i++) {
			String[] nn = pp[i].split("@");
			
			if(nn[0].equals("#")){
				CurrPage =nn[1];
				Listli+="<li class='active'><a href='#'>"+nn[1]+"</a></li>";
			}else if (nn[1].equals("[left]")) {
				Listli+="<li><a href='javascript:void(0)' onclick=getPage(1)><i class='fa fa-angle-left'></i></a></li>";
			}else if (nn[1].equals("[right]")) {
				int pnum = toInt(CurrPage);
				pnum++;
				Listli+="<li><a href='javascript:void(0)' onclick=getPage("+pnum+") ><i class='fa fa-angle-right'></i></a></li>";
			}else if (nn[1].equals("[last]")) {
				
			}else {
				Listli+="<li><a  href='javascript:void(0)' onclick=getPage("+nn[1]+") >"+nn[1]+"</a></li>";
			}
		}
		

		Map<String, Object> pageInfo = new HashMap<>();

		pageInfo.put("data", getStra());

		pageInfo.put("page", Listli);

		String json = JSON.toJSONString(pageInfo);

		super.setHtml(json);
	}

	private List<Map<String, Object>> getStra() {
		String format = "SELECT * FROM `strategy_stock`  where cid=%d limit %d, %d";
		int p = toInt(porg.getKey("p"));

		int offset = 0;

		if (p != 0) {
			offset = (p - 1) * limit;
		}

		String sql = String.format(format, toInt(porg.getKey("cid")), offset, limit);
		
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
		String format = "SELECT count(*) as cou FROM `strategy_stock`  where cid=%d limit 1";

		String sql = String.format(format, toInt(porg.getKey("cid")));

		Map<String, Object> res = FetchOne(sql);
		if (res != null) {
			return Integer.valueOf(res.get("cou").toString());
		} else {
			return 0;
		}
	}
}
