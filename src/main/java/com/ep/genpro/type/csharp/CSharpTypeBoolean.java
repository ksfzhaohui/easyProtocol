package com.ep.genpro.type.csharp;

import com.ep.genpro.type.CSharpType;

public class CSharpTypeBoolean extends CSharpType {
	public static final String name = "bool";

	public CSharpTypeBoolean() {
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
		return "true".equals(initial.toLowerCase())
				|| "false".equals(initial.toLowerCase());
	}

	@Override
	public String getBoxName() {
		return "bool";
	}

	@Override
	public String formatHashCode(String value) {
		return "(" + value + " ? 1231 : 1237)";
	}
}
