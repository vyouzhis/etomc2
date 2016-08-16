package org.ppl.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.ppl.common.function;

public class ProjectPath extends function {
	static ProjectPath pp = null;

	public static ProjectPath getInstance() {
		if (pp == null) {
			pp = new ProjectPath();
		}

		return pp;
	}

	public URI ViewDir() {
		// String path = getServletContext().getRealPath("/");
		return getPath("/theme/default");
	}

	public URI DataDir() {
		return getPath("../../Data/");
	}

	public boolean mkDir(String dir) {
		File f = null;
		boolean bool = false;
		URI u = DataDir();
		if (dir == null)
			return false;

		String newDir = u.getPath() + dir;

		try {
			// returns pathnames for files and directory
			f = new File(newDir);
			// create
			bool = f.mkdir();
			// print
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}

		return bool;
	}

	public boolean isDir(String dir) {

		File f = null;
		boolean bool = false;
		URI u = DataDir();
		if (dir == null)
			return false;

		String newDir = u.getPath() + dir;

		try {
			// returns pathnames for files and directory
			f = new File(newDir);
			// create
			bool = f.isDirectory();			
			// print
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}

		return bool;
	}

	private URI getPath(String baseName) {

		URL path = ProjectPath.class.getClassLoader().getResource(baseName);

		URI p = null;
		try {
			p = path.toURI();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return p;
	}

	public void SaveFile(String name, byte[] val, boolean apend) {
		URI u = DataDir();
		if (name == null)
			return;
		if (val == null) {
			return;
		}
		String f = u.getPath() + name;
		try {

			FileOutputStream file = new FileOutputStream(f, apend);
			file.write(val);
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFile(String name) {
		URI u = DataDir();
		String data = "";
		int len = 1024;

		String f = u.getPath() + name;
		try {
			FileInputStream file = new FileInputStream(f);
			byte[] bytes = new byte[len];
			while (file.read(bytes) != -1) {
				data += new String(bytes);
			}
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public boolean findFile(String name) {

		URI u = DataDir();

		String f = u.getPath();
		File mf = new File(f);

		for (String key : mf.list()) {
			// echo("key:"+key);
			if (key.equals(name + ".png"))
				return true;
		}
		return false;
	}

}
