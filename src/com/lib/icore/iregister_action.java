package com.lib.icore;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.io.Encrypt;

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

		setRoot("login", ucl.Url("login"));

		List<String> rmc = porg.getRmc();

		String salt = porg.getKey("salt");

		if (rmc.get(0).equals("register_act")) {

			if (!checkSalt(salt)) {
				smsg.Redirect(ucl.Url("register_error/" + salt + "?arg=0"));
				return;
			}

			AddUser(salt);
			if (isOK) {
				smsg.Redirect(ucl.Url("register_ok/" + salt));
			} else {
				smsg.Redirect(ucl.Url("register_error/" + salt + "?arg=1"));
			}
		} else {
			if (rmc.get(0).equals("register_ok")) {
				setRoot("isOK", -1);
			} else if (rmc.get(0).equals("active")) {
				ActiveUser();
			} else {
				setRoot("isOK", toInt(porg.getKey("arg")));
			}

			super.View();
		}
	}

	private void AddUser(String salt) {

		int now = time();
		String format = "INSERT INTO `"
				+ DB_STOCK_PRE
				+ "user_info` ( `passwd`, `nickname`, `email`, `ctime`, `phone`,  `ip`, `salt`) "
				+ "VALUES ('%s', '%s', '%s', '%d',  '%s', '%s', '%s');";

		String pwd = porg.getKey("password");

		String sql = String.format(format, pwd, porg.getKey("nickname"), porg
				.getKey("email"), now, porg.getKey("phone").replace("-", ""),
				porg.GetIP(), salt);
		//echo(sql);
		try {
			insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			// echo(e.getMessage());
			// isOK = false;
		}
		 //echo(getErrorMsg());
		// echo("++");
		if (getErrorMsg() == null) {
			isOK = true;
		}

		ActiveEmail(porg.getKey("email"), salt);
	}

	private void ActiveEmail(String email, String salt) {
		Map<String, Object> message = new HashMap<>();

		String sql = "SELECT html FROM `theme_html`  where types='mail'";
		Map<String, Object> res = FetchOne(sql);
		if (res == null) {
			echo("mail theme is null");
			return;
		}
		
		Encrypt en = Encrypt.getInstance();
		
		String html = res.get("html").toString();
		UrlClassList ucl = UrlClassList.getInstance();
		String aurl = "http://www.etomc2.com/" + ucl.Url("active") + "/"
				+ salt + "?h=" + en.toHex(email);

		String[] es = email.split("@");
		es[0] = es[0].substring(0, 2);
		String nemail = es[0] + "***@" + es[1];

		html = html.replace("@URL@", aurl);
		html = html.replace("@EMAIL@", nemail);

		message.put("email", email);
		message.put("subject", _MLang("EmailSubject"));
		message.put("salt", salt);
		message.put("text", html);

		//echo(html);

		String json = JSON.toJSONString(message);

		TellPostMan("ActiveEmail", json);

	}

	private void ActiveUser() {
		List<String> rmc = porg.getRmc();
		String salt = rmc.get(1).toString();
		String email = porg.getKey("h");
		if (email == null || salt == null) {
			setRoot("isOK", 3);
			return;
		}
		
		//echo(salt);
		//echo(email);
		Encrypt en = Encrypt.getInstance();
		email = en.hexToString(email);
		
		String format = "SELECT uid,salt,active FROM `stock_user_info` where email='%s' limit 1";
		String sql = String.format(format, email);
		//echo(sql);
		Map<String, Object> res = FetchOne(sql);

		if (res == null) {
			setRoot("isOK", 3);
			return;
		}

		if (toInt(res.get("active")) == 1) {
			setRoot("isOK", 4);
			return;
		}

		if (!res.get("salt").toString().equals(salt)) {
			setRoot("isOK", 3);
			return;
		}

		sql = "UPDATE `stock_user_info` SET `active` = '1',`atime`="+time()+" WHERE `stock_user_info`.`uid` = "
				+ res.get("uid");

		try {
			update(sql);
			setRoot("isOK", 5);
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			setRoot("isOK", 3);
			return;
		}

	}
}
