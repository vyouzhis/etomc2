package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public abstract class SugarCRMLogout extends SugarCRMObject implements SugarCRMInterFace {

	public SugarCRMLogout(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String action() {
		// TODO Auto-generated method stub
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		String parame_json = JSON.toJSONString(Para);
		String rec = call("logout", parame_json);
		return rec;
	}
}
