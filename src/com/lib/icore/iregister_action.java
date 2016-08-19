package com.lib.icore;

import java.sql.SQLException;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

import com.alibaba.fastjson.JSON;
import com.log.base.SQLErrorLog;

public class iregister_action extends BaseSurface {
	private String className = null;
	private boolean isOK=false;
	
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

		//List<String> rmc = porg.getRmc();
		//String usalt = rmc.get(1);
		//echo(usalt);

		String salt = porg.getKey("salt");
		String csalt = SessAct.GetSession(globale_config.SessSalt);
		echo("salt:" + salt + " csalt:" + csalt);

		// if(!salt.equals(csalt)){
		// echo(salt+"__"+csalt);
		//
		// smsg.ShowMsg(ucl.BuildUrl("iregister", ""));
		// }
		

		AddUser();
		setRoot("isOK", isOK);
		super.View();
	}

	private void AddUser() {

		int now = time();
		String format = "INSERT INTO `"
				+ DB_STOCK_PRE
				+ "user_info` ( `passwd`, `nickname`, `email`, `ctime`, `phone`,  `ip`) "
				+ "VALUES ('%s', '%s', '%s', '%d',  '%s', '%s');";

		String sql = String.format(format, 
				porg.getKey("password"), porg.getKey("nickname"),
				porg.getKey("email"), now, porg.getKey("phone").replace("-", ""), porg.GetIP());
		
		try {
			insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			echo(e.getMessage());
		}
		//echo("++");
		if(getErrorMsg() == null){
			isOK = true;
		}
	}
}
