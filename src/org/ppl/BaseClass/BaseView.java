package org.ppl.BaseClass;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.ppl.core.ACLControl;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.FMConfig;
import com.alibaba.fastjson.JSON;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BaseView extends ACLControl {
	protected String html = "";
	private Map<String, Object> root;

	public BaseView() {
		// TODO Auto-generated constructor stub

	}

	public void View() {

		if (root == null) {
			echo("root is null");
			return;
		}

		InitStatic();

		FMConfig fmc = FMConfig.getInstance();

		Template temp;
		String[] libPaths = stdClass.split("\\.");
		String path = "";
		for (int i = 2; i < libPaths.length; i++) {
			path += "/" + libPaths[i];
		}
		//echo("html path:"+path);
		temp = fmc.getTemp(path);
		if (temp != null) {
			StringWriter out = new StringWriter();
			
			try {
				temp.process(root, out);
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			html = out.toString();
		}

	}

	private void InitStatic() {
		setRoot("static_uri", porg.getContext_Path());
		setRoot("static_css_uri", porg.getContext_Path() + "/static/css");
		setRoot("static_js_uri", porg.getContext_Path() + "/static/js");
		setRoot("static_avatars_uri", porg.getContext_Path()
				+ "/static/ace/avatars");
		setRoot("data_uri",
				mConfig.GetValue("data.imghost") + porg.getContext_Path()
						+ "/Data/");

		setRoot("static_uri", porg.getContext_Path());
		setRoot("surface_plugins", porg.getContext_Path()
				+ "/static/surface/plugins");
		setRoot("surface_static_css_uri", porg.getContext_Path()
				+ "/static/surface/css");
		setRoot("surface_static_js_uri", porg.getContext_Path()
				+ "/static/surface/js");
		setRoot("surface_static_images_uri", porg.getContext_Path()
				+ "/static/images");
	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(String key, Object obj) {
		if (this.root == null) {
			root = new HashMap<String, Object>();
		}
		root.put(key, obj);
	}

	public int isLogin() {
		int uid = igetUid();
		if (uid > 0)
			return uid;
		return -1;
	}

	public int igetUid() {
		String uid = getUinfo("id");
		if (uid == null)
			return 0;
		return Integer.valueOf(uid);
	}

	public String igetName() {
		return getUinfo("alias");
	}

	@SuppressWarnings("unchecked")
	private String getUinfo(String key) {
		String uinfo = cookieAct.GetCookie(globale_config.Uinfo);
		if (uinfo == null)
			return null;
		Encrypt en = Encrypt.getInstance();
		String hex = en.hexToString(uinfo);

		Map<String, Object> res = JSON.parseObject(hex, Map.class);
		if (res == null)
			return null;
		return res.get(key).toString();
	}
}
