package com.lib.icore;

import java.sql.SQLException;
import java.util.List;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;

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
		String csalt = SessAct.GetSession(globale_config.SessSalt);
		//echo("salt:" + salt + " csalt:" + csalt);

		if (rmc.get(0).equals("register_act")) {

			if (!salt.equals(csalt)) {
				smsg.ShowMsg(ucl.Url("register_error/" + csalt));
				return;
			}

			AddUser();
			if (isOK) {
				smsg.ShowMsg(ucl.Url("register_ok/" + csalt));
			} else {
				smsg.ShowMsg(ucl.Url("register_error/" + csalt));
			}
		} else {
			if (rmc.get(0).equals("register_ok")) {
				setRoot("isOK", true);
			} else {
				setRoot("isOK", false);
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

		Encrypt ec = Encrypt.getInstance();
		String pwd = ec.MD5(porg.getKey("password"));
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
