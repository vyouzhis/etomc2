package org.ppl.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.ppl.core.PObject;
import org.ppl.etc.globale_config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionPool extends PObject {
	static HikariConnectionPool source;
	public HikariDataSource ds = null;
	private Boolean initialized;
	static{
		source = new HikariConnectionPool();
	}
	
	public static HikariConnectionPool getInstance() {
	
		return source;
	}

	public HikariConnectionPool() {
		// TODO Auto-generated constructor stub
		InitConnect();

	}
	
	private void InitConnect() {
		initialized = false;
		String className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
						
		LoadDBLib();

		HikariConfig config = new HikariConfig();
		// echo(mConfig.GetValue("database.url"));
		config.setJdbcUrl(myConfig.GetValue("database.url"));
		config.setUsername(myConfig.GetValue("database.username"));
		config.setPassword(myConfig.GetValue("database.password"));

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.addDataSourceProperty("useServerPrepStmts", "true");
		
		ds = new HikariDataSource(config);
		ds.setMaximumPoolSize(myConfig.GetInt("database.MaximumPoolSize"));
		initialized = true;
	}

	private void LoadDBLib() {
		String ldbl = myConfig.GetValue("database.driverClassName");
		try {
			Class.forName(ldbl);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void GetCon() {
		if(!initialized){
			throw new UnsupportedOperationException("Debe inicializar mediante el método Init() primero!!!!!."); 
		}
		
		synchronized (globale_config.GDB) {	
			long tid = myThreadId();
			
			Connection con;
			try {
				con = ds.getConnection();	
				
				con.setAutoCommit(false);
				globale_config.GDB.put(tid, con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	}

	public void free() {
		synchronized (globale_config.GDB) {
			long tid = myThreadId();
			Connection con = globale_config.GDB.get(tid);
			if (con != null) {
				try {					
					con.commit();						
					con.close();
					
					//echo("close db");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
													
				globale_config.GDB.put(tid, null);
			}
			

		}
	}
}
