package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @since Retrieves a specific document revision.
 *
 */
public abstract class Get_Document_Revision extends SugarCRMObject  implements SugarCRMInterFace{

	
	public Get_Document_Revision(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}

	public String action() {
		Map<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("session", this.SessionID);
		parameters.put("i", i());
		String parame_json = JSON.toJSONString(parameters);
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}

	public abstract String i();
}
