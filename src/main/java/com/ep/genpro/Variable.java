package com.ep.genpro;

public abstract class Variable {

	/** 名称 **/
	protected String name;
	/** 类型 **/
	protected String type;
	/** 默认值 **/
	protected String initial;
	/** map的key类型 **/
	protected String key;
	/** map的value类型 **/
	protected String value;
	/** 注释 **/
	protected String comment;

	public Variable(String name, String type, String initial, String key,
			String value, String comment) {
		this.name = name;
		this.type = type;
		this.initial = initial;
		this.key = key;
		this.value = value;
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract String fullType();

}
