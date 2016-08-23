package org.ppl.BaseClass;

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
		super.View();
	}
	
	public void addStdClazz(String clazz) {
		this.pclazz = clazz;
	}
	
	public String getPClazz() {
		return this.pclazz;
	}
}
