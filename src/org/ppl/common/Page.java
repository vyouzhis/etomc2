package org.ppl.common;

public class Page extends function{

	public String s_page(String url, int total, int page, int limit, String para) {

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
				spage += "<b class='here_num' style='margin:5px;padding:0px;font-size:16px;'>"
						+ page + "</b>";
			} else {
				spage += "<a href='" + url + "?p=" + st + para
						+ "' style='margin:5px;padding:0px;font-size:16px;'>"
						+ st + "</a>";
			}
		}

		if (page >= ceil) {
			npage = "[已到尾页]";
		} else {

			npage = "<a href='"
					+ url
					+ "?p="
					+ (page + 1)
					+ para
					+ "' style='margin:5px;padding:0px;font-size:16px;'>[下一页]</a>";
			npage += "<a href='"
					+ url
					+ "?p="
					+ ceil
					+ para
					+ "' style='margin:5px;padding:0px;font-size:16px;'>[到尾页]</a>";

		}

		ppage = (page > 7) ? "<a href='" + url + "?p=1&" + para + "&tol="
				+ total
				+ "' style='margin:5px;padding:0px;font-size:16px;'>[回首页]</a>"
				: "";

		return ceil > 1 ? ppage + spage + npage : spage;
	}

	public String base_page(String url, int total, int page, int limit,
			String para) {

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

				spage += "#@" + page + ";";
			} else {

				spage += url + "?p=" + st + para + "@" + st + ";";
			}
		}

		if (page >= ceil) {
			npage = ""; //"#@[已到尾页]"
		} else {

			npage = url + "?p=" + (page + 1) + para + "@[right];";
			npage += url + "?p=" + ceil + para + "@[last];";

		}

		ppage = (page > 7) ? url + "?p=1&" + para + "&tol=" + total
				+ "@[left];" : "";

		return ceil > 1 ? ppage + spage + npage : spage;

	}
	
	public String getDefPage(String url , int p, int tol, int limit, String onclickFun) {

		String pageString = base_page(url, tol, p, limit, "");
				
		String[] pp = pageString.split(";");
		
		if(pp.length == 0 ) return "";
		String Listli = "";
		String CurrPage = "1";
		for (int i = 0; i < pp.length; i++) {
			String[] nn = pp[i].split("@");
			
			if(nn.length <= 1) continue;
			
			if(nn[0].equals("#")){
				CurrPage =nn[1];
				Listli+="<li class='active'><a href='#'>"+nn[1]+"</a></li>";
			}else if (nn[1].equals("[left]")) {
				Listli+="<li><a href='javascript:void(0)' onclick="+onclickFun+"(1)><i class='fa fa-angle-left'></i></a></li>";
			}else if (nn[1].equals("[right]")) {
				int pnum = toInt(CurrPage);
				pnum++;
				Listli+="<li><a href='javascript:void(0)' onclick="+onclickFun+"("+pnum+") ><i class='fa fa-angle-right'></i></a></li>";
			}else if (nn[1].equals("[last]")) {
				
			}else {
				Listli+="<li><a  href='javascript:void(0)' onclick="+onclickFun+"("+nn[1]+") >"+nn[1]+"</a></li>";
			}
		}
		
		return Listli;
	}
}
