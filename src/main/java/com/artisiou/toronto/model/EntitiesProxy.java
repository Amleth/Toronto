package com.artisiou.toronto.model;

import java.util.ArrayList;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.artisiou.toronto.model.vo.Entity;

public class EntitiesProxy extends Proxy implements IEntitiesManager {

	public static final String NAME = "EntitiesProxy";

	private ArrayList<Entity> entities;

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public EntitiesProxy() {
		super(NAME);
		this.entities = new ArrayList<Entity>();
	}
}
