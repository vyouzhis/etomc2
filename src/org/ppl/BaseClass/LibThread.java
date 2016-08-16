package org.ppl.BaseClass;

import org.ppl.common.function;

public abstract class LibThread extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
	
	public abstract void ListQueue();
	
	public String SliceName(String k) {
		function fun = new function();
		return fun.SliceName(k);
	}
}
