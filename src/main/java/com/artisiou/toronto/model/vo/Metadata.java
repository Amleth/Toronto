package com.artisiou.toronto.model.vo;

import java.math.BigInteger;

public class Metadata {

	public static final String VOID = "Void";

	private String name;

	public String getName() {
		return this.name;
	}

	private String value;

	public String getValue() {
		return this.value;
	}

	public Metadata setValue(String value) {
		if (this.name.equals("id"))
			value = value.replace(" ", "_");
		this.value = value;

		return this;
	}

	private String comment;

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Metadata(String name) {
		this.name = name.trim().toLowerCase();
		this.comment = "";
	}

	private BigInteger entity_sql_id;

	public BigInteger getEntitySqlId() {
		return this.entity_sql_id;
	}

	public void setEntitySqlId(BigInteger id) {
		this.entity_sql_id = id;
	}
}
