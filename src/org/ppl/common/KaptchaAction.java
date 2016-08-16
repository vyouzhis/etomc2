package org.ppl.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.ppl.core.PObject;
import org.ppl.etc.globale_config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class KaptchaAction extends PObject {
	private HttpServletResponse response;
	DefaultKaptcha producer;
	
	public KaptchaAction() {
		// TODO Auto-generated constructor stub
		producer = new DefaultKaptcha();
		Properties pp = new Properties();
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(globale_config.Kaptch);
		try {
			pp.load(inputStream);
			Config kConfig = new Config(pp);
			producer.setConfig(kConfig);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
				
	}
	
	public void getVerify() {
		response = porg.getHsr();
		
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		// return a jpeg
		response.setContentType("image/jpeg");

		// create the text for the image
		String capText = producer.createText();

		// store the text in the session

		SessAct.SetSession(globale_config.KaptchSes, capText);

		// create the image with the text
		BufferedImage bi = producer.createImage(capText);

		ServletOutputStream out;

		// write the data out
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
	}
}
