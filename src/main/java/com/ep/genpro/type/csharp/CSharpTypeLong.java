package com.ep.genpro.type.csharp;

import com.ep.genpro.type.CSharpType;

public class CSharpTypeLong extends CSharpType {
	public static final String name = "long";

	public CSharpTypeLong() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		if (null == initial)
			return "0L";
		else
			return initial + "L";
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
		return "long";
	}

	@Override
	public String formatHashCode(String value) {
		return "(int)(" + value + " ^ (" + value + " >> 32))";
	}
}
