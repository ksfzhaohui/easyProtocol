package com.ep.genpro.type.csharp;

import java.util.ArrayList;
import java.util.List;

import com.ep.genpro.sign.CSharpSign;
import com.ep.genpro.type.CSharpType;
import com.ep.genpro.typeFactory.CSharpTypeFactory;

public class CSharpTypeArray extends CSharpType {
	public static final String name = "Array";

	public CSharpTypeArray() {
		super(name);
	}

	@Override
	public String getFullTypeName(String key, String value) {
		return CSharpTypeFactory.obj().getBoxName(value) + "[]";
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		return null;
	}

	@Override
	public String formatInitialExpressionP(String initial, String key,
			String value) {
		return "new " + CSharpTypeFactory.obj().getBoxName(value) + "[0]";
	}

	@Override
	public String getBoxName() {
		return "Array";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		List<String> result = new ArrayList<String>();
		result.add(CSharpSign.getInstance().OS_Parameter + ".Compact_uint32("
				+ parameterName + ".Length);");
		result.add("foreach(" + valueType + " _v in " + parameterName + ")");
		result.add("{");
		if (CSharpTypeFactory.obj().isInnerType(valueType)) {
			for (String line : CSharpTypeFactory.obj().getInnerType(valueType)
					.formatMarshal("_v", null, null))
				result.add(CSharpSign.getInstance().Tab[0] + line);
		} else {
			result.add(CSharpSign.getInstance().Tab[0]
					+ CSharpSign.getInstance().OS_Parameter + ".Marshal(_v);");
		}
		result.add("}");
		return result.toArray(new String[result.size()]);
	}

	@Override
	public String[] formatUnmarshal(String parameterName, String keyType,
			String valueType, String varName) {
		List<String> result = new ArrayList<String>();
		result.add(String.format("%s = new %s[%s];", parameterName,
				CSharpTypeFactory.obj().getBoxName(valueType),
				CSharpSign.getInstance().OS_Parameter + ".Uncompact_uint32()"));
		result.add("for(int _i = " + parameterName + ".Length; _i > 0; --_i)");
		result.add("{");

		if (CSharpTypeFactory.obj().isInnerType(valueType)) {
			result.add(CSharpSign.getInstance().Tab[0]
					+ CSharpTypeFactory.obj().getBoxName(valueType)
					+ CSharpTypeFactory.obj().getInnerType(valueType)
							.formatUnmarshal(" _v_", null, null, null)[0]);
			result.add(CSharpSign.getInstance().Tab[0]
					+ String.format("%s[%s.Length - _i] = _v_;", parameterName,
							parameterName));
		} else {
			result.add(CSharpSign.getInstance().Tab[0] + valueType + " _v_ = "
					+ "new " + valueType + "();");
			result.add(CSharpSign.getInstance().Tab[0] + " _v_.Unmarshal("
					+ CSharpSign.getInstance().OS_Parameter + ");");
			result.add(CSharpSign.getInstance().Tab[0]
					+ String.format("%s[%s.Length - _i] = _v_;", parameterName,
							parameterName));
		}

		result.add("}");
		return result.toArray(new String[result.size()]);
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
