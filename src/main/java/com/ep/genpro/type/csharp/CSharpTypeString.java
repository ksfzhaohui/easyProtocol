package com.ep.genpro.type.csharp;

import com.ep.genpro.sign.CSharpSign;
import com.ep.genpro.type.CSharpType;

public class CSharpTypeString extends CSharpType {
	public static final String name = "string";

	public CSharpTypeString() {
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
		return "string";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		return new String[] { CSharpSign.getInstance().OS_Parameter
				+ ".Marshal(" + parameterName + ",\"" + CSharpSign.CharSet
				+ "\");" };
	}

	@Override
	public String[] formatUnmarshal(String paraneterName, String keyType,
			String valueType, String varName) {
		return new String[] { paraneterName + " = "
				+ CSharpSign.getInstance().OS_Parameter
				+ ".Unmarshal_string(\"" + CSharpSign.CharSet + "\");" };
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
