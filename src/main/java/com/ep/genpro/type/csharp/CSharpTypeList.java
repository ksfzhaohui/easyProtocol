package com.ep.genpro.type.csharp;

import java.util.ArrayList;
import java.util.List;

import com.ep.genpro.sign.CSharpSign;
import com.ep.genpro.type.CSharpType;
import com.ep.genpro.typeFactory.CSharpTypeFactory;

public class CSharpTypeList extends CSharpType {
	public static final String name = "List";

	public CSharpTypeList() {
		super(name);
	}

	@Override
	public String getFullTypeName(String key, String value) {
		String v = CSharpTypeFactory.obj().getBoxName(value);
		return "List<" + v + ">";
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		return null;
	}

	@Override
	public String formatInitialExpressionP(String initial, String key,
			String value) {
		String v = CSharpTypeFactory.obj().getBoxName(value);
		return "new " + "List<" + v + ">()";
	}

	@Override
	public String getBoxName() {
		return "List";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		List<String> result = new ArrayList<String>();
		result.add(CSharpSign.getInstance().OS_Parameter + ".Compact_uint32("
				+ parameterName + ".Count);");
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
		result.add("for(int _i = " + CSharpSign.getInstance().OS_Parameter
				+ ".Uncompact_uint32(); _i > 0; --_i)");
		result.add("{");

		if (CSharpTypeFactory.obj().isInnerType(valueType)) {
			result.add(CSharpSign.getInstance().Tab[0]
					+ CSharpTypeFactory.obj().getBoxName(valueType)
					+ CSharpTypeFactory.obj().getInnerType(valueType)
							.formatUnmarshal(" _v_", null, null, null)[0]);
			result.add(CSharpSign.getInstance().Tab[0] + parameterName
					+ ".Add(_v_);");
		} else {
			result.add(CSharpSign.getInstance().Tab[0] + valueType + " _v_ = "
					+ "new " + valueType + "();");
			result.add(CSharpSign.getInstance().Tab[0] + " _v_.Unmarshal("
					+ CSharpSign.getInstance().OS_Parameter + ");");
			result.add(CSharpSign.getInstance().Tab[0] + parameterName
					+ ".Add(_v_);");
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
