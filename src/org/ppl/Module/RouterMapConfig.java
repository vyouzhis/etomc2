package org.ppl.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.BaseClass.BaseiCore;
import org.ppl.BaseClass.Permission;
import org.ppl.core.PObject;
import org.ppl.db.HikariConnectionPool;

import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class RouterMapConfig extends PObject {
	List<String> RMC = null;
	private String servletPath = null;
	private String mehtod = null;
	private boolean NullMap = true;
	private String htmlCon;
	private boolean isAjax = false;
	private UrlClassList ucl;
	private String BaseName = null;
	private List<String> pum = null;

	public RouterMapConfig() {
		// TODO Auto-generated constructor stub		
		RMC = new ArrayList<String>();
		pum = PermUrlMap();
		
		ParserMap();
	}
	
	public void map(String key) {
		servletPath = key.trim();
	}

	public void match() {
		String[] BaseClass;		
		if (matchRouter()) {
			if (BaseName != null && RMC.size() > 0) {
				porg.UrlServlet(RMC);
				
				Injector injector = globale_config.injector;

				BaseClass = BaseName.split("\\.");
				if (BaseClass.length != 2){					
					return;
				}
				HikariConnectionPool hcp = HikariConnectionPool.getInstance();
				hcp.GetCon();
				switch (BaseClass[0]) {
				case "Permission":
					Permission home = (Permission) injector.getInstance(Key
							.get(Permission.class, Names.named(BaseClass[1])));
					
					home.Show();
					htmlCon = home.getHtml();
					// System.out.println(htmlCon);
					isAjax = home.isAjax();
					
					break;
				case "BaseSurface":
					BaseSurface Shome = (BaseSurface) injector.getInstance(Key
							.get(BaseSurface.class, Names.named(BaseClass[1])));
					
					Shome.Show();
					htmlCon = Shome.getHtml();
					isAjax = Shome.isAjax();
					
					break;
				case "BaseiCore":
					BaseiCore ihome = (BaseiCore) injector.getInstance(Key.get(
							BaseiCore.class, Names.named(BaseClass[1])));
					
					ihome.Show();
					htmlCon = ihome.getHtml();
					isAjax = ihome.isAjax();
					
					break;
				default:
					hcp.free();
					return;
				}
				hcp.free();
				
				NullMap = false;
			}
						
		}
		
	}

	public boolean routing() {
		return NullMap;
	}

	public String getHtml() {
		return htmlCon;
	}

	public String setContentType() {
		if (isAjax) {
			return "application/json";
		} else {
			return "text/html";
		}
	}

	private boolean matchRouter() {

		if (servletPath.length() > 0 && servletPath.substring(0, 1).equals("/")) {
			servletPath = servletPath.substring(1);
		}
		// System.out.println("servletpath: "+servletPath);
		String[] UrlServlet = servletPath.split("/");
		String uri;
		String mMthod = null;

		List<String> lu = ucl.getUcls();

		// System.out.println("getUcls: " + lu.size());
		for (int i = 0; i < lu.size(); i++) {
			RMC.clear();
			BaseName = lu.get(i).toString();

			mMthod = GetMapMethod(BaseName);
			if (mMthod == null)
				continue;

			int l = mMthod.toLowerCase().indexOf(mehtod.toLowerCase());
			if (l == -1) {
				BaseName = null;
				// System.out.println("method is not");
				continue;
			}

			uri = GetMapUri(BaseName);
			// System.out.println(uri);
			if (uri.length() > 0 && uri.substring(0, 1).equals("/")) {
				uri = uri.substring(1);
			}

			String[] uris = uri.split("/");

			if (uri.length() == 0 && servletPath.length() == 0) {
				// System.out.println("uri equals index " + uri);
				RMC.add("");
				return true;
			} else if (uri.length() == 0 && servletPath.length() != 0) {
				continue;
			}

			if (uri.length() > 1 && uri.substring(uri.length() - 1).equals("?")) {
				if (uris.length < UrlServlet.length) {
					BaseName = null;
					// System.out.println("UrlServlet.length < "
					// + UrlServlet.length);
					continue;
				}
			} else {

				if (uris.length != UrlServlet.length) {
					BaseName = null;
					// System.out.println("UrlServlet.length != "
					// + UrlServlet.length);
					continue;
				}
			}

			for (int j = 0; j < UrlServlet.length; j++) {
				// System.out.println("uri: "+uris[j]+" us:"+UrlServlet[j]);
				Pattern r = Pattern.compile(uris[j]);
				if (uris[j].length() > 11
						&& uris[j].substring(0, 11).equals("Permission.")) {
					r = Pattern.compile(uris[j].substring(11));
				}
				//
				Matcher m = r.matcher(UrlServlet[j]);
				if (m.find()) {
					if (m.group().equals(UrlServlet[j])) {
						RMC.add(m.group());
						// System.out.println("Found value: " + BaseName +
						// " arg:"
						// + m.group());
					} else {
						RMC.clear();
						break;
					}
				} else {
					RMC.clear();
					break;
				}
			}

			if (RMC.size() == UrlServlet.length)
				return true;

			BaseName = null;
		}

		return false;
	}

	public String getMehtod() {
		return mehtod;
	}

	public void setMehtod(String mehtod) {
		this.mehtod = mehtod;
	}

	private void ParserMap() {
		String uri = null;
		
		ucl = UrlClassList.getInstance();
		//echo("uConfig size:"+uConfig.getKey().size());
		if (ucl.getUcls() == null) {
			for (Object um : uConfig.getKey()) {
				uri = um.toString();
				if (uri.substring(uri.length() - 4).equals(".uri")) {
					ucl.setUcls(uri.substring(0, uri.length() - 4));
				}

			}
		}

		for (int i = 0; i < pum.size(); i++) {
			ucl.setUcls(pum.get(i));
		}

	}

	private String GetMapMethod(String key) {
		
		String method = uConfig.GetValue(key + ".method");
		if (method == null) {
			if (pum.contains(key)) {
				return "POST|GET";
			}
		}

		return method;
	}

	private String GetMapUri(String key) {
		
		String uri = uConfig.GetValue(key + ".uri");
		if (uri == null) {
			if (pum.contains(key)) {
				return key + "/(read|create|edit|remove|search)";
			}
		}

		return uri;
	}}
