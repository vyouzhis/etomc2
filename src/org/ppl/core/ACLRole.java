package org.ppl.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ACLRole extends ACLBase {

	/**
	 * @since manager acl check role
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkRole() {
		List<String> rmc = porg.getRmc();
		if (rmc.size() < 2)
			return true;
		String action = rmc.get(1).toString();
		String libName = rmc.get(0).toString();
		Map<String, String> act = new HashMap<>();
		act.put("read", "0");
		act.put("create", "1");
		act.put("edit", "2");
		act.put("remove", "3");
		act.put("search", "4");

		String role = aclfetchMyRole();
		JSONObject jsonRole = JSON.parseObject(role);
		if(jsonRole==null){
			return false;
		}
		for (String key : jsonRole.keySet()) {
			Object libObject = jsonRole.get(key);

			Map<String, Object> lib = JSON.parseObject(libObject.toString(),
					Map.class);
			if (lib.containsKey(libName)) {
				String myAct = lib.get(libName).toString();
				String search = act.get(action);
				if (search == null)
					return false;
				if (myAct.matches("(.*)" + act.get(action) + "(.*)")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @since manager acl role update
	 * @return
	 */
	public int RoleUpdate() {
		int uid = aclGetUid();
		int gid = aclGetGid();
		return InitGroup(uid, gid);
	}
		
}
