package com.happycoding.model;

public class Provider {

	private String name;
	private Integer id;
	private String content;

	public Provider() {
	}

	public Provider(String name, Integer id, String content) {
		this.name = name;
		this.setId(id);
		this.content = content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Provider [name=" + name + ", id=" + id + ", content=" + content + "]";
	}

}
