package com.artisiou.toronto.parsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.artisiou.toronto.model.FileNotExistsException;
import com.artisiou.toronto.model.IEntitiesManager;

public class TorontoFilesWalker {

	private Path root;

	private IEntitiesManager entitiesManager;

	public TorontoFilesWalker(Path root, IEntitiesManager entitiesManager) {
		this.root = root;
		this.entitiesManager = entitiesManager;
	}

	public void walk() {
		if (Files.exists(this.root)) {
			try {
				Files.walkFileTree(this.root.toRealPath(), new TorontoFilesParser(this.entitiesManager, root));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new FileNotExistsException();
		}
	}
}
