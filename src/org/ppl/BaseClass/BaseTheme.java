package org.ppl.BaseClass;

import org.ppl.common.ShowMessage;

import com.lib.common.SurfaceFooter;
import com.lib.common.SurfaceHeader;

public abstract class BaseTheme extends BaseView implements BaseThemeInterface {
	protected boolean isAutoHtml = true;
	private boolean ajax = false;
	protected String header_html = "";
	protected String footer_html = "";

	public abstract void Show();

	public void setHtml(String Con) {
		this.html = Con;
	}

	public String getHtml() {

		if (isAutoHtml) {
			common();
		}

		return header_html + html + footer_html;
	}

	public boolean isAjax() {

		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	private void common() {
		if (header_html.length() == 0) {
			SurfaceHeader header = new SurfaceHeader();
			header.filter();
			header_html = header.getHtml();

		}
		if (footer_html.length() == 0) {
			SurfaceFooter footer = new SurfaceFooter();
			footer.filter();
			footer_html = footer.getHtml();

		}
	}

	public void TipMessage(String url, String msg) {
		ShowMessage ms = ShowMessage.getInstance();
		this.html = ms.SetMsg(url, msg, 3000);
		isAutoHtml = false;
	}
}
