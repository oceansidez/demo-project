package com.telecom.base;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;

public abstract class BaseSchedulTask implements Runnable {

	public abstract void exec();
	public abstract Trigger getCustomTrigger();

	public void run() {
		exec();
	}

	public Trigger getTrigger() {
		return new Trigger() {
			public Date nextExecutionTime(TriggerContext triggerContext) {
				Date nextExec = getCustomTrigger().nextExecutionTime(triggerContext);
				return nextExec;
			}
		};
	}
}
