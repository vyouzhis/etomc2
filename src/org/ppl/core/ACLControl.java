package org.ppl.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ppl.BaseClass.BasePrograma;
import org.ppl.BaseClass.Permission;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class ACLControl extends ACLRole {

	/**
	 * @since manager acl load class lib
	 */
	public void aclLoadLib() {
		Injector injector = globale_config.injector;
		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();
		// HashMap<String, HashMap> selects = new HashMap<String, HashMap>();

		for (Entry<String, List<String>> entry : PackClassList.entrySet()) {
			// String key = entry.getKey();

			List<String> value = entry.getValue();
			for (int i = 0; i < value.size(); i++) {
				if (value.get(i).substring(value.get(i).length() - 6)
						.equals("_index")) {
					BasePrograma Index = (BasePrograma) injector
							.getInstance(Key.get(BasePrograma.class,
									Names.named(value.get(i))));
					Index._MLang("name");
				} else {
					Permission home = (Permission) injector.getInstance(Key
							.get(Permission.class, Names.named(value.get(i))));
					home._MLang("name");
					home._MLang("desc");
				}
			}
		}
	}

	/**
	 * @since main menu name
	 * @param value
	 * @return
	 */
	public String IndexName(String value) {

		if (value.matches("(.*)_index")) {
			Injector injector = globale_config.injector;
			BasePrograma Index = (BasePrograma) injector.getInstance(Key.get(
					BasePrograma.class, Names.named(value)));
			return Index._CLang(value);
		}
		return "not has lan key";
	}

	/**
	 * @since lib info
	 * @param value
	 * @return
	 */
	public Map<String, String> LibInfo(String value) {
		Injector injector = globale_config.injector;
		Map<String, String> info = null;
		try {
			info = new HashMap<String, String>();
			Permission home = (Permission) injector.getInstance(Key.get(
					Permission.class, Names.named(value)));
			info.put("name", home._MLang("name"));
			info.put("desc", home._MLang("desc"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return info;
	}

	/**
	 * @since who login time out, def: session.ontime
	 * @return
	 */
	public boolean CheckOntime() {

		if(isAlone()==true)return true;

		String time = SessAct.GetSession(mConfig
				.GetValue(globale_config.Ontime));
		if (time == null)
			return false;

		int ontime = Integer.valueOf(time);

		int now = time();
		int timeOut = mConfig.GetInt(globale_config.TimeOut);
		if (now - ontime > timeOut) {
			List<String> rmc = porg.getRmc();
			if (rmc.size() < 2) {
				aclLogout();
				return true;
			} else {
				return false;
			}
		}

		SessAct.SetSession(mConfig.GetValue(globale_config.Ontime), now + "");

		return true;
	}

}
