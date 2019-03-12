package com.cjf.java.common;
import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node> {
	private Integer id;
	private Integer parentId;
	private String text;
	private String state;
	private NodeAttribute attributes;
	private List<Node> children = new LinkedList<Node>();
	private Integer order;//节点的状态

	public Node(Integer id, Integer parentId, String text, String state, NodeAttribute attributes,Integer order) {
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.state = state;
		this.attributes = attributes;
		this.order = order;
	}
	
	public int compareTo(Node o) {
		if(order>o.order){
			return 1;
		}
		if(order<o.order){
			return -1;
		}
		return 0;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public NodeAttribute getAttributes() {
		return attributes;
	}
	public void setAttributes(NodeAttribute attributes) {
		this.attributes = attributes;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	
}