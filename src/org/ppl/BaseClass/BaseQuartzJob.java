package org.ppl.BaseClass;

public abstract class BaseQuartzJob {
	public BaseQuartzJob() {
		// TODO Auto-generated constructor stub
		
	}
	public abstract String title();
	public abstract void Run();
	public abstract boolean isRun();
	public abstract boolean Stop();
	public abstract void mailbox(Object o);
}
