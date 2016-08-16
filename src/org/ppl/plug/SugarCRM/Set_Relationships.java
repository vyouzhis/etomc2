package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public abstract class Set_Relationships extends SugarCRMObject implements SugarCRMInterFace{

	public Set_Relationships(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String action() {
		// TODO Auto-generated method stub
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		Para.put("module_names", module_name());
		Para.put("module_ids", module_ids());
		Para.put("link_field_names", link_field_name());
		
		Para.put("related_ids", related_ids());
		
		Para.put("name_value_lists",
				name_value_lists());
		Para.put("deleted_array", deleted_array());
	

		String parame_json = JSON.toJSONString(Para);

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}
	
	public abstract List<String> module_name();

	public abstract List<String> module_ids();

	public abstract List<String> link_field_name();
	
	public abstract List<String> related_ids();

	public abstract List<List<Map<String, Object>>> name_value_lists();

	public abstract int deleted_array();

}
