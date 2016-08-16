package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @since Create or update a list of records.
 * @since http://support.sugarcrm.com/02_Documentation/04_Sugar_Developer/Sugar_Developer_Guide_7.6/70_API/Web_Services/Legacy_Web_Services/05_Method_Calls/set_entries/
 *
 */
public abstract class Set_Entries extends SugarCRMObject implements SugarCRMInterFace {

	public Set_Entries(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}
	
	public String action() {
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
