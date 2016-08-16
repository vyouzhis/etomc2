package org.ppl.core;

import org.ppl.etc.Config;
import org.ppl.etc.globale_config;

public class InitCore {
	protected String stdClass = null;
	protected static final Config UserCoreConfig = new Config(
			globale_config.Mysql);
	protected static final Config myConfig = new Config(globale_config.DBCONFIG);
	protected static final Config mConfig = new Config(globale_config.Config);
	protected static final Config uConfig = new Config(globale_config.UrlMap);
	protected static final Config mgConfig = new Config(globale_config.Mongo);
	protected static final Config mailConfig = new Config(globale_config.Mail);
	
	protected static String DB_NAME = mConfig.GetValue("db.name");
	protected static String DB_PRE = mConfig.GetValue("db.rule.ext");
	protected static String DB_HOR_PRE = mConfig.GetValue("db.hor.ext");
	protected static String DB_WEB_PRE = mConfig.GetValue("db.web.ext");
}
