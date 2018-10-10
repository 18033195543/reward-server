package com.kdl.common.framework.filter;

import org.springframework.context.ApplicationEvent;

public class UserEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public UserEvent(Object source) {
		super(source);
	}

}
