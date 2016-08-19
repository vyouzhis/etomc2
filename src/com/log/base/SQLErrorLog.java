package com.log.base;

import org.ppl.core.PObject;
import org.ppl.io.Encrypt;

public class SQLErrorLog extends PObject {
	static SQLErrorLog sel = null;

	public static SQLErrorLog getInstance() {
		// TODO Auto-generated constructor stub
		if (sel == null) {

			sel = new SQLErrorLog();
		}
		return sel;
	}

	public String SELog(String data, String error) {
		String sql = null;
		if (mConfig.GetInt("SELOG") == 1) {
			String format = "INSERT INTO `error_log_sql` (`data`, `error`, `ctime`) "
					+ "VALUES ('%s', '%s', '%d')";
			Encrypt en = Encrypt.getInstance();
			String base64Data = en.Base64_Encode(data);
			
			sql = String.format(format, base64Data, error.replace("'", "\\\'"), time());			
		}
		return sql;
	}
}
