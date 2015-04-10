package com.ep.genpro.type.csharp;

import com.ep.genpro.type.CSharpType;

public class CSharpTypeInt extends CSharpType {
	public static final String name = "int";

	public CSharpTypeInt() {
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
	public boolean isInitialValid(String initial) {
		try {
			Integer.parseInt(initial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getBoxName() {
		return "int";
	}
}
