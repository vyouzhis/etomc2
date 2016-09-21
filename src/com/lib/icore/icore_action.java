package com.lib.icore;

import java.sql.SQLException;
import java.util.Map;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.etc.UrlClassList;

public class icore_action extends BaseiCore{
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
		String nickname = porg.getKey("nickname");
		String addree = porg.getKey("addree");
		int sex = toInt(porg.getKey("sex"));
		String phone = porg.getKey("phone").replace("-", "");
		String rdesc = porg.getKey("rdesc");
		
		String format = "UPDATE `stock_user_info` SET"
				+ " `nickname` = '%s', `etime` = '%d', `phone` = '%s', `addr`='%s', `sex`='%d', `rdesc`='%s' "
				+ "WHERE `stock_user_info`.`uid` = %d;";
		
		String sql = String.format(format, nickname, time(),phone, addree,sex,rdesc, igetUid());
				
		Map<String, Object> res = UnPackUinfo();
		if (res != null) {
			res.put("nickname", nickname);
			res.put("phone", phone);
			res.put("addr", addree);
			res.put("sex", sex+"");
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
