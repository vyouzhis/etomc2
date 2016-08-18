package com.lib.icore;

import java.sql.SQLException;
import java.util.List;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

public class iregister_action extends BaseSurface {
	private String className = null;

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
		String usalt = rmc.get(1);
		echo(usalt);

		String salt = porg.getKey("salt");
		String csalt = SessAct.GetSession(globale_config.SessSalt);
		echo("salt:" + salt + " csalt:" + csalt);

		// if(!salt.equals(csalt)){
		// echo(salt+"__"+csalt);
		//
		// smsg.ShowMsg(ucl.BuildUrl("iregister", ""));
		// }

		echo(porg.getAllpg());

		AddUser();
		super.View();
	}

	private void AddUser() {

		int now = time();
		String format = "INSERT INTO `"
				+ DB_STOCK_PRE
				+ "user_info` (`name`, `passwd`, `nickname`, `email`, `ctime`, `phone`,  `ip`) "
				+ "VALUES ('%s', '%s', '%s', '%s', '%d',  '%s', '%s');";

		String sql = String.format(format, porg.getKey("username"),
				porg.getKey("password"), porg.getKey("username"),
				porg.getKey("email"), now, porg.getKey("phone"), porg.GetIP());

		echo(sql);
		try {
			insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			echo(getErrorMsg());
		}
	}
}
