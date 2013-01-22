package com.artisiou.toronto.model.vo;

public class Link extends Metadata {

	private Boolean direction;

	public Boolean getDirection() {
		return this.direction;
	}

	private String comment;

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	private String referencedEntity;

	public String getReferencedEntity() {
		return this.referencedEntity;
	}

	private Entity contextEntity;

	public Entity getContextEntity() {
		return this.contextEntity;
	}

	public Link(String name, Entity contextEntity, String referencedEntity, Boolean direction) {
		super(name);
		this.contextEntity = contextEntity;
		this.referencedEntity = referencedEntity;
		this.direction = direction;
	}
}
