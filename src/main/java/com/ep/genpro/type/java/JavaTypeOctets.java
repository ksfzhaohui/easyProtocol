package com.ep.genpro.type.java;

import com.ep.genpro.sign.JavaSign;
import com.ep.genpro.type.JavaType;

public class JavaTypeOctets extends JavaType {
	public static final String name = "Octets";

	public JavaTypeOctets() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		return "new Octets()";
	}

	@Override
	public boolean isInitialValid(String initial) {
		return false;
	}

	@Override
	public String getBoxName() {
		return "Octets";
	}

	@Override
	public String formatHashCode(String value) {
		return value + ".hashCode()";
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		String result = leftValue + ".equals(" + rightValue + ")";
		return equal ? result : "!" + result;
	}

	@Override
	public String[] getImport() {
		return new String[] { JavaSign.getInstance().ObjPath[4] + ";" };
	}
}
