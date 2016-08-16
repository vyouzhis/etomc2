package org.ppl.common;

import javax.servlet.http.HttpSession;

public class SessionAction {
	private HttpSession session;
	private static SessionAction source;

	public static SessionAction getInstance() {
		if (source == null) {
			source = new SessionAction();
		}

		return source;
	}

	public void init(HttpSession se) {
		session = se;
	}

	public void SetSession(String key, String val) {
		session.setAttribute(key, val);
	}

	public String GetSession(String key) {
		Object val = session.getAttribute(key);
		if (val != null) {
			return session.getAttribute(key).toString();
		} else {
			return null;
		}
	}
}
