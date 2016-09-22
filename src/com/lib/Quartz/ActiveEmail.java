package com.lib.Quartz;

import java.util.Map;

import org.ppl.net.MailSender;
import org.ppl.plug.Quartz.SimpleQuartz;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author vyouzhi
 * 
 * @see 用作注册激活发送邮件
 */

public class ActiveEmail extends SimpleQuartz implements Job{
	private Map<String, Object> Msg=null;
	public ActiveEmail() {
		// TODO Auto-generated constructor stub
        String className = null;
        className = this.getClass().getCanonicalName();
        super.GetSubClassName(className);
	}
	
	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return "Group_"+SliceName(stdClass);
	}

	@Override
	public int withRepeatCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int withIntervalInSeconds() {
		// TODO Auto-generated method stub
		return 10;
	}
	
	private void SendEmail() {				
		if(Msg == null){
			echo("msg is null");
			return;
		}
			
		MailSender mSender = new MailSender();
		mSender.setSubject(Msg.get("subject").toString());
		mSender.setText(Msg.get("text").toString());
		mSender.To(Msg.get("email").toString());		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap map = context.getJobDetail().getJobDataMap();
		String json = map.getString(getArg());
		if(json==null){
			echo("json is null");
			return ;
		}
		Msg = JSON.parseObject(json, Map.class);
				
		SendEmail();
	}

}
