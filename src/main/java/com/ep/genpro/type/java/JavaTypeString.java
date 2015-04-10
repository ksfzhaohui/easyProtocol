package com.ep.genpro.type.java;

import com.ep.genpro.sign.JavaSign;
import com.ep.genpro.type.JavaType;

public class JavaTypeString extends JavaType {
	public static final String name = "String";

	public JavaTypeString() {
		super(name);
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		String in = initial == null ? "" : initial;
		return "\"" + in + "\"";
	}

	@Override
	public boolean isInitialValid(String initial) {
		return true;
	}

	@Override
	public String getBoxName() {
		return "String";
	}

	@Override
	public String formatHashCode(String value) {
		return value + ".hashCode()";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		return new String[] { JavaSign.getInstance().OS_Parameter + ".marshal("
				+ parameterName + ",\"" + JavaSign.CharSet + "\");" };
	}

	@Override
	public String[] formatUnmarshal(String paraneterName, String keyType,
			String valueType, String varName) {
		return new String[] { paraneterName + " = "
				+ JavaSign.getInstance().OS_Parameter + ".unmarshal_String(\""
				+ JavaSign.CharSet + "\");" };
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		String result = leftValue + ".equals(" + rightValue + ")";
		return equal ? result : "!" + result;
	}
}
