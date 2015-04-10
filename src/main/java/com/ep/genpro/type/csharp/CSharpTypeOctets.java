package com.ep.genpro.type.csharp;

import com.ep.genpro.type.CSharpType;

public class CSharpTypeOctets extends CSharpType {
	public static final String name = "Octets";

	public CSharpTypeOctets() {
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
		return value + ".GetHashCode()";
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		String result = leftValue + ".Equals(" + rightValue + ")";
		return equal ? result : "!" + result;
	}
}
