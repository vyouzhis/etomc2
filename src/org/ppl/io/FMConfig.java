package org.ppl.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ppl.common.function;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FMConfig extends function {
	static FMConfig fmc = null;
	private static Configuration cfg = null;
	private static Map<String, Template> TempList;

	public static FMConfig getInstance() {
		if (fmc == null) {

			fmc = new FMConfig();
			TempList = new HashMap<>();
		}

		return fmc;
	}

	public Template getTemp(String path) {
		if (TempList.containsKey(path) || mConfig.GetInt("CacheTemp")==1)
			return TempList.get(path);
		try {
			ProjectPath pp = ProjectPath.getInstance();
			cfg = new Configuration();

			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setDefaultEncoding("UTF-8");
			try {
				File file = new File(pp.ViewDir());
				cfg.setDirectoryForTemplateLoading(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Template temple = cfg.getTemplate(path + ".html");
			temple.setEncoding("UTF-8");
			TempList.put(path, temple);

			return temple;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void clearTL() {
		TempList.clear();
	}
}
