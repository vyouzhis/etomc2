package org.ppl.core;

import java.util.HashMap;
import java.util.Map;

import org.ppl.common.CookieAction;
import org.ppl.common.PorG;
import org.ppl.common.SessionAction;
import org.ppl.common.function;
import org.ppl.etc.Config;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;
import org.ppl.plug.Quartz.RunQuartz;

public class PObject extends function{
	protected String stdClass = null;
	private String BindName = null;

	protected SessionAction SessAct = SessionAction.getInstance();
	protected CookieAction cookieAct = CookieAction.getInstance();
	protected PorG porg = PorG.getInstance();

	protected void GetSubClassName(String subClassname) {
		stdClass = subClassname;
	}
	public String getBindName() {
		if (BindName == null) {
			BindName = stdClass;

		}
		return BindName;
	}

	public void setBindName(String bindName) {
		BindName = bindName;
	}

	public String getSalt() {
		TimeClass tc = TimeClass.getInstance();
		Encrypt ec = Encrypt.getInstance();

		String salt = ec.MD5(String.valueOf(tc.time()));
		Config mConfig = new Config(globale_config.Config);

		SessAct.SetSession(mConfig.GetValue(globale_config.SessSalt), salt);

		return salt;
	}

	public boolean checkSalt(String salt) {

		Encrypt ec = Encrypt.getInstance();
		String new_salt = ec.MD5(String.valueOf(time()));

		String sess_salt = SessAct.GetSession(mConfig
				.GetValue(globale_config.SessSalt));

		if (sess_salt == null)
			return false;
		if (sess_salt.equals(salt)) {
			SessAct.SetSession(mConfig.GetValue(globale_config.SessSalt),
					new_salt);
			return true;
		}

		return false;
	}

	public void TellPostMan(String ThreadName, Object message) {
//		Map<String, Object> tpm = new HashMap<>();
//		tpm.put("name", ThreadName);
//		tpm.put("obj", message);
		
//		synchronized (globale_config.RapidListQueue) {
//			globale_config.RapidListQueue.add(tpm);
//			globale_config.RapidListQueue.notify();
//		}
		RunQuartz rq = new RunQuartz();
		rq.SimpleQuartz(ThreadName, message);
	}

	public long myThreadId() {
		return Thread.currentThread().getId();
	}
}
