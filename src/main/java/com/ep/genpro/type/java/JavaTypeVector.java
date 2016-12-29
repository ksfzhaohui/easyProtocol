package com.ep.genpro.type.java;

import java.util.ArrayList;
import java.util.List;

import com.ep.genpro.sign.JavaSign;
import com.ep.genpro.type.JavaType;
import com.ep.genpro.typeFactory.JavaTypeFactory;

public class JavaTypeVector extends JavaType {
	public static final String name = "Vector";

	public JavaTypeVector() {
		super(name);
	}

	@Override
	public String getFullTypeName(String key, String value) {
		String v = JavaTypeFactory.obj().getBoxName(value);
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
		String v = JavaTypeFactory.obj().getBoxName(value);
		return "new " + "java.util.ArrayList<" + v + ">()";
	}

	@Override
	public String getBoxName() {
		return "Vector";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		List<String> result = new ArrayList<String>();
		result.add(JavaSign.getInstance().OS_Parameter + ".compact_uint32("
				+ parameterName + ".size());");
		result.add("for(" + JavaTypeFactory.obj().getBoxName(valueType)
				+ " _v : " + parameterName + ")");
		result.add("{");
		if (JavaTypeFactory.obj().isInnerType(valueType)) {
			for (String line : JavaTypeFactory.obj().getInnerType(valueType)
					.formatMarshal("_v", null, null))
				result.add(JavaSign.getInstance().Tab[1] + line);
		} else {
			result.add(JavaSign.getInstance().Tab[1]
					+ JavaSign.getInstance().OS_Parameter + ".marshal(_v);");
		}
		result.add("}");
		return result.toArray(new String[result.size()]);
	}

	@Override
	public String[] formatUnmarshal(String parameterName, String keyType,
			String valueType, String varName) {
		List<String> result = new ArrayList<String>();
		result.add("for(int _i = " + JavaSign.getInstance().OS_Parameter
				+ ".uncompact_uint32(); _i > 0; --_i)");
		result.add("{");

		if (JavaTypeFactory.obj().isInnerType(valueType)) {
			result.add(JavaSign.getInstance().Tab[1]
					+ JavaTypeFactory.obj().getBoxName(valueType)
					+ JavaTypeFactory.obj().getInnerType(valueType)
							.formatUnmarshal(" _v_", null, null, null)[0]);
			result.add(JavaSign.getInstance().Tab[1] + parameterName
					+ ".add(_v_);");
		} else {
			result.add(JavaSign.getInstance().Tab[1]
					+ JavaTypeFactory.obj().getBoxName(valueType) + " _v_ = "
					+ "new " + valueType + "();");
			result.add(JavaSign.getInstance().Tab[1] + " _v_.unmarshal("
					+ JavaSign.getInstance().OS_Parameter + ");");
			result.add(JavaSign.getInstance().Tab[1] + parameterName
					+ ".add(_v_);");
		}

		result.add("}");
		return result.toArray(new String[result.size()]);
	}

	@Override
	public String formatHashCode(String value) {
		return value + ".hashCode()";
	}

	@Override
	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		String result = leftValue + ".equals(" + rightValue + ")";
		return equal ? result : "!" + result;
	}

	@Override
	public String[] getImport() {
		return new String[] { "java.util.List;" };
	}
}
