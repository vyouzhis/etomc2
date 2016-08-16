package org.ppl.plug.SugarCRM;

import org.ppl.core.PObject;
import org.ppl.net.cUrl;

public class SugarCRMObject extends PObject{

	static final String url = "http://www.suitecrm.com/service/v4_1/rest.php";
	
	protected String SessionID;
	public SugarCRMObject(String SessionID) {
		// TODO Auto-generated constructor stub
		this.SessionID = SessionID;
	}
	
	protected String call(String method, String parameters_json) {

		cUrl mCUrl = new cUrl();
		mCUrl.addParams("method", method);
		mCUrl.addParams("input_type", "JSON");
		mCUrl.addParams("response_type", "JSON");
		mCUrl.addParams("rest_data", parameters_json);

		String getResult = mCUrl.httpPost(url);

		return getResult;
	}
	

}
