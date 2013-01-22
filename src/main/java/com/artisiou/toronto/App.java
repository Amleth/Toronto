package com.artisiou.toronto;

import java.util.HashMap;

import com.artisiou.toronto.controller.StartupCommand;

public class App {
	public static void main(String[] args) {
		ApplicationFacade applicationFacade = ApplicationFacade.getInstance();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(StartupCommand.PATH, args[0]);
		applicationFacade.startup(params);
	}
}
