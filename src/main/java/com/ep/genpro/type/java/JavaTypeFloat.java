package com.ep.genpro.type.java;

import com.ep.genpro.type.JavaType;

/**
 * 
 * @author zhaohui
 *
 */
public class JavaTypeFloat extends JavaType {
	public static final String name = "float";

	public JavaTypeFloat() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		if (null == initial)
			return "0.0f";
		else
			return initial + "f";
	}

	@Override
	public boolean isInitialValid(String initial) {
		try {
			Float.parseFloat(initial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getBoxName() {
		return "Float";
	}

	@Override
	public String formatHashCode(String value) {
		return "Float.floatToIntBits(" + value + ")";
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		if (equal) {
			return "Float.floatToIntBits(" + leftValue
					+ ") == Float.floatToIntBits(" + rightValue + ")";
		} else {
			return "Float.floatToIntBits(" + leftValue
					+ ") != Float.floatToIntBits(" + rightValue + ")";
		}
	}
}
