package com.happycoding.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Provider {

	private String name;
	private Integer id;

	public Provider() {
	}

	public Provider(String name, Integer id) {
		this.name = name;
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Provider [name=" + name + ", id=" + id + "]";
	}

}
