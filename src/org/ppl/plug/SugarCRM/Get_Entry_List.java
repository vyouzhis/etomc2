package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public abstract class Get_Entry_List extends SugarCRMObject implements SugarCRMInterFace {

	public Get_Entry_List(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String action() {
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		Para.put("module_name", module_name());
		Para.put("query", query());
		Para.put("order_by", order_by());
		Para.put("offset", offset());
		Para.put("select_fields", select_fields());
		Para.put("link_name_to_fields_array", link_name_to_fields_array());
		Para.put("max_results", max_results());
		Para.put("deleted", deleted());
		Para.put("favorites", favorites());
		
		String parame_json = JSON.toJSONString(Para);
				
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}

	public abstract String module_name();
	public abstract String query();
	public abstract String order_by();
	public abstract int offset();	
	public abstract List<String> select_fields();
	public abstract List<List<Map<String, Object>>> link_name_to_fields_array();
	public abstract int max_results();
	public abstract int deleted();
	public abstract boolean favorites();
}
