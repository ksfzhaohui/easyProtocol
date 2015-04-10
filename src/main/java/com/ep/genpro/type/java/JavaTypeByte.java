package com.ep.genpro.type.java;

import com.ep.genpro.type.JavaType;

/**
 * 
 * @author zhaohui
 *
 */
public class JavaTypeByte extends JavaType {
	public static final String name = "byte";

	public JavaTypeByte() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		if (null == initial)
			return "0";
		else
			return initial;
	}

	@Override
	public String formatInitialExpressionP(String initial, String key,
			String value) {
		if (null == initial)
			return "(byte)0";
		else
			return "(byte)" + initial;
	}

	@Override
	public boolean isInitialValid(String initial) {
		try {
			Byte.parseByte(initial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getBoxName() {
		return "Byte";
	}
}
