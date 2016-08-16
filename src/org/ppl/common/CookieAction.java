package org.ppl.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ppl.core.InitCore;

public class CookieAction extends InitCore{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static CookieAction source;
	private String Path;
	private int TimeOut;

	public static CookieAction getInstance() {
		if (source == null) {
			source = new CookieAction();					
		}

		return source;
	}

	public void init(HttpServletRequest req,HttpServletResponse res) {
		request = req;
		response = res;	
		
		Path = mConfig.GetValue("cookie.path");
		TimeOut = mConfig.GetInt("cookie.timeout");
	}

	public void SetCookie(String key, String val) {			
		CookieFun(key, val, TimeOut);
	}
	
	public void SetCookie(String key, String val, int timeOut) {	
		CookieFun(key, val, timeOut);
	}
	
	private void CookieFun(String key, String val, int timeOut) {
		String ckey = key.trim();
		Cookie userCookie = new Cookie(ckey, val);
		userCookie.setMaxAge(timeOut); //Store cookie for timeOut second
		userCookie.setPath(Path);	
	
		response.addCookie(userCookie);	
	}
	
	public void DelCookie(String key) {		
		Cookie[] cookies = request.getCookies();
		if(cookies==null)return;
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(key)){
				cookie.setValue(null);
				cookie.setMaxAge(0);
				cookie.setPath(Path);
				response.addCookie(cookie);
			}
		}
		
	}

	public String GetCookie(String key) {
		String ckey = key.trim();
		Cookie[] cookies = request.getCookies();
		
		if (cookies==null) {
			return null;
		}
		for(Cookie cookie : cookies){			
		    if(ckey.equals(cookie.getName())){
		        return cookie.getValue();
		    }
		}
	
		return null;
	}
}
