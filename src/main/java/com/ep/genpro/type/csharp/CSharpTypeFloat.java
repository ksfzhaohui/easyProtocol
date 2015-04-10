package com.ep.genpro.type.csharp;

import com.ep.genpro.type.CSharpType;

public class CSharpTypeFloat extends CSharpType {
	public static final String name = "float";

	public CSharpTypeFloat() {
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
		return "float";
	}

	@Override
	public String formatHashCode(String value) {
		return "BitConverter.ToInt32(BitConverter.GetBytes(" + value + "), 0)";
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		if (equal) {
			return "BitConverter.ToInt32(BitConverter.GetBytes(" + leftValue
					+ "), 0) == BitConverter.ToInt32(BitConverter.GetBytes("
					+ rightValue + "), 0)";
		} else {
			return "BitConverter.ToInt32(BitConverter.GetBytes(" + leftValue
					+ "), 0) != BitConverter.ToInt32(BitConverter.GetBytes("
					+ rightValue + "), 0)";
		}
	}
}
