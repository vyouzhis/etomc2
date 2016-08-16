package org.ppl.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.ppl.common.function;

public class cUrl extends function {
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	private List<Header> cHeader = new ArrayList<>();
	private CloseableHttpClient httpclient = null;	
	ResponseHandler<String> responseHandler = null;
	RequestConfig requestConfig = null;

	public String httpGet(String url) {
		if (httpclient == null) {
			httpclient = HttpClients.createDefault();
			requestConfig = RequestConfig.custom()  
			        .setSocketTimeout(60000)  
			        .setConnectTimeout(60000)  
			        .build();
		}
		
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(requestConfig);
		
		if (cHeader.size() > 0) {
			for (Header mHeader : cHeader) {
				httpget.addHeader(mHeader);
			}
		}

		// Create a custom response handler
		Response();

		String responseBody = null;
		try {			 			
			responseBody = httpclient.execute(httpget, responseHandler);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpget = null;
		return responseBody;
	}

	private void Response() {
		if (responseHandler == null) {
			responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
		}
	}

	public String httpPost(String url) {
		if (httpclient == null) {
			httpclient = HttpClients.custom()
					.addInterceptorFirst(new HttpRequestInterceptor() {

						public void process(final HttpRequest request,
								final HttpContext context)
								throws HttpException, IOException {
							if (!request.containsHeader("Accept-Encoding")) {
								request.addHeader("Accept-Encoding", "gzip");
							}

						}
					}).addInterceptorFirst(new HttpResponseInterceptor() {

						public void process(final HttpResponse response,
								final HttpContext context)
								throws HttpException, IOException {
							HttpEntity entity = response.getEntity();
							if (entity != null) {

								Header ceheader = entity.getContentEncoding();
								if (ceheader != null) {
									HeaderElement[] codecs = ceheader
											.getElements();
									for (int i = 0; i < codecs.length; i++) {
										if (codecs[i].getName()
												.equalsIgnoreCase("gzip")) {
											response.setEntity(new GzipDecompressingEntity(
													response.getEntity()));
											return;
										}
									}
								}
							}
						}

					}).build();

		}
		HttpPost httppost = new HttpPost(url);
		if (cHeader.size() > 0) {
			for (Header mHeader : cHeader) {
				httppost.addHeader(mHeader);
			}
		}
		if (params.size() > 0) {
			try {
				httppost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CloseableHttpResponse response;
		// ByteArrayOutputStream bao = null;
		StringBuilder sb1 = new StringBuilder();
		InputStream bis = null;
		int len = 1024;
		byte[] buf = new byte[len];
		// byte[] sBuf;
		String content = null;
		try {

			response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				bis = response.getEntity().getContent();
				// long lenEntity = response.getEntity().getContentLength();
				Header encoding = response.getEntity().getContentEncoding();
				int count;

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((count = bis.read(buf)) != -1) {

					baos.write(buf, 0, count);

				}

				String str = new String(baos.toByteArray(), 0,
						baos.toByteArray().length, "UTF-8");
				baos.close();
				// echo("lenEntity:"+lenEntity+" baos:"+baos.toByteArray().length+" str:"+str.length());
				// echo(str);
				sb1.append(str);

				bis.close();

				ByteArrayInputStream bai = new ByteArrayInputStream(sb1
						.toString().getBytes());
				if (encoding != null) {
					if (encoding.getValue().equals("gzip")
							|| encoding.getValue().equals("zip")
							|| encoding.getValue().equals(
									"application/x-gzip-compressed")) {

						GZIPInputStream gzin = new GZIPInputStream(bai);
						StringBuffer sb = new StringBuffer();
						int size;
						while ((size = gzin.read(buf)) != -1) {
							sb.append(new String(buf, 0, size, "utf-8"));
						}
						gzin.close();
						// bao.close();

						content = sb.toString();

					}
				} else {

					content = new String(sb1);
				}

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
				}
			}
		}
		return content;

	}

	public void closePost() {
		try {
			httpclient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearParams() {
		params.clear();
	}

	public void addParams(String key, String val) {
		params.add(new BasicNameValuePair(key, val));
	}

	public List<Header> getcHeader() {
		return cHeader;
	}

	public void setcHeader(Header mHeader) {
		this.cHeader.add(mHeader);
	}

	public void clearHeader() {
		this.cHeader.clear();
	}
}
