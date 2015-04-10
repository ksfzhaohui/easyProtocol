package com.ep.genpro.type.csharp;

import com.ep.genpro.type.CSharpType;

public class CSharpTypeDouble extends CSharpType {
	public static final String name = "double";

	public CSharpTypeDouble() {
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
		return "(int)(BitConverter.DoubleToInt64Bits(" + value
				+ ") ^ ( BitConverter.DoubleToInt64Bits(" + value + ") >> 32))";
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		if (equal) {
			return "BitConverter.DoubleToInt64Bits(" + leftValue
					+ ") == BitConverter.DoubleToInt64Bits(" + rightValue + ")";
		} else {
			return "BitConverter.DoubleToInt64Bits(" + leftValue
					+ ") != BitConverter.DoubleToInt64Bits(" + rightValue + ")";
		}
	}
}
