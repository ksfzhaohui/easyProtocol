package com.ep.genpro.type.java;

import java.util.ArrayList;
import java.util.List;

import com.ep.genpro.sign.JavaSign;
import com.ep.genpro.type.JavaType;
import com.ep.genpro.typeFactory.JavaTypeFactory;

/**
 * 
 * @author zhaohui
 *
 */
public class JavaTypeMap extends JavaType {
	public static final String name = "Map";

	public final String _Entry = "Entry";

	public JavaTypeMap() {
		super(name);
	}

	@Override
	public String getFullTypeName(String key, String value) {
		String k = JavaTypeFactory.obj().getBoxName(key);
		String v = JavaTypeFactory.obj().getBoxName(value);

		return "Map<" + k + ", " + v + ">";
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		return null;
	}

	@Override
	public String formatInitialExpressionP(String initial, String key,
			String value) {
		String k = JavaTypeFactory.obj().getBoxName(key);
		String v = JavaTypeFactory.obj().getBoxName(value);

		return "new " + "java.util.HashMap<" + k + ", " + v + ">()";
	}

	@Override
	public String getBoxName() {
		return "Map";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		List<String> result = new ArrayList<String>();
		result.add(JavaSign.getInstance().OS_Parameter + ".compact_uint32("
				+ parameterName + ".size());");

		String k = JavaTypeFactory.obj().getBoxName(keyType);
		String v = JavaTypeFactory.obj().getBoxName(valueType);

		result.add("for(" + _Entry + "<" + k + " , " + v + "> _e_ : "
				+ parameterName + ".entrySet())");
		result.add("{");

		if (JavaTypeFactory.obj().isInnerType(keyType))
			result.add(JavaSign.getInstance().Tab[1]
					+ JavaTypeFactory.obj().getInnerType(keyType)
							.formatMarshal("_e_.getKey()", null, null)[0]);
		else
			result.add(JavaSign.getInstance().Tab[1] + "_e_.getKey().marshal("
					+ JavaSign.getInstance().OS_Parameter + ");");

		if (JavaTypeFactory.obj().isInnerType(valueType))
			result.add(JavaSign.getInstance().Tab[1]
					+ JavaTypeFactory.obj().getInnerType(valueType)
							.formatMarshal("_e_.getValue()", null, null)[0]);
		else
			result.add(JavaSign.getInstance().Tab[1]
					+ "_e_.getValue().marshal("
					+ JavaSign.getInstance().OS_Parameter + ");");

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

		String k = JavaTypeFactory.obj().getBoxName(keyType);
		String v = JavaTypeFactory.obj().getBoxName(valueType);

		if (JavaTypeFactory.obj().isInnerType(keyType)) {
			result.add(JavaSign.getInstance().Tab[1]
					+ k
					+ JavaTypeFactory.obj().getInnerType(keyType)
							.formatUnmarshal(" _k_", null, null, null)[0]);
		} else {
			result.add(JavaSign.getInstance().Tab[1] + keyType + " _k_ = "
					+ "new " + keyType + "();");
			result.add(JavaSign.getInstance().Tab[1] + " _k_.unmarshal("
					+ JavaSign.getInstance().OS_Parameter + ");");
		}

		if (JavaTypeFactory.obj().isInnerType(valueType)) {
			result.add(JavaSign.getInstance().Tab[1]
					+ v
					+ JavaTypeFactory.obj().getInnerType(valueType)
							.formatUnmarshal(" _v_", null, null, null)[0]);
		} else {
			result.add(JavaSign.getInstance().Tab[1] + valueType + " _v_ = "
					+ "new " + valueType + "();");
			result.add(JavaSign.getInstance().Tab[1] + " _v_.unmarshal("
					+ JavaSign.getInstance().OS_Parameter + ");");
		}
		result.add(JavaSign.getInstance().Tab[1] + "this." + parameterName
				+ ".put(_k_, _v_);");

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
		return new String[] { "java.util.Map;", "java.util.Map.Entry;" };
	}
}
