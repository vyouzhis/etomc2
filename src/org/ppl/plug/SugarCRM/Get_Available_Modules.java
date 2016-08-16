package org.ppl.plug.SugarCRM;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.Session;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @since Retrieves a list of available modules in the system.
 * @since http://support.sugarcrm.com/
 *        02_Documentation/04_Sugar_Developer/Sugar_Developer_Guide_7.6/70_API/Web_Services/Legacy_Web_Services/05_Method_Calls/get_available_module
 *        s /
 * 
 */
public abstract class Get_Available_Modules extends SugarCRMObject  implements SugarCRMInterFace{
	
	
	public Get_Available_Modules(String SessionID) {
		super(SessionID);
		// TODO Auto-generated constructor stub
	}

	public String action() {
		Map<String, Object> Para = new LinkedHashMap<>();
		Para.put("session", this.SessionID);
		Para.put("filter", Filter());
		String parame_json = JSON.toJSONString(Para);
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		clazz = SliceName(clazz);
		String rec = call(clazz.toLowerCase(), parame_json);
		return rec;
	}

	public abstract String Filter();
}
