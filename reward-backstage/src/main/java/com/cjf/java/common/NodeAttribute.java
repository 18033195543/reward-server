package com.cjf.java.common;
public class NodeAttribute {
	private String url;
	private Integer id;
	public NodeAttribute(String url, Integer id) {
		this.url = url;
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}