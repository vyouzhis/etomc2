package com.lib.api;

import java.sql.SQLException;

import org.ppl.BaseClass.BaseiCore;

public class iTalk extends BaseiCore {
	private String className = null;

	public iTalk() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		super.setAjax(true);

		if (super.Init() == -1) {

			super.setHtml("");
		} else {
			Save();
			super.setHtml("ok");
		}
	}

	private void Save() {
		String format = "INSERT INTO `stock_user_talk` (`pid`, `sid`, `uid`, `msg`, `ctime`, `ip`) VALUES "
				+ "('%d', '%d', '%d', '%s', '%d', '%s');";
				
		int pid = toInt(porg.getKey("pid"));
		int sid = toInt(porg.getKey("sid"));
		int uid = igetUid();
		String msg = porg.getKey("msg");
		String ip = porg.GetIP();

		String sql = String.format(format, pid, sid, uid, msg, time(), ip);
		echo(sql);
		try {
			insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

