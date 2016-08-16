package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public abstract class Get_Entries extends SugarCRMObject implements SugarCRMInterFace {
	
	
	public Get_Entries(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}

	public String action() {
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		Para.put("module_name", module_name());
		Para.put("ids", ids());
		Para.put("select_fields", select_fields());
		Para.put("link_name_to_fields_array", link_name_to_fields_array());
		Para.put("track_view", track_view());
		String parame_json = JSON.toJSONString(Para);
		
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}

	public abstract String module_name();
	public abstract List<String> ids();
	public abstract List<String> select_fields();
	public abstract List<List<Map<String, Object>>> link_name_to_fields_array();
	public abstract boolean track_view();
}
