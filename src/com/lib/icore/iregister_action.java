package com.lib.icore;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message.RecipientType;
import javax.print.attribute.HashAttributeSet;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.net.MailSender;

import com.alibaba.fastjson.JSON;

public class iregister_action extends BaseSurface {
	private String className = null;
	private boolean isOK = false;

	public iregister_action() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub

		ShowMessage smsg = ShowMessage.getInstance();
		UrlClassList ucl = UrlClassList.getInstance();

		List<String> rmc = porg.getRmc();
		
		String salt = porg.getKey("salt");

		if (rmc.get(0).equals("register_act")) {
					
			if (!checkSalt(salt)) {
				smsg.ShowMsg(ucl.Url("register_error/" + salt+"?arg=0"));
				return;
			}

			AddUser();
			if (isOK) {
				smsg.ShowMsg(ucl.Url("register_ok/" + salt));
			} else {
				smsg.ShowMsg(ucl.Url("register_error/"+ salt+"?arg=1" ));
			}
		} else {
			if (rmc.get(0).equals("register_ok")) {
				setRoot("isOK", -1);
			} else {
				setRoot("isOK", porg.getKey("arg"));
			}

			super.View();
		}
	}

	private void AddUser() {

		int now = time();
		String format = "INSERT INTO `"
				+ DB_STOCK_PRE
				+ "user_info` ( `passwd`, `nickname`, `email`, `ctime`, `phone`,  `ip`) "
				+ "VALUES ('%s', '%s', '%s', '%d',  '%s', '%s');";
		
		String pwd = porg.getKey("password");
		
		String sql = String.format(format, pwd, porg
				.getKey("nickname"), porg.getKey("email"), now,
				porg.getKey("phone").replace("-", ""), porg.GetIP());

		try {
			insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			//echo(e.getMessage());
			//isOK = false;
		}
		//echo(getErrorMsg());
		// echo("++");
		if (getErrorMsg() == null) {
			isOK = true;
		}
		
		ActiveEmail(porg.getKey("email"));
	}
	
	private void ActiveEmail(String email) {
		Map<String, Object> message = new HashMap<>();
		message.put("email", email);
		message.put("subject", _MLang("EmailSubject"));
		message.put("text", "text");
		String json = JSON.toJSONString(message);
		
		TellPostMan("ActiveEmail", json);
		
	}
}
