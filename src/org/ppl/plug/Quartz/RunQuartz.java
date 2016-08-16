package org.ppl.plug.Quartz;

import java.util.List;

import org.ppl.common.function;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class RunQuartz extends function {
	private CronQuartz runquartz = null;

	@SuppressWarnings("unchecked")
	public void CronQuartz() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		List<String> groupList;
		try {
			// scheduler = StdSchedulerFactory.getDefaultScheduler();

			globale_config.scheduler = new StdSchedulerFactory(
					"properties/quartz.properties").getScheduler();

			groupList = globale_config.scheduler.getJobGroupNames();
			echo("+++++++++++++");
			// echo(scheduler.);
			echo("+++++++++++++");
			Injector injector = globale_config.injector;

			for (String ps : ucl.getPackList()) {
				try {
					Class<?> clazz = Class.forName(ps);

					if (clazz.getSuperclass().equals(CronQuartz.class)) {
						String name = SliceName(ps);
						// JobKey jobKey = new JobKey(name);
						echo("name====:" + name);
						runquartz = (CronQuartz) injector.getInstance(Key.get(
								CronQuartz.class, Names.named(name)));

						if (!groupList.contains("Group_" + name)) {

							JobDetail job = (JobDetail) JobBuilder
									.newJob((Class<? extends Job>) runquartz
											.getClass())
									.withIdentity(name, runquartz.getGroup())
									.build();

							Trigger trigger = newTrigger()
									.withIdentity(runquartz.getTrigger(),
											runquartz.getGroup())
									.withSchedule(
											cronSchedule(
													runquartz.cronSchedule())
													.withMisfireHandlingInstructionDoNothing())

									.forJob(name, runquartz.getGroup()).build();

							globale_config.scheduler.scheduleJob(job, trigger);

						} else {
							echo("job " + name + " is exits  !!");
						}

						if (runquartz.isRun() == 0) {
							delete(name, runquartz.getGroup());
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			globale_config.scheduler.start();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public static boolean isJobRunning(String jobName, String groupName)
	// throws SchedulerException {
	// List<JobExecutionContext> currentJobs =
	// scheduler.getCurrentlyExecutingJobs();
	//
	// // for (JobExecutionContext jobCtx : currentJobs) {
	// // String thisJobName = jobCtx.getJobDetail().getKey().getName();
	// // String thisGroupName = jobCtx.getJobDetail().getKey().getGroup();
	// // if (jobName.equalsIgnoreCase(thisJobName) &&
	// groupName.equalsIgnoreCase(thisGroupName)
	// // && !jobCtx.getFireTime().equals(scheduler.getFireTime())) {
	// // return true;
	// // }
	// // }
	// return false;
	// }

	@SuppressWarnings("unchecked")
	public void SimpleQuartz(String ThreadName, Object message) {
		SimpleQuartz Simquartz = null;
		Injector injector = globale_config.injector;
		Simquartz = (SimpleQuartz) injector.getInstance(Key.get(
				SimpleQuartz.class, Names.named(ThreadName)));

		JobDetail job = (JobDetail) JobBuilder
				.newJob((Class<? extends Job>) Simquartz.getClass())
				.withIdentity(ThreadName + time(), Simquartz.getGroup())

				.build();

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(Simquartz.getTrigger(), Simquartz.getGroup())
				.startNow()
				.withSchedule(
						simpleSchedule().withRepeatCount(
								Simquartz.withRepeatCount())
								.withIntervalInSeconds(
										Simquartz.withIntervalInSeconds()))
				.build();

		try {
			globale_config.scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean isJobPaused(String jobName) throws SchedulerException {

		JobKey jobKey = new JobKey(jobName);
		JobDetail jobDetail = globale_config.scheduler.getJobDetail(jobKey);
		List<? extends Trigger> triggers = globale_config.scheduler
				.getTriggersOfJob(jobDetail.getKey());
		for (Trigger trigger : triggers) {
			TriggerState triggerState = globale_config.scheduler
					.getTriggerState(trigger.getKey());
			if (TriggerState.PAUSED.equals(triggerState)) {
				return true;
			}
		}
		return false;
	}

	public void delete(String jobName, String groupName) {
		try {
			globale_config.scheduler.deleteJob(JobKey
					.jobKey(jobName, groupName));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
