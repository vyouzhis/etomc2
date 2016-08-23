package org.ppl.common;

import java.util.HashMap;
import java.util.Map;

public class Page {

	public Map<String, Object> s_page(String url, int total, int page,
			int limit, String para) {
		Map<String, Object> pMap = new HashMap<>();

		if (para.length() > 0)
			para = '&' + para;
		double tol_page = (double) total / (double) limit;
		int ceil = (int) Math.ceil(tol_page);
		int st = 0;
		int ceil_limit = 0;
		String spage, npage, ppage;

		if (ceil > 1)
			ceil_limit = 5;

		if (ceil > 1 && page >= 5) {
			ceil_limit = 10;
		}
		if (page >= 8) {
			st = (ceil - page) > 10 ? (page - 5) : (ceil - 10);
			ceil_limit += st - 1;
			if (ceil - page < 8) {
				st = page - 2;
				ceil_limit = ceil;
			}
			if (ceil - page == 10) {
				st = page - 2;
				ceil_limit = st + 9;
			}
		} else {
			st = 1;
		}
		if (ceil < ceil_limit)
			ceil_limit = ceil;
		spage = "";
		for (; st <= ceil_limit; st++) {
			if (page == st || (page == 0 && page == st - 1)) {
				if (page == 0)
					page = 1;
				pMap.put("#", page);
				// spage +="<li class='active'><a href='#'>"+page+"</a></li>";
			} else {
				pMap.put(url + "?p=" + st + para, st);
				// spage +=
				// "<li><a href='"+url+"?p="+st+para+"' >"+st+"</a></li>";
			}
		}

		if (page >= ceil) {
			pMap.put("", "[已到尾页]");
			// npage = "[已到尾页]";
		} else {
			pMap.put(url + "?p=" + (page + 1) + para, ">");

			// npage =
			// "<li><a href='"+url+"?p="+(page+1)+para+"'><i class='fa fa-angle-right'></i></a></li>";
			// npage +=
			// "<a href='"+url+"?p="+ceil+para+"' style='margin:5px;padding:0px;font-size:16px;'>[到尾页]</a>";

		}

		//ppage = (page > 7) ? "<li><a href='" + url + "?p=1&" + para + "&tol="
		//		+ total + "'><i class='fa fa-angle-left'></i></a></li>" : "";
		return pMap;
		// return ceil>1?ppage+spage+npage:spage;

	}
}
