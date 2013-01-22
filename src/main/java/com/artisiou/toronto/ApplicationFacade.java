package com.artisiou.toronto;

import java.util.HashMap;

import org.puremvc.java.multicore.patterns.facade.Facade;

import com.artisiou.toronto.controller.StartupCommand;

public class ApplicationFacade extends Facade {

	public static final String MULTITONKEY = "toronto";

	protected ApplicationFacade(String key) {
		super(key);
	}

	public static final String STARTUP = "startup";

	// Startup command notifications
	public static final String PREP_MODEL = "prepModel";
	public static final String PREP_VIEW = "prepView";

	/**
	 * Unique instance
	 */
	private static ApplicationFacade instance = null;

	/**
	 * Get the instance.
	 * 
	 * @return the singleton
	 */
	public static ApplicationFacade getInstance() {
		if (instance == null) {
			instance = new ApplicationFacade(MULTITONKEY);
		}
		return instance;
	}

	/**
	 * Start the application.
	 */
	public void startup(HashMap<String, String> params) {
		this.sendNotification(STARTUP, params);
	}

	/**
	 * Initialize controller. Register the commands.
	 */
	@Override
	protected final void initializeController() {
		super.initializeController();
		registerCommand(STARTUP, new StartupCommand());
	}
}
