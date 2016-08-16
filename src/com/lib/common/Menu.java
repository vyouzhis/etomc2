package com.lib.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseModule;
import org.ppl.BaseClass.Permission;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class Menu extends BaseModule {
	private String className = null;
	private JSONObject RoleJson;
	static Menu source;
	
	public Menu() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}
	
	public static Menu getInstance() {
		if (source == null) {
			source = new Menu();
		}

		return source;
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		if (myRole() == false) {
			super.html = "";
			return;
		}
		UrlClassList ucl = UrlClassList.getInstance();
		setRoot("Index_url", ucl.BuildUrl("admin_index", ""));
		setRoot("fun", this);
		
		super.View();
	}
	
	private boolean myRole() {
		String role = aclfetchMyRole();

		if (role == null)
			return false;
		RoleJson = JSON.parseObject(role);
		if (RoleJson == null)
			return false;

		setRoot("menu", JsonToMap(role));
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean isMainMenuActive(String main) {
		List<String> rmc = porg.getRmc();

		Object libs = RoleJson.get(main);

		Map<String, Object> libO = JSON.parseObject(libs.toString(), Map.class);

		return libO.containsKey(rmc.get(0));
	}

	public boolean isLib(String lib) {
		List<String> rmc = porg.getRmc();
		return rmc.get(0).equals(lib);
	}

	private Map<String, Map<String, String>> JsonToMap(String json) {
		JSONObject subJson = JSON.parseObject(json);
		Map<String, Map<String, String>> res = new HashMap<>();

		Map<String, String> subMap;
		for (String key : subJson.keySet()) {
			String dbJson = subJson.getString(key);
			JSONObject dbJ = JSON.parseObject(dbJson);
			subMap = new HashMap<String, String>();
			for (String k : dbJ.keySet()) {
				if(isMenu(k)==0)
					subMap.put(k, dbJ.getString(k));
			}
			res.put(key, subMap);
		}

		return res;
	}

	public String MenuUrl(String lib) {
		UrlClassList ucl = UrlClassList.getInstance();
		return ucl.read(lib);
	}
	
	public String MainName(String lib) {
		
		return _CLang(lib+"_index");
	}
	
	public String libName(String lib) {

		setStdName(lib);
		return _Lang("name");
	}
	
	private int isMenu(String lib) {
		Injector injector = globale_config.injector;
		try {
			Permission home = (Permission) injector.getInstance(Key.get(Permission.class, Names.named(lib)));
			return home.getAction();
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		
	}
}
