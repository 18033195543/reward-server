package com.cjf.java.enums;

/**
 * 是否显示顶级菜单
 * @author jfyus
 *
 */
public enum Whether {

	YES(1,"是"),
	NO(0, "否");
	
	private int value;
	private String desc;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	Whether(int value, String desc){
		this.value = value;
		this.desc = desc;
	}
}
