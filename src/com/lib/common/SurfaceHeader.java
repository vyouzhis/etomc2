package com.lib.common;

import java.io.File;

import org.ppl.BaseClass.BaseModule;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.ProjectPath;

public class SurfaceHeader extends BaseModule {
	private String className = null;

	public SurfaceHeader() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub

		UrlClassList ucl = UrlClassList.getInstance();

		setRoot("index", ucl.Url(""));
		setRoot("register", ucl.Url("register"));
		setRoot("login", ucl.Url("login"));
		setRoot("icore", ucl.Url("icore"));

		setRoot("trade", ucl.Url("trade"));
		setRoot("risk", ucl.Url("risk"));
		setRoot("arbitrage", ucl.Url("arbitrage"));
		setRoot("strategy", ucl.Url("strategy"));

		String clazz = SliceName(getPClazz());
		String pclazz = BaseSlice(getPClazz(), 2);

		ProjectPath pp = ProjectPath.getInstance();
		String path = pp.ViewDir().getPath() + "/common/" + pclazz + "/"
				+ clazz + "_header.html";

		File file = new File(path);
		if (file.exists()) {

			super.baseView("common/" + pclazz + "/" + clazz + "_header");

			String content = super.html;

			setRoot("ClazzHeader", content);
		}

		super.View();
	}

}
