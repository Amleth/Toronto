package com.artisiou.toronto.parsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.artisiou.toronto.model.IEntitiesManager;
import com.artisiou.toronto.model.vo.Entity;
import com.artisiou.toronto.model.vo.Link;
import com.artisiou.toronto.model.vo.Metadata;

public class TorontoFilesParser extends SimpleFileVisitor<Path> {

	private Path root;
	private IEntitiesManager entitiesManager;

	public TorontoFilesParser(IEntitiesManager entitiesManager, Path root) {
		this.entitiesManager = entitiesManager;
		this.root = root;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		if (attrs.isRegularFile() && file.toString().endsWith(".tex"))
			this.parseContentFile(file);

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		System.err.println(exc);
		return FileVisitResult.CONTINUE;
	}

	public void parseContentFile(Path path) {
		try {

			BufferedReader br;
			br = new BufferedReader(new FileReader(path.toString()));

			try {

				Entity ent = new Entity(this.root, path);
				ArrayList<Metadata> lastEncounteredMetadata = new ArrayList<Metadata>();
				Boolean isCommentContent = false;
				StringBuilder mainContent = new StringBuilder();
				StringBuilder currentCommentContent = new StringBuilder();
				String line;

				while ((line = br.readLine()) != null) {

					// The patterns we are looking for
					Matcher id_matcher = Pattern.compile("\\s*%\\s*\\@\\s*(.*)").matcher(line);
					Matcher metadata_matcher = Pattern.compile("\\s*%\\s*\\$\\s*(\\S*)\\s*(.*)").matcher(line);
					Matcher link_matcher = Pattern.compile("\\s*%\\s*&\\s*(\\S*)\\s*(<|>)\\s*(.*)").matcher(line);
					Matcher open_comment_matcher = Pattern.compile("\\s*%\\s*\\(").matcher(line);
					Matcher close_comment_matcher = Pattern.compile("\\s*%\\s*\\)").matcher(line);

					if (id_matcher.find()) {
						ent.setId(id_matcher.group(1).trim());
					} else if (metadata_matcher.find()) {
						lastEncounteredMetadata.clear();
						Metadata md = new Metadata(metadata_matcher.group(1).trim()).setValue(metadata_matcher.group(2).trim());
						ent.addMetadata(md);
						lastEncounteredMetadata.add(md);
					} else if (link_matcher.find()) {
						lastEncounteredMetadata.clear();
						String referenced_entity = link_matcher.group(3).trim();
						if (referenced_entity.length() > 0) {
							Link l = new Link(link_matcher.group(1).trim(), ent, referenced_entity, link_matcher.group(2).trim().equals("<") ? true : false);
							ent.addLink(l);
							lastEncounteredMetadata.add(l);
						}
					} else if (open_comment_matcher.find()) {
						isCommentContent = true;
					} else if (close_comment_matcher.find()) {
						if (lastEncounteredMetadata.size() > 0)
							for (Metadata m : lastEncounteredMetadata)
								m.setComment(currentCommentContent.toString());
						currentCommentContent.setLength(0);
						isCommentContent = false;
					} else {
						if (isCommentContent)
							currentCommentContent.append(line + "\n");
						else
							mainContent.append(line + "\n");
					}
				}

				ent.setContent(mainContent.toString());
				ent.check();
				this.entitiesManager.getEntities().add(ent);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
