package com.artisiou.toronto.model.vo;

import java.nio.file.Path;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

public class Entity {

	public static final ImmutableList<String> MAIN_METADATA = ImmutableList.of("TYPE", "ID", "NAME");

	private Path root;

	private Path uri;

	public Path getUri() {
		return this.root.relativize(uri);
	}

	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private ArrayList<Metadata> metadata;

	public ArrayList<Metadata> getMetadata() {
		return metadata;
	}

	public void addMetadata(Metadata s) {
		this.metadata.add(s);
	}

	private ArrayList<Link> links;

	public ArrayList<Link> getLinks() {
		return this.links;
	}

	public void addLink(Link l) {
		this.links.add(l);
	}

	public Entity(Path root, Path uri) {
		this.root = root;
		this.uri = uri;
		this.metadata = new ArrayList<Metadata>();
		this.links = new ArrayList<Link>();
	}

	public void check() {
		this.id = this.makeId();
	}

	private String makeId() {
		String id;

		if (this.id == null) {
			id = this.root.relativize(this.uri).toString();
			id = id.substring(0, id.length() - 4);
			id = id.replace(" ", "_");
			id = id.replace("/", "_");
		} else {
			id = this.id;
		}

		return id;
	}
}
