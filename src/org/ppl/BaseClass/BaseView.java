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
	protected Map<String, Object> root = null;

	public BaseView() {
		// TODO Auto-generated constructor stub

	}

	public void View() {

		if (root == null) {
			echo("class Name: "+stdClass + " root is null");
			return;
		}

		InitStatic();

		String[] libPaths = stdClass.split("\\.");
		String path = "";
		for (int i = 2; i < libPaths.length; i++) {
			path += "/" + libPaths[i];
		}
		// echo("html path:"+path);
		this.baseView(path);

	}

	public void baseView(String path) {

		if (root == null) {
			echo("root is null");
			return;
		}
		setRoot("StaticVer", globale_config.allStaticVer);
		
		InitStatic();

		FMConfig fmc = FMConfig.getInstance();

		Template temp;

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
		setRoot("static_ico", porg.getContext_Path()
				+ "/static/ico");
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

	public void addRoot(Map<String, Object> Root) {
		root = Root;
	}

	public int isLogin() {
		int uid = igetUid();
		if (uid > 0)
			return uid;
		return -1;
	}

	public int igetUid() {
		String uid = getUinfo("uid");
		if (uid == null)
			return 0;
		return toInt(uid);
	}

	public String igetName() {
		return getUinfo("nickname");
	}

	public String igetKey(String k) {
		return getUinfo(k);
	}

	private String getUinfo(String key) {

		Map<String, Object> res = UnPackUinfo();
		if (res == null)
			return null;
		if(!res.containsKey(key)) return null;
		
		return res.get(key).toString();
	}

	public void PackUinfo(Map<String, Object> res) {
		Encrypt ec = Encrypt.getInstance();
		String info_json = JSON.toJSONString(res);
		
		String hex = ec.toHex(info_json);

		cookieAct.SetCookie(globale_config.Uinfo, hex);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> UnPackUinfo() {
		String uinfo = cookieAct.GetCookie(globale_config.Uinfo);
		if (uinfo == null)
			return null;
		Encrypt en = Encrypt.getInstance();
		String hex = en.hexToString(uinfo);

		Map<String, Object> res = JSON.parseObject(hex, Map.class);
		return res;
	}

}
