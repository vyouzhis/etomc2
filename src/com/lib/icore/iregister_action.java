package com.lib.icore;

import java.sql.SQLException;
import java.util.List;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

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
			String kaptcha = porg.getKey("kaptchaImage");
			String sess_kaptcha = SessAct.GetSession(globale_config.KaptchSes);
			
			if(!kaptcha.equals(sess_kaptcha)){
				smsg.ShowMsg(ucl.Url("register_error/" + salt+"?arg=2"));
				return;
			}
			
			if (!checkSalt(salt)) {
				smsg.ShowMsg(ucl.Url("register_error/" + salt+"?arg=0"));
				return;
			}

			AddUser();
			if (isOK) {
				smsg.ShowMsg(ucl.Url("register_ok/" + salt));
			} else {
				smsg.ShowMsg(ucl.Url("register_error"+ salt+"?arg=1" ));
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
		echo(getErrorMsg());
		// echo("++");
		if (getErrorMsg() == null) {
			isOK = true;
		}
	}
}
