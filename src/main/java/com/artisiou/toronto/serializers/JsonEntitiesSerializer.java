package com.artisiou.toronto.serializers;

import java.util.ArrayList;
import java.util.HashMap;

import com.artisiou.toronto.model.IEntitiesManager;
import com.artisiou.toronto.model.vo.Entity;
import com.artisiou.toronto.model.vo.Link;
import com.artisiou.toronto.model.vo.Metadata;
import com.google.gson.Gson;

public class JsonEntitiesSerializer {

	private IEntitiesManager entitiesManager;

	public void setEntitiesManager(IEntitiesManager entitiesManager) {
		this.entitiesManager = entitiesManager;
	}

	public JsonEntitiesSerializer(IEntitiesManager entitiesManager) {
		this.setEntitiesManager(entitiesManager);
	}

	public Object serializeEntities() {

		HashMap<String, Object> data = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> links = new ArrayList<HashMap<String, String>>();
		data.put("links", links);
		ArrayList<HashMap<String, String>> entities = new ArrayList<HashMap<String, String>>();
		data.put("entities", entities);

		Gson gson = new Gson();

		for (Entity e : this.entitiesManager.getEntities()) {

			HashMap<String, String> entity = new HashMap<String, String>();
			entity.put("id", e.getId());
			entity.put("uri", e.getUri().toString());
			for (Metadata m : e.getMetadata()) {
				if (!(m instanceof Link)) {
					entity.put(m.getName(), m.getValue());
				}
			}
			entity.put("content", e.getContent());
			entities.add(entity);

			for (Link l : e.getLinks()) {
				HashMap<String, String> link = new HashMap<String, String>();

				link.put("name", l.getName());
				String from;
				String to;
				if (l.getDirection() == false) {
					from = l.getContextEntity().getId();
					to = l.getReferencedEntity();
				} else {
					from = l.getReferencedEntity();
					to = l.getContextEntity().getId();
				}
				link.put("from", from);
				link.put("to", to);
				link.put("comment", l.getComment());

				links.add(link);
			}
		}

		System.out.println(gson.toJson(data));

		return null;
	}
}
