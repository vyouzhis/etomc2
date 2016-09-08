package org.ppl.esapi;

import java.net.URL;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncodingException;
import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;
import org.ppl.common.function;
import org.ppl.io.ProjectPath;

public class pEsapi extends function {

	public static final short ENC_BASE64 = 1;
	public static final short ENC_CSS = 2;
	public static final short ENC_DN = 3;
	public static final short ENC_HTML = 4;
	public static final short ENC_HTML_ATTR = 5;
	public static final short ENC_JAVA_SCRIPT = 6;
	public static final short ENC_LDAP = 7;
	public static final short ENC_OS = 8;
	public static final short ENC_SQl = 9;
	public static final short ENC_URL = 10;
	public static final short ENC_VB_SCRIPT = 11;
	public static final short ENC_XML = 12;
	public static final short ENC_XML_ATTR = 13;
	public static final short ENC_XPATH = 14;

	public String HtmlFilter(String html) {
		return ESAPI.encoder().encodeForHTML(html);
	}

	private void myesapi() {
		URL path = ProjectPath.class.getClassLoader().getResource(
				"./properties/esapi/");
		// echo("===="+ProjectPath.class.getClassLoader().getResource(".").getPath());

		System.setProperty("org.owasp.esapi.resources", path.getPath());

		DefaultSecurityConfiguration dc = new DefaultSecurityConfiguration();
		dc.setResourceDirectory(path.getPath());

		// ESAPI.securityConfiguration().setResourceDirectory();

		PlainText abc = new PlainText("abc");
		CipherText encrypted;
		try {
			encrypted = ESAPI.encryptor().encrypt(abc);
			echo("--------");
			echo(encrypted.getBase64EncodedRawCipherText());
			echo("===========");
			PlainText decrypted = ESAPI.encryptor().decrypt(encrypted);
			echo(decrypted.toString());
		} catch (EncryptionException e) {
			// TODO Auto-generated catch block
			echo(e.getMessage());
		}

	}

	public String encode(String item, short encFor) {

		String out = "";
		try {
			// System.setOut(new
			// PrintStream(DevNullOutputStream.DEV_NULL_OUTPUT_STREAM));
			Encoder encoder = ESAPI.encoder();

			switch (encFor) {
			// case ENC_CSS:return encoder.encodeForBase64(item);
			case ENC_CSS:
				out = encoder.encodeForCSS(item);
			case ENC_DN:
				out = encoder.encodeForDN(item);
			case ENC_HTML:
				out = encoder.encodeForHTML(item);

			case ENC_HTML_ATTR:
				out = encoder.encodeForHTMLAttribute(item);
			case ENC_JAVA_SCRIPT:
				out = encoder.encodeForJavaScript(item);
			case ENC_LDAP:
				out = encoder.encodeForLDAP(item);
				// case ENC_CSS:return encoder.encodeForOS(arg0, arg1)(item);
				// case ENC_CSS:return encoder.encodeForSQL(arg0,
				// arg1)CSS(item);
			case ENC_URL:
				out = encoder.encodeForURL(item);
			case ENC_VB_SCRIPT:
				out = encoder.encodeForVBScript(item);
			case ENC_XML:
				out = encoder.encodeForXML(item);
			case ENC_XML_ATTR:
				out = encoder.encodeForXMLAttribute(item);
			case ENC_XPATH:
				out = encoder.encodeForXPath(item);
			}

		} catch (EncodingException ee) {
			echo(ee.getMessage());
		} finally {

		}
		return out;
	}
}
