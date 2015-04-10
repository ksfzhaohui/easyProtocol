package com.ep.genpro.type.java;

import com.ep.genpro.type.JavaType;

/**
 * 
 * @author zhaohui
 *
 */
public class JavaTypeBoolean extends JavaType {
	public static final String name = "boolean";

	public JavaTypeBoolean() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		if (null == initial)
			return "true";
		else
			return initial;
	}

	@Override
	public boolean isInitialValid(String initial) {
		return initial.toLowerCase().equals("true")
				|| initial.toLowerCase().equals("false");
	}

	@Override
	public String getBoxName() {
		return "Boolean";
	}

	@Override
	public String formatHashCode(String value) {
		return "(" + value + " ? 1231 : 1237)";
	}
}
