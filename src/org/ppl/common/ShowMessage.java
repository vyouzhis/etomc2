package org.ppl.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowMessage {
	static ShowMessage source;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public static ShowMessage getInstance() {
		if (source == null) {
			source = new ShowMessage();
		}

		return source;
	}

	public void Init(HttpServletRequest req,HttpServletResponse res) {
		request = req;
		response = res;
	}
	
	public void ShowMsg(String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public String SetMsg(String surl, String msg, int limittime) {
		String url = surl;
		
		String htmlhead = "<html><head><title>系统提示</title>";
		htmlhead += "<meta http-equiv=\"Content-Type\" content=\"text/html;\" /><meta charset=\"utf-8\" />";
		htmlhead += "<base target='_self'/>\r\n</head>\r\n";
		htmlhead += "<body leftmargin='0' topmargin='0'>\r\n<center>\r\n<script>\r\n";

		String htmlfoot = "</script>\r\n</center>\r\n</body>\r\n</html>\r\n";		
		int litime = 0;
		String func = "";
		String rmsg = "";
		if (limittime == 0)
		litime = 800;
		else
		litime = limittime;

		if (url.equals("-1")) {
			if (limittime == 0)
				litime = 3000;
			url = "javascript:history.go(-1);";
		}

		if (url.isEmpty()) {
			msg = "<script>alert(\"" +msg.replace("\"", "“")+ "\");</script>";
		} else {
			func = "var pgo=0;function JumpUrl(){if(pgo==0){ location='" + url + "'; pgo=1; }}\r\n";

			rmsg = func;
			rmsg += "document.write(\"<br/>";
			rmsg += "<div style='width:400px;padding-top:4px;height:24;font-size:10pt;border-left:1px solid #b9df92;border-top:1px solid #b9df92;border-right:1px solid #b9df92;background-color:#def5c2;'>提示信息：</div>\");\r\n";
			rmsg += "document.write(\"<div style='width:400px;height:100;font-size:10pt;border:1px solid #b9df92;background-color:#f9fcf3'><br/><br/>\");\r\n";
			rmsg += "document.write(\"" +msg.replace("\"", "“")+ "\");\r\n";
			rmsg += "document.write(\"";

			//if($onlymsg==0){
			if (!url.equals("javascript:;") && !url.isEmpty()) {
				rmsg += "<br/><br/><a href='" + url + "'>如果你的浏览器没反应，请点击这里...</a>";
			}
			rmsg += "<br/><br/></div>\");\r\n";
			if (!url.equals("javascript:;") && !url.isEmpty()) {
				rmsg += "setTimeout('JumpUrl()',"+litime+");";
			}
			//}else{ rmsg .= "<br/><br/></div>\");\r\n"; }
			msg = htmlhead + rmsg + htmlfoot;					
		}
		return msg;
	}
	
	public void forward(String url) {
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);

		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
