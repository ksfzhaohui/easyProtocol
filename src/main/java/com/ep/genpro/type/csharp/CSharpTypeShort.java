package com.ep.genpro.type.csharp;

import com.ep.genpro.type.CSharpType;

public class CSharpTypeShort extends CSharpType {
	public static final String name = "short";

	public CSharpTypeShort() {
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
			return "(short)0";
		else
			return "(short)" + initial;
	}

	@Override
	public boolean isInitialValid(String initial) {
		try {
			Short.parseShort(initial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getBoxName() {
		return "short";
	}
}
