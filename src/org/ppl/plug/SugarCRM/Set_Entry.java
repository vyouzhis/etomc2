package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public abstract class Set_Entry extends SugarCRMObject implements SugarCRMInterFace {
	
	public Set_Entry(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String action() {
		// TODO Auto-generated method stub
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		Para.put("module_name", module_name());
		Para.put("name_value_lists", name_value_lists());
		String parame_json = JSON.toJSONString(Para);
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}
	public abstract String module_name();
	public abstract List<List<Map<String, Object>>> name_value_lists();
}
