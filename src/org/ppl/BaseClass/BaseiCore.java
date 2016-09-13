package org.ppl.BaseClass;

import java.util.Map;

import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;

import com.alibaba.fastjson.JSON;

public class BaseiCore extends BaseSurface {

	protected int Init() {
		String method = porg.getMehtod();
		int h = isLogin();
		
		if(h != -1) return 0;
		
		if (method.toLowerCase().equals("get")) {
			if (h == -1) {
				iLogout();

				return -1;
			}
		} else {			
			String email = porg.getKey("email");
			String passwd = porg.getKey("Password");
			int il = iLogin(email, passwd);
						
			if ( il != 0) {
				iLogout();
				return il;
			}
		}

		return 0;
	}

	public void iLogout() {

		cookieAct.DelCookie(globale_config.Uinfo);
	}

	public int iLogin(String email, String pwd) {
		if (email==null || email.length() < 1)
			return -1;
		if (pwd==null || pwd.length() < 1)
			return -1;

		String user_salt = porg.getKey("salt");
		if (checkSalt(user_salt) == false) {
			return -2;
		}

		String format = "SELECT * FROM `"+DB_STOCK_PRE+"user_info` where email='%s' limit 1;";
		String sql = String.format(format, email);
		
		Map<String, Object> res;

		res = FetchOne(sql);
		
		if (res == null) {
			return -3;
		}

		Encrypt ec = Encrypt.getInstance();
		String check_passd = ec.MD5(res.get("passwd").toString() + user_salt);

		//echo("check_pawd:"+check_passd + " pwd:"+pwd);
		if (!check_passd.equals(pwd))
			return -4;

		String info_json = JSON.toJSONString(res);

		String hex = ec.toHex(info_json);
		
		cookieAct.SetCookie(globale_config.Uinfo, hex);
		return 0;
	}
}
