package com.ep.genpro.type.java;

import com.ep.genpro.type.JavaType;

/**
 * 
 * @author zhaohui
 *
 */
public class JavaTypeLong extends JavaType {
	public static final String name = "long";

	public JavaTypeLong() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		if (null == initial)
			return "0l";
		else
			return initial + "l";
	}

	@Override
	public boolean isInitialValid(String initial) {
		try {
			Long.parseLong(initial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getBoxName() {
		return "Long";
	}

	@Override
	public String formatHashCode(String value) {
		return "(int)(" + value + " ^ (" + value + " >>> 32))";
	}
}
