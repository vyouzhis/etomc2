package org.ppl.core;

import java.util.Map;

public class ACLBase extends ACLInit {

	/**
	 * @since manager acl check who is access
	 * @return
	 */
	public boolean aclCheckAccess() {
		int uid = aclGetUid();
		String cm = aclgetCM();
		
		if(isAlone()==true)return true;
		
		String format = "SELECT uid FROM `" + DB_PRE
				+ "user_info` WHERE uid =%d and cm='%s' LIMIT 1";
		String sql = String.format(format, uid, cm);

		Map<String, Object> res = FetchOne(sql);
		if (res != null && res.get("uid") != null) {
			return true;
		} else {
			aclLogout();
			return false;
		}
	}

	public boolean isAlone() {
		int alone = mConfig.GetInt("login.alone");
		//int uid = aclGetUid();
		
		if (alone == 0)
			return true;
		return false;		 
	}
}
