package com.lib.icore;

import java.sql.SQLException;
import java.util.Map;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.etc.UrlClassList;

public class icore_action extends BaseiCore {
	private String className = null;

	public icore_action() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub

		Save();
		UrlClassList ucl = UrlClassList.getInstance();
		TipMessage(ucl.BuildUrl("icore", time() + ""), _CLang("ok_save"));
	}

	private void Save() {
		Map<String, Object> res = UnPackUinfo();
		
		String nickname = porg.getKey("nickname");
		String whereNick = "";
		if (nickname != null && nickname.length() > 1) {
			whereNick = "`nickname` = '" + nickname + "',";
			res.put("nickname", nickname);
		}
		String addree = porg.getKey("addree");
		String whereAddr = "";
		if (addree != null && addree.length() > 2) {
			whereAddr = "`addr`='" + addree + "', ";
			res.put("addr", addree);
		}

		int sex = toInt(porg.getKey("sex"));

		String phone = porg.getKey("phone");
		String wherePhone = "";
		if (phone != null && phone.length() > 9) {
			phone = phone.replace("-", "");
			wherePhone = "`phone` = '" + phone + "',";
			res.put("phone", phone);
		}
		String rdesc = porg.getKey("rdesc");

		String format = "UPDATE `stock_user_info` SET"
				+ " %s  `etime` = '%d', %s  %s `sex`='%d', `rdesc`='%s' "
				+ "WHERE `stock_user_info`.`uid` = %d;";

		String sql = String.format(format, whereNick, time(), wherePhone,
				whereAddr, sex, rdesc, igetUid());

		
		if (res != null) {							
			res.put("sex", sex + "");
			res.put("rdesc", rdesc);
			PackUinfo(res);
		}

		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
