package org.ppl.core;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.db.DBSQL;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ACLInit extends DBSQL {

	/**
	 * @since manager acl get uid
	 * @return
	 */
	public int aclGetUid() {
		return toInt(getInfo("Uid"));
	}

	/**
	 * @since manager acl get phone
	 * @return
	 */
	public String aclGetPhone() {
		return getInfo("phone");
	}

	/**
	 * @since manager acl get nick name
	 * @return
	 */
	public String aclGetNickName() {
		return getInfo("NickName");
	}

	/**
	 * @since manager acl get email
	 * @return
	 */
	public String aclGetEmail() {
		return getInfo("email");
	}

	/**
	 * @since manager acl get group id
	 * @return
	 */
	public int aclGetGid() {
		return toInt((getInfo("gid")));
	}

	/**
	 * @since manager acl get login name
	 * @return
	 */
	public String aclGetName() {
		return getInfo("Name");
	}

	/**
	 * @since manager acl get cm
	 * @return
	 */
	public String aclgetCM() {

		return getInfo("CM");
	}

	/**
	 * @since manager acl fetch my role
	 * @return
	 */
	public String aclfetchMyRole() {

		return getInfo("main_role");
	}

	/**
	 * @since manager acl get info
	 * @param key
	 * @return
	 */
	private String getInfo(String key) {
		String aclSess = SessAct.GetSession(mConfig
				.GetValue(globale_config.sessAcl));
		// Object actJson = null;
		if (aclSess == null)
			return null;
		JSONObject actJson = JSON.parseObject(aclSess);
		if (actJson == null)
			return null;
		return actJson.getString(key);
	}

	/**
	 * @since manager update error password count
	 * @param name
	 */
	private void ErrorPWD(String name) {

		int now = time();

		String format = "UPDATE `"
				+ DB_PRE
				+ "user_info` SET `error` = `error`+1, `ltime`=%d WHERE `name` = '%s' LIMIT 1";

		String sql = String.format(format, now, name);
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @since manager acl login
	 * @param name
	 * @param passwd
	 * @param get_salt
	 * @return
	 */
	public int aclLogin(String name, String passwd, String get_salt) {

		String format = "select uid, cm, passwd,nickname,phone,email,ltime,error,gid  from "
				+ DB_PRE + "user_info WHERE  name='%s' limit 1";
		String sql = String.format(format, name);
		Encrypt en = Encrypt.getInstance();

		Map<String, Object> res = FetchOne(sql);

		if (res != null) {
			int ltime = Integer.valueOf(res.get("ltime").toString());
			int error = Integer.valueOf(res.get("error").toString());
			int now = time();
			int uid = 0;

			int delay = mConfig.GetInt(globale_config.TimeDelay);
			if (now - ltime < delay && error > 2)
				return -3;

			String pwd = en.MD5(res.get("passwd") + get_salt);

			if (!passwd.equals(pwd)) {
				ErrorPWD(name);
				return -2;
			}

			String new_cm = en.MD5(now + "");
			format = "UPDATE "
					+ DB_PRE
					+ "user_info SET `cm` = '%s', `ltime`=%d, `error`=0 WHERE `uid`='%d';";
			sql = String.format(format, new_cm, now, res.get("uid"));

			try {
				update(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> UserSess = new HashMap<String, String>();
			UserSess.put("Uid", res.get("uid").toString());
			uid = Integer.valueOf(res.get("uid").toString());
			UserSess.put("NickName", res.get("nickname").toString());
			UserSess.put("phone", res.get("phone").toString());
			UserSess.put("email", res.get("email").toString());
			UserSess.put("gid", res.get("gid").toString());
			UserSess.put("Name", name);
			UserSess.put("CM", new_cm);

			SessAct.SetSession(mConfig.GetValue(globale_config.Ontime), now
					+ "");

			format = "SELECT g.mainrole,g.subrole FROM " + DB_PRE
					+ "user_info u, " + DB_PRE
					+ "group g where u.gid =  g.id and u.uid=%s LIMIT 1";
			sql = String.format(format, res.get("uid").toString());

			Map<String, Object> role = FetchOne(sql);
			if (role != null) {
				UserSess.put("main_role", role.get("mainrole").toString());
				UserSess.put("sub_role", role.get("subrole").toString());
			}else if (uid==1) {
				InitRole(uid, 1);
				return -5;
			}
			else {
				return -4;
			}

			String json = JSON.toJSONString(UserSess);

			SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), json);

			return 0;
		}

		return -1;
	}

	/**
	 * @since manager acl logout
	 */
	public void aclLogout() {
		SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), "");
	}
	
	public void InitRole(int uid, int gid) {
		InitGroup(uid, gid);
		String format = "UPDATE "
				+ DB_PRE
				+ "user_info SET `gid` = '%d' WHERE `uid`='%d';";
		String sql = String.format(format, gid,uid);

		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public int InitGroup(int uid, int gid) {
		if (uid != 1)
			return -1;
		Map<String, Map<String, String>> AdminRole = new HashMap<String, Map<String, String>>();
		String RoleVal = "0_1_2_3_4";

		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();
		// echo(JSON.toJSONString(PackClassList));
		for (String key : PackClassList.keySet()) {
			Map<String, String> subRole = new HashMap<>();
			for (int i = 0; i < PackClassList.get(key).size(); i++) {
				subRole.put(PackClassList.get(key).get(i), RoleVal);
			}
			AdminRole.put(key, subRole);
		}
		String MainRole = JSON.toJSONString(AdminRole);

		String format = "SELECT id FROM `"+DB_PRE+"group`  where id=" + gid;
		Map<String, Object> res;
		res = FetchOne(format);
		
		int now = time();
		String sql = "";
		if (res == null) {
			format = "INSERT INTO `"
					+ DB_PRE
					+ "group` "
					+ "(`gname`, `gdesc`, `mainrole`, `subrole`,`uid`,`ctime`, `etime`)"
					+ " VALUES ('%s', '%s',  '%s', '%s', %d, %d, %d);";
			sql = String.format(format, "default name", "default desc",
					MainRole, "", uid, now, now);

		} else {
			format = "UPDATE `" + DB_PRE
					+ "group` SET " + " `mainrole` = '%s',  `etime` = '%d' "
					+ "WHERE `"+DB_PRE+"group`.`id` = %d;";

			sql = String.format(format, MainRole, now, gid);

		}
		
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
