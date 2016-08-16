package org.ppl.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.ppl.io.ProjectPath;

public class DynamicClassObject extends ClassLoader {
	public DynamicClassObject(ClassLoader parent) {
		// TODO Auto-generated constructor stub
		super(parent);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class loadClass(String name) throws ClassNotFoundException {

		String path = ProjectPath.getInstance().DataDir().toString();
		try {
			String url = path+name;
			URL myUrl = new URL(url);
			URLConnection connection = myUrl.openConnection();
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int data = input.read();

			while (data != -1) {
				buffer.write(data);
				data = input.read();
			}

			input.close();

			byte[] classData = buffer.toByteArray();

			return defineClass(name, classData, 0,
					classData.length);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
