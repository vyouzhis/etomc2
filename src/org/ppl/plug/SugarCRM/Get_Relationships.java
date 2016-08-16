package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public abstract class Get_Relationships extends SugarCRMObject implements
		SugarCRMInterFace {

	public Get_Relationships(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String action() {
		// TODO Auto-generated method stub
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		Para.put("module_name", module_name());
		Para.put("module_id", module_id());
		Para.put("link_field_name", link_field_name());
		Para.put("related_module_query", related_module_query());
		Para.put("related_fields", related_fields());
		Para.put("related_module_link_name_to_fields_array",
				related_module_link_name_to_fields_array());
		Para.put("deleted", deleted());
		Para.put("order_by", order_by());
		Para.put("offset", offset());
		Para.put("limit", limit());

		String parame_json = JSON.toJSONString(Para);

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}

	public abstract String module_name();

	public abstract String module_id();

	public abstract String link_field_name();

	public abstract String related_module_query();

	public abstract List<String> related_fields();

	public abstract List<List<Map<String, Object>>> related_module_link_name_to_fields_array();

	public abstract int deleted();

	public abstract String order_by();

	public abstract int offset();

	public abstract int limit();

}
