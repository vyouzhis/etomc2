package org.ppl.BaseClass;

import java.io.File;

import org.ppl.io.ProjectPath;

public abstract class BaseModule extends BaseView {

	public abstract void filter();

	private String pclazz = null;

	public String getHtml() {

		return html;
	}

	@Override
	public void View() {
		// TODO Auto-generated method stub
		setRoot("BaseModule", "1");

		addExt();

		super.View();
	}

	public void addStdClazz(String clazz) {
		this.pclazz = clazz;
	}

	public String getPClazz() {
		return this.pclazz;
	}

	private void addExt() {
		ExtTmp("header", "ClazzHeader");
		ExtTmp("foot", "ClazzFoot");
	}

	private void ExtTmp(String ext, String RootName) {
		String clazz = SliceName(getPClazz());
		String pclazz = BaseSlice(getPClazz(), 2);
		ProjectPath pp = ProjectPath.getInstance();
		String path = pp.ViewDir().getPath() + "/common/" + pclazz + "/"
				+ clazz + "_" + ext + ".html";

		File file = new File(path);
		if (file.exists()) {

			super.baseView("common/" + pclazz + "/" + clazz + "_" + ext);

			String content = super.html;

			setRoot(RootName, content);
		}
	}
}
