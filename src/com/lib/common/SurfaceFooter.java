package com.lib.common;

import java.io.File;

import org.ppl.BaseClass.BaseModule;
import org.ppl.io.ProjectPath;

public class SurfaceFooter extends BaseModule {
	private String className = null;

	public SurfaceFooter() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		// echo("current class:" + porg.getCurrentClass());

		ProjectPath pp = ProjectPath.getInstance();
		String path = pp.ViewDir().getPath() + "/common/"
				+ porg.getCurrentClass() + "_foot.html";

		File file = new File(path);
		if (file.exists()) {

			super.baseView("common/" + porg.getCurrentClass() + "_foot");

			String content = super.html;

			setRoot("ClazzFoot", content);
		}
		super.View();
	}

}
