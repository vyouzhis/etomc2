package org.ppl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserCoreDB extends DBSQL {
	private Connection c = null;
	private String driverClassName = null;
	private String dbUrl = null;
	private String dbUser = null;
	private String dbPwd = null;

	public boolean Init() {
		if (driverClassName == null || dbUrl == null || dbUrl == null
				|| dbPwd == null)
			return false;

		try {
			c = createConnection(driverClassName, dbUrl, dbUser, dbPwd);
			c.setAutoCommit(true);
			SetCon(c);
			return true;

		} catch (Exception e) {

			setErrorMsg(e.getMessage());
		}
		return false;
	}

	public void DBEnd() {
		end();

		try {
			// c.commit();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection createConnection(String driver, String url,
			String username, String password) throws ClassNotFoundException,
			SQLException {
		Class.forName(driver);
		if ((username == null) || (password == null)
				|| (username.trim().length() == 0)
				|| (password.trim().length() == 0)) {
			return DriverManager.getConnection(url);
		} else {
			return DriverManager.getConnection(url, username, password);
		}
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}
}
