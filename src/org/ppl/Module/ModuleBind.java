package org.ppl.Module;

import org.ppl.BaseClass.BaseCronThread;
import org.ppl.BaseClass.BaseModule;
import org.ppl.BaseClass.BasePrograma;
import org.ppl.BaseClass.BaseSurface;
import org.ppl.BaseClass.BaseRapidThread;
import org.ppl.BaseClass.BaseiCore;
import org.ppl.BaseClass.LibLang;
import org.ppl.BaseClass.Permission;
import org.ppl.core.PObject;
import org.ppl.etc.UrlClassList;
import org.ppl.plug.Quartz.CronQuartz;
import org.ppl.plug.Quartz.SimpleQuartz;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class ModuleBind extends PObject implements Module {

	private Binder binder;

	@SuppressWarnings("unchecked")
	@Override
	public void configure(Binder binder) {
		// TODO Auto-generated method stub
		this.binder = binder;
		
		UrlClassList ucl = UrlClassList.getInstance();
		
		for (String ps : ucl.getPackList()) {
			try {
				Class<?> clazz = Class.forName(ps);
								
				if (clazz.getSuperclass().equals(BaseSurface.class)) {
					Class<? extends BaseSurface> cls = (Class<? extends BaseSurface>) Class
							.forName(ps);
					Add(BaseSurface.class, cls);
				}else if (clazz.getSuperclass().equals(LibLang.class)) {
					Class<? extends LibLang> cls = (Class<? extends LibLang>) Class
							.forName(ps);
					Add(LibLang.class, cls);
				}else if (clazz.getSuperclass().equals(BaseiCore.class)) {
					Class<? extends BaseiCore> cls = (Class<? extends BaseiCore>) Class
							.forName(ps);
					Add(BaseiCore.class, cls);
				}else if (clazz.getSuperclass().equals(BaseModule.class)) {
					Class<? extends BaseModule> cls = (Class<? extends BaseModule>) Class
							.forName(ps);
					Add(BaseModule.class,cls);
				}else if (clazz.getSuperclass().equals(Permission.class)) {
					Class<? extends Permission> cls = (Class<? extends Permission>) Class
							.forName(ps);
					 Add(Permission.class, cls);
				}else if (clazz.getSuperclass().equals(BasePrograma.class)) {
					Class<? extends BasePrograma> cls = (Class<? extends BasePrograma>) Class
							.forName(ps);
					 Add(BasePrograma.class, cls);
				}				
				else if (clazz.getSuperclass().equals(BaseRapidThread.class)) {
					Class<? extends BaseRapidThread> cls = (Class<? extends BaseRapidThread>) Class
							.forName(ps);
					 Add(BaseRapidThread.class, cls);
			
				}else if (clazz.getSuperclass().equals(BaseCronThread.class)) {
					Class<? extends BaseCronThread> cls = (Class<? extends BaseCronThread>) Class
							.forName(ps);
					 Add(BaseCronThread.class, cls);
				}else if (clazz.getSuperclass().equals(CronQuartz.class)) {
					Class<? extends CronQuartz> cls = (Class<? extends CronQuartz>) Class
							.forName(ps);
					 Add(CronQuartz.class, cls);
				}else if (clazz.getSuperclass().equals(SimpleQuartz.class)) {
					Class<? extends SimpleQuartz> cls = (Class<? extends SimpleQuartz>) Class
							.forName(ps);
					 Add(SimpleQuartz.class, cls);
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public <T> void Add(@SuppressWarnings("rawtypes") Class mClass,
			Class<T> clazz) {
		String name = SliceName(clazz.getName());
		binder.bind(mClass).annotatedWith(Names.named(name)).to(clazz);
	}

}
