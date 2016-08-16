package org.ppl.plug.SugarCRM;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ppl.io.Encrypt;

import com.alibaba.fastjson.JSON;

public abstract class SugarCRMLogin extends SugarCRMObject implements SugarCRMInterFace{
		
	public SugarCRMLogin(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String action() {
		// TODO Auto-generated method stub
		Map<String, Object> Para = new LinkedHashMap<>();
		
		Para.put("user_auth", auth());
		Para.put("application_name", application_name());

		String parame_json = JSON.toJSONString(Para);

		String login_json = call("login", parame_json);

		return handle(login_json);
	}
	
	public Map<String, String> auth(){
		Encrypt en = Encrypt.getInstance();
		String USERNAME = "admin";
		String PASSWORD = "123456";
		
		Map<String, String> user_auth = new HashMap<>();
		user_auth.put("user_name", USERNAME);
		user_auth.put("password", en.MD5(PASSWORD));
		user_auth.put("version", "1");
		return user_auth;
	}
	public abstract String application_name();
	
	
	public String handle(String res) {
		res = res.trim();
		
		@SuppressWarnings("unchecked")
		List<Object> SessionObject = JSON.parseObject("[" + res + "]",
				List.class);
		if (SessionObject.size() > 0) {
			@SuppressWarnings("unchecked")
			Map<String, Object> SessMap = (Map<String, Object>) SessionObject
					.get(0);
			if (SessMap.containsKey("id")) {
				return SessMap.get("id").toString();
			} else {
				return null;
			}
		}
		return null;
	}

}
