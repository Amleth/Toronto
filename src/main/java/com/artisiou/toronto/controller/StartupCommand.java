package com.artisiou.toronto.controller;

import java.nio.file.Paths;

import org.puremvc.java.multicore.interfaces.INotification;

import com.artisiou.puremvc.patterns.command.SimpleCommand;
import com.artisiou.toronto.ApplicationFacade;
import com.artisiou.toronto.model.EntitiesProxy;
import com.artisiou.toronto.parsing.TorontoFilesWalker;
import com.artisiou.toronto.serializers.JsonEntitiesSerializer;

public class StartupCommand extends SimpleCommand {

	public static final String PATH = "path";

	public StartupCommand() {
		super();
		this.setRequiredParams(new String[] { PATH });
	}

	@Override
	public final void execute(final INotification notification) {
		super.execute(notification);
		String root = (String) this.getParams().get(PATH);

		this.getFacade().removeCommand(ApplicationFacade.STARTUP);

		/* Entities manager */
		EntitiesProxy entitiesProxy = new EntitiesProxy();
		this.getFacade().registerProxy(entitiesProxy);

		/* Serializer(s) */
		JsonEntitiesSerializer entitiesJsonSerializer = new JsonEntitiesSerializer(entitiesProxy);

		/* Files manager */
		TorontoFilesWalker walker = new TorontoFilesWalker(Paths.get(root), entitiesProxy);

		// The App "life cycle"

		walker.walk();
		entitiesJsonSerializer.serializeEntities();
	}
}
