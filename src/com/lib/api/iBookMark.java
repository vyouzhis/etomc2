package com.lib.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseiCore;

import com.alibaba.fastjson.JSON;

public class iBookMark extends BaseiCore {
	private String className = null;

	public iBookMark() {
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

			int act = toInt(porg.getKey("act"));
			if (act == 0) {
				bmset();

			} else if (act == 1) {
				bmgetCode();
			} else {
				bmgetStra();
			}
		}

	}

	private void bmset() {
		int sid = toInt(porg.getKey("sid"));
		int cid = toInt(porg.getKey("cid"));
		String code = porg.getKey("code");
		String name = porg.getKey("name");

		if (code == null || code.length() == 0 || cid == 0 || sid == 0
				|| name == null || name.length() == 0) {
			super.setHtml("-1");
			return;
		}

		int uid = igetUid();
		int now = time();

		String format = "INSERT INTO `stock_strategy` "
				+ "( `uid`, `code`,`name`, `cid`, `sid`, `ctime`) "
				+ "VALUES ( %d, '%s', '%s', '%d', '%d', '%d');";

		String sql = String.format(format, uid, code, name, cid, sid, now);

		List<Map<String, Object>> res = checksid(uid, cid, code);

		if (res != null) {
			if (res.size() >= 3) {
				super.setHtml("-2");
				return;
			} else {
				for (Map<String, Object> k : res) {
					if (toInt(k.get("sid").toString()) == sid) {
						super.setHtml("-3");
						return;
					}
				}
			}
		}

		int st = checkcount(uid, cid, code);

		if (st >= 3) {
			super.setHtml("-4");
			return;
		}

		try {
			insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.setHtml("0");
	}

	private List<Map<String, Object>> checksid(int uid, int cid, String code) {
		String format = "SELECT sid FROM `stock_strategy`  "
				+ "where uid=%d and isstop = 0 and cid = '%d' AND code = '%s' ";

		String sql = String.format(format, uid, cid, code);

		List<Map<String, Object>> res = null;
		try {
			res = FetchAll(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	private int checkcount(int uid, int cid, String code) {
		String format = "select count(*) as st from (SELECT code FROM `stock_strategy` WHERE isstop = 0 AND uid=%d and cid=%d and code!='%s' GROUP by code) t";

		String sql = String.format(format, uid, cid, code);

		Map<String, Object> res = FetchOne(sql);
		if (res != null) {
			return toInt(res.get("st").toString());
		}

		return 0;
	}

	private void bmgetCode() {
		int cid = toInt(porg.getKey("cid"));
		int uid = igetUid();
		
		String format = "SELECT id, code,name FROM `stock_strategy` WHERE isstop=0 AND cid='%d' and uid=%d GROUP BY code ";
		String sql = String.format(format, cid, uid);
		
		List<Map<String, Object>> res = null;
		try {
			res = FetchAll(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = "";
		
		if(res!=null){
			json = JSON.toJSONString(res);
		}
		
		super.setHtml(json);
		
	}

	private void bmgetStra() {
		int cid = toInt(porg.getKey("cid"));
		String code = porg.getKey("code");
		int uid = igetUid();
		
		String format = "SELECT t.id, t.title FROM `stock_strategy` s, strategy_stock t"
				+ " WHERE s.uid=%d and s.cid=%d and s.code='%s' AND s.isstop = 0 and t.id = s.sid  "
				+ "order by s.ctime DESC";
				
		String sql = String.format(format,  uid, cid, code);
		
		List<Map<String, Object>> res = null;
		try {
			res = FetchAll(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = "";
		
		if(res!=null){
			json = JSON.toJSONString(res);
		}
		
		super.setHtml(json);
	}

	private void ListCodeStra() {
		String format = "SELECT s.id,s.code,s.sid,t.title FROM `stock_strategy` s, strategy_stock t WHERE s.uid=%d and s.cid=%d AND s.isstop = 0 and t.id = s.sid ORDER BY s.code ";
		int cid = mConfig.GetInt("Step.strategy");
		int uid = igetUid();
		String sql = String.format(format, uid, cid);

		List<Map<String, Object>> res = null;

		try {
			res = FetchAll(sql);
			if (res != null) {

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
