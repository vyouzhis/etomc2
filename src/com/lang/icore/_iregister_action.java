package com.lang.icore;

import org.ppl.BaseClass.LibLang;

public class _iregister_action extends LibLang {
	public _iregister_action() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		SelfPath(this.getClass().getPackage().getName());
	}
}
