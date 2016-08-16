package org.ppl.etc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class Config {

	private Properties p;

	public Config(String file) {
		// TODO Auto-generated constructor stub

		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(file);
		p = new Properties();
		try {
			p.load(inputStream);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public Properties getPro() {
		return p;
	}

	public String GetValue(String key) {
		String val = p.getProperty(key);
		if (val != null) {
			return val.trim();
		}
		return null;
	}
	
	public int GetInt(String key) {
		return Integer.valueOf(p.getProperty(key).trim());
	}
	
	public Set<Object> getKey() {
		return p.keySet();
	}
}
