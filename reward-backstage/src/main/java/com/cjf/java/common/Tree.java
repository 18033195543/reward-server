package com.cjf.java.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.cjf.java.entity.FunctionEntity;

public class Tree {
	private List<Node> nodes = new LinkedList<Node>();
	private Node root = null;//根节点	
	public Tree(List<FunctionEntity> functions){
		for(FunctionEntity function:functions){
			Node node = new Node(function.getId(),function.getParentId(),function.getMenuName(),"open",
					new NodeAttribute(null==function.getUrl()?"":function.getUrl(),function.getId()),
					function.getOrder());
			nodes.add(node);			
			if(node.getId()==0){
				root = node;
			}
		}
	}	
	public List<Node> build(){
		buildTree(root);
		List<Node> result = new ArrayList<Node>();
		result.add(root);
		return result;
	}	
	private void buildTree(Node parent){
		Node node = null;
		for(Iterator<Node> ite=nodes.iterator();ite.hasNext();){
			node = ite.next();
			if(Objects.equals(node.getParentId(), parent.getId())){
				parent.getChildren().add(node);
				buildTree(node);
			}
		}
	}
}