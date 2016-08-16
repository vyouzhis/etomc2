package org.ppl.BaseClass;

public abstract class BaseModule extends BaseView {

	public abstract void filter();
	
	public String getHtml() {
	
		return html;
	}
	@Override
	public void View() {
		// TODO Auto-generated method stub
		setRoot("BaseModule", "1");
		super.View();
	}
}
