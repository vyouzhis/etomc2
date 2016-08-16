package org.ppl.common;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class PorG  extends function {
	static PorG source;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, String> sporg;
	private String Context_Path;
	private String mehtod = null;
	private List<String> rmc;
	private Map<String, String> upload_name=null;
	private Map<String, byte[]> upload_string=null;
	
	private static final String[] HEADERS_TO_TRY = { 
	    "X-Forwarded-For",
	    "Proxy-Client-IP",
	    "WL-Proxy-Client-IP",
	    "HTTP_X_FORWARDED_FOR",
	    "HTTP_X_FORWARDED",
	    "HTTP_X_CLUSTER_CLIENT_IP",
	    "HTTP_CLIENT_IP",
	    "HTTP_FORWARDED_FOR",
	    "HTTP_FORWARDED",
	    "HTTP_VIA",
	    "REMOTE_ADDR" };

	
	public static PorG getInstance() {
		if (source == null) {
			source = new PorG();
		}

		return source;
	}

	public void Init(HttpServletRequest req, HttpServletResponse res) {
		request = req;
		response = res;
		sporg = new HashMap<String, String>();
		ParserParame();
	}
	
	public HttpServletResponse getHsr() {
		return response;
	}

	public String getKey(String key) {
		if (sporg.containsKey(key)) {
			return sporg.get(key);
		}
		return null;
	}

	@SuppressWarnings({ "unused" })
	private void ParserParame() {
		
		long maxFileSize = Integer.valueOf(mConfig.GetInt("maxFileSize"));
		
		int maxMemSize = mConfig.GetInt("maxMemSize");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemSize);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		upload.setSizeMax(maxFileSize);
		String contentType = request.getContentType();

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {

			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			
			String pres = Check(paramValues[0]);
			
			try {
				pres = new String(pres.getBytes("iso8859-1"),
						"UTF-8");				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sporg.put(paramName, pres);
		}
		if (contentType != null
				&& (contentType.indexOf("multipart/form-data") != -1)) {
			final String encoding = "UTF-8";
			try {
				ServletRequestContext context = new ServletRequestContext(request) ;
				
				List<FileItem> items = upload.parseRequest(context);
				Iterator<FileItem> iter = items.iterator();

				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (item.isFormField()) {
						// processFormField(item);

						String name = item.getFieldName();
						String value = item.getString();

						try {
							value = new String(value.getBytes("iso8859-1"),
									"UTF-8");

						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//System.out.println("name:"+name+"  value:"+value);
												
						sporg.put(name, Check(value));
					} else {

						String fieldName = item.getFieldName();
						String fileName = item.getName();
				
						if(upload_name==null){
							upload_name = new HashMap<>();
						}
						upload_name.put(fieldName, fileName);				
						if(upload_string==null){
							upload_string = new HashMap<>();
						}
						upload_string.put(fieldName, item.get());
						
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
		}

	}

	private String Check(String value) {
		//value = value.replace("'", "&apos;");
		//value = value.replaceAll("&", "&amp;");
		//value = value.replace("\"", "&quot;");
		//value = value.replace("\t", "&nbsp;&nbsp;");
		//value = value.replace(" ", "&nbsp;");
		//value = value.replace("<", "&lt;");
		//value = value.replaceAll(">", "&gt;");
		return value.trim();
	}

	public String getContext_Path() {
		return Context_Path;
	}

	public void setContext_Path(String context_Path) {
		Context_Path = context_Path;
	}
	
	public void UrlServlet(List<String> arg){
		this.rmc = arg;
	}
	
	public List<String> getRmc() {
		return rmc;
	}
	
	public Map<String, String> getAllpg() {
		return sporg;
	}
	
	public String GetIP() {
		String ip = "";
		//String stdClass="PorG";
		
		//Logger log = Logger.getLogger(stdClass);

		for (String header : HEADERS_TO_TRY) {
	        ip = request.getHeader(header);	 
	        //log.info(header+" : "+ip);
	    }
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
	    return request.getRemoteAddr();
	}

	
	public String getMehtod() {
		return mehtod;
	}

	public void setMehtod(String mehtod) {
		this.mehtod = mehtod;
	}

	public Map<String, String> getUpload_name() {
		return upload_name;
	}

	public Map<String, byte[]> getUpload_string() {
		return upload_string;
	}

}
