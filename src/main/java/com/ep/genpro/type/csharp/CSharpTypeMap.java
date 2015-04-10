package com.ep.genpro.type.csharp;

import java.util.ArrayList;
import java.util.List;

import com.ep.genpro.sign.CSharpSign;
import com.ep.genpro.type.CSharpType;
import com.ep.genpro.typeFactory.CSharpTypeFactory;

public class CSharpTypeMap extends CSharpType {
	public static final String name = "Map";

	public final String _Entry = "KeyValuePair";

	public CSharpTypeMap() {
		super(name);
	}

	@Override
	public String getFullTypeName(String key, String value) {
		String k = CSharpTypeFactory.obj().getBoxName(key);
		String v = CSharpTypeFactory.obj().getBoxName(value);

		return "Dictionary<" + k + ", " + v + ">";
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		return null;
	}

	@Override
	public String formatInitialExpressionP(String initial, String key,
			String value) {
		String k = CSharpTypeFactory.obj().getBoxName(key);
		String v = CSharpTypeFactory.obj().getBoxName(value);

		return "new " + "Dictionary<" + k + ", " + v + ">()";
	}

	@Override
	public String getBoxName() {
		return "Dictionary";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		List<String> result = new ArrayList<String>();
		result.add(CSharpSign.getInstance().OS_Parameter + ".Compact_uint32("
				+ parameterName + ".Count);");

		String k = CSharpTypeFactory.obj().getBoxName(keyType);
		String v = CSharpTypeFactory.obj().getBoxName(valueType);

		result.add("foreach(" + _Entry + "<" + k + " , " + v + "> _e_ in "
				+ parameterName + ")");
		result.add("{");

		if (CSharpTypeFactory.obj().isInnerType(keyType))
			result.add(CSharpSign.getInstance().Tab[0]
					+ CSharpTypeFactory.obj().getInnerType(keyType)
							.formatMarshal("_e_.Key", null, null)[0]);
		else
			result.add(CSharpSign.getInstance().Tab[0] + "_e_.Key.Marshal("
					+ CSharpSign.getInstance().OS_Parameter + ");");

		if (CSharpTypeFactory.obj().isInnerType(valueType))
			result.add(CSharpSign.getInstance().Tab[0]
					+ CSharpTypeFactory.obj().getInnerType(valueType)
							.formatMarshal("_e_.Value", null, null)[0]);
		else
			result.add(CSharpSign.getInstance().Tab[0] + "_e_.Value.Marshal("
					+ CSharpSign.getInstance().OS_Parameter + ");");

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

		String k = CSharpTypeFactory.obj().getBoxName(keyType);
		String v = CSharpTypeFactory.obj().getBoxName(valueType);

		if (CSharpTypeFactory.obj().isInnerType(keyType)) {
			result.add(CSharpSign.getInstance().Tab[0]
					+ k
					+ CSharpTypeFactory.obj().getInnerType(keyType)
							.formatUnmarshal(" _k_", null, null, null)[0]);
		} else {
			result.add(CSharpSign.getInstance().Tab[0] + keyType + " _k_ = "
					+ "new " + keyType + "();");
			result.add(CSharpSign.getInstance().Tab[0] + " _k_.Unmarshal("
					+ CSharpSign.getInstance().OS_Parameter + ");");
		}

		if (CSharpTypeFactory.obj().isInnerType(valueType)) {
			result.add(CSharpSign.getInstance().Tab[0]
					+ v
					+ CSharpTypeFactory.obj().getInnerType(valueType)
							.formatUnmarshal(" _v_", null, null, null)[0]);
		} else {
			result.add(CSharpSign.getInstance().Tab[0] + valueType + " _v_ = "
					+ "new " + valueType + "();");
			result.add(CSharpSign.getInstance().Tab[0] + " _v_.Unmarshal("
					+ CSharpSign.getInstance().OS_Parameter + ");");
		}
		result.add(CSharpSign.getInstance().Tab[0] + parameterName
				+ ".Add(_k_, _v_);");

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
