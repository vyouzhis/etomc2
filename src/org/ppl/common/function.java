package org.ppl.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ppl.core.InitCore;
import org.ppl.etc.UrlClassList;
import org.ppl.io.TimeClass;

public class function extends InitCore {

	public void echo(Object o) {
		if (stdClass != null) {
			Logger log = Logger.getLogger(stdClass);
			log.info(o.toString());
		} else {
			// System.out.println("stdClass:"+stdClass);
			System.out.println(o);
		}
	}

	public void echo(Object o, String file, int line) {
		if (stdClass != null) {
			Logger log = Logger.getLogger(stdClass);
			log.info(o.toString() + "file:" + file + ":" + line);
		} else {
			// System.out.println("stdClass:"+stdClass);
			System.out.println(o + "file:" + file + ":" + line);
		}
	}

	public static String _FILE_() {
		StackTraceElement stackTraces[] = (new Throwable()).getStackTrace();
		return stackTraces[1].getFileName();
	}

	public static int _LINE_() {
		StackTraceElement stackTraces[] = (new Throwable()).getStackTrace();
		return stackTraces[1].getLineNumber();
	}

	public String SliceName(String k) {
		String[] name = k.split("\\.");
		String cName = name[name.length - 1];
		return cName;
	}

	public List<String> PermFileList(String directoryName) {
		List<String> fl = new ArrayList<String>();
		File directory = new File(directoryName);
		Map<String, List<String>> PackClassList;
		UrlClassList ucl = UrlClassList.getInstance();
		PackClassList = ucl.getPackClassList();
		if (PackClassList == null) {
			PackClassList = new HashMap<String, List<String>>();
		}
		// get all the files from a directory
		File[] fList = directory.listFiles();
		if (fList == null || fList.length == 0)
			return fl;
		for (File file : fList) {
			if (file.isFile()) {
				// echo("name:"+directory.getName()+"__"+file.getName());
				String lib = file.getName().split("\\.")[0];
				String index = directory.getName();
				if (!index.equals("manager") && !lib.matches("(.*)_index")) {

					if (PackClassList.get(index) != null) {
						if (!PackClassList.get(index).contains(lib))
							PackClassList.get(index).add(lib);
					} else {
						List<String> l = new ArrayList<String>();
						l.add(lib);
						PackClassList.put(index, l);
					}
				}
				fl.add("Permission." + lib);
			} else if (file.isDirectory()) {
				fl.addAll(PermFileList(file.getAbsolutePath()));
			}
		}
		ucl.setPackClassList(PackClassList);

		return fl;
	}

	public List<String> PermUrlMap() {

		String path = this.getClass().getResource("/").getPath()
				+ mConfig.GetValue("perm_class_path");

		List<String> pum = PermFileList(path);

		return pum;
	}

	public void findPack(String path) {
		UrlClassList ucl = UrlClassList.getInstance();
		File directory = new File(path);
		File[] fList = directory.listFiles();
		String[] pack = path.split("classes");
		if (pack.length != 2)
			return;
		String pn = pack[1].replace("/", ".");
		pn = pn.replace("\\", ".");

		if (!pn.substring(pn.length() - 1, pn.length()).equals(".")) {
			pn = pn + ".";
		}
		pn = pn.substring(1);

		for (File file : fList) {
			if (file.isFile()) {
				String lib = file.getName().split("\\.")[0];
				ucl.setPackList(pn + lib);
			} else if (file.isDirectory()) {
				findPack(file.getAbsolutePath());
			}
		}
	}

	public int time() {
		TimeClass tc = TimeClass.getInstance();
		return (int) tc.time();
	}

	//yyyy-MM-dd HH:mm:ss
	public String DateFormat(Long TimeStamp, String format) {
		TimeClass tc = TimeClass.getInstance();
		return tc.TimeStamptoDate(TimeStamp, format);
	}

	public boolean validateEmailAddress(String emailAddress) {
		Pattern regexPattern;
		Matcher regMatcher;
		regexPattern = Pattern
				.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		regMatcher = regexPattern.matcher(emailAddress);
		if (regMatcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public int toInt(Object o) {
		if (o != null && o.toString().matches("[0-9-]+")) {
			return Integer.valueOf(o.toString());
		} else {
			return 0;
		}
	}

	public float toFloat(Object o) {
		if (o != null && o.toString().matches("[0-9.-]+")) {
			try {
				return Float.valueOf(o.toString());
			} catch (NumberFormatException e) {
				// TODO: handle exception
				return 0;
			}

		} else {
			return 0;
		}
	}

	public Double toDouble(Object o) {
		if (o != null && o.toString().matches("[0-9.-]+")) {
			try {
				return Double.valueOf(o.toString());
			} catch (NumberFormatException e) {
				// TODO: handle exception
				return 0.0;
			}

		} else {
			return 0.0;
		}
	}

	public String escapeHtml(String old) {
		if (old == null)
			return "";

		String news = old.replace("&nbsp;", "");
		news = news.replace("&quot;", "\"");
		news = news.replace("&apos;", "\'");
		news = news.replace(";", "");
		news = news.replace("'", "\'");
		// news = news.replace("%", "%%");
		news = news.replace("\r", " ");
		news = news.replace("\t", " ");
		news = news.replace("\n", " ");

		return news;
	}

	public String unescapeHtml(String old) {
		if (old == null)
			return "";

		String news = old.replace("\"", "&quot;");
		news = news.replace("\'", "&apos;");
		news = news.replace("\r", " ");
		news = news.replace("\t", " ");
		news = news.replace("\n", " ");
		// news = news.replace("%", "%%");
		return news;

	}

	public Map<String, Object> SetTree(List<Map<String, Object>> res, int id,
			String name) {
		Map<String, Map<String, String>> file = new HashMap<>();
		Map<String, String> Item = null;
		String unit="";
		if (res != null) {
			for (Map<String, Object> map : res) {
				Item = new HashMap<>();
				Item.put("type", "item");

				Item.put("id", map.get("id").toString());
				Item.put("name", map.get("name").toString());
				if (map.containsKey("qaction")) {
					Item.put("qaction", map.get("qaction").toString());
				} else if (map.containsKey("sql_type")) {
					Item.put("qaction", "4");
				} else {
					Item.put("qaction", "5");
				}

				if (map.containsKey("sql_type")) {
					Item.put("sql_type", map.get("sql_type").toString());
				}
				if (map.containsKey("sqltmp")) {
					Item.put("sqltmp", map.get("sqltmp").toString());
				}
				if(map.containsKey("units")){
					unit = " ("+map.get("units").toString()+")";
				}
				Item.put("unit", unit);
				file.put(map.get("id").toString(), Item);
			}
		}
		Map<String, Object> Trees;
		Trees = new HashMap<String, Object>();

		Trees.put("children", file);
		Trees.put("name", name);
		Trees.put("id", id);
		return Trees;
	}

}
