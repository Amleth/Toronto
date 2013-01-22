package com.artisiou.puremvc.patterns.command;

import java.util.HashMap;

import org.puremvc.java.multicore.interfaces.INotification;

public class SimpleCommand extends org.puremvc.java.multicore.patterns.command.SimpleCommand {

	private String[] requiredParams = {};

	private HashMap<String, ?> params;

	public void setRequiredParams(String[] requiredParams) {
		this.requiredParams = requiredParams;
	}

	public HashMap<String, ?> getParams() {
		return params;
	}

	@SuppressWarnings("unchecked")
	public void execute(INotification notification) throws NotificationBodyIsNotHashMapException {
		this.checkParamsKeys((HashMap<String, ?>) notification.getBody());

		super.execute(notification);
	}

	private void checkParamsKeys(HashMap<String, ?> params) {
		for (String _ : this.requiredParams) {
			if (!params.containsKey(_)) {
				throw new NotificationParamNotFoundException();
			}
		}

		this.params = params;
	}

	private class NotificationBodyIsNotHashMapException extends RuntimeException {
		private static final long serialVersionUID = 778526772221107320L;
	}

	private class NotificationParamNotFoundException extends RuntimeException {
		private static final long serialVersionUID = -4136282100143187947L;
	}
}
