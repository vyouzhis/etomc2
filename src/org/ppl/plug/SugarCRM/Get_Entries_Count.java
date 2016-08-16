package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public abstract class Get_Entries_Count extends SugarCRMObject implements SugarCRMInterFace{

	public Get_Entries_Count(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}


	@Override
	public String action() {
		// TODO Auto-generated method stub
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		Para.put("module_name", module_name());
		Para.put("query", query());
		Para.put("deleted", deleted());
		String parame_json = JSON.toJSONString(Para);
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}
	
	public abstract String module_name();
	public abstract String query();
	public abstract int deleted();
	
}
