package com.ep.genpro.type.csharp;

import com.ep.config.Bean;
import com.ep.genpro.Variable;
import com.ep.genpro.sign.CSharpSign;
import com.ep.genpro.type.CSharpType;

public class CSharpTypeBean extends CSharpType {
	private String beanName = null;

	@Override
	public void decorateBean(Bean bean) {
		this.beanName = bean.getName();
	}

	@Override
	public void decorateBean(Variable variable) {
		this.beanName = variable.getType();
	}

	public CSharpTypeBean() {
		super("bean");
	}

	@Override
	public String getTypeName() {
		return beanName;
	}

	@Override
	public String getFullTypeName(String key, String value) {
		return beanName;
	}

	@Override
	public String getBoxName() {
		return beanName;
	}

	@Override
	public String formatInitialExpression(String initial, String key,
			String value, String varName) {
		return "new _" + beanName + "(this, " + varName + ")";
	}

	@Override
	public String formatInitialExpressionP(String initial, String key,
			String value) {
		return "new " + beanName + "()";
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		return new String[] { parameterName + ".Marshal("
				+ CSharpSign.getInstance().OS_Parameter + ");" };
	}

	@Override
	public String[] formatUnmarshal(String parameterName, String keyType,
			String valueType, String varName) {
		return new String[] { parameterName + ".Unmarshal("
				+ CSharpSign.getInstance().OS_Parameter + ");" };
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
