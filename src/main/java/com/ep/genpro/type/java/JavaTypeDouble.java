package com.ep.genpro.type.java;

import com.ep.genpro.type.JavaType;

/**
 * 
 * @author zhaohui
 *
 */
public class JavaTypeDouble extends JavaType {
	public static final String name = "double";

	public JavaTypeDouble() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		if (null == initial)
			return "0.0";
		else
			return initial;
	}

	@Override
	public boolean isInitialValid(String initial) {
		try {
			Double.parseDouble(initial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getBoxName() {
		return "Double";
	}

	@Override
	public String formatHashCode(String value) {
		return "(int)(Double.doubleToLongBits(" + value
				+ ") ^ ( Double.doubleToLongBits(" + value + ") >>> 32))";
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		if (equal) {
			return "Double.doubleToLongBits(" + leftValue
					+ ") == Double.doubleToLongBits(" + rightValue + ")";
		} else {
			return "Double.doubleToLongBits(" + leftValue
					+ ") != Double.doubleToLongBits(" + rightValue + ")";
		}
	}
}
