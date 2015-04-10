package com.ep.config;

public class Protocol extends Bean {

	private int type; //协议号
	private int maxSize; //协议大小

	public Protocol(String name, String comment, int type, int maxSize) {
		super(name, comment);
		this.type = type;
		this.maxSize = maxSize;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

}
