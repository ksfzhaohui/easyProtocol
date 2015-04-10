package com.ep.genpro.struct;

import com.ep.genpro.Struct;
import com.ep.genpro.Type;
import com.ep.genpro.Variable;
import com.ep.genpro.typeFactory.JavaTypeFactory;
import com.ep.genpro.variable.JavaVariable;

public class JavaStruct extends Struct {

	protected String getFormatType(String namespace, String xmlType) {
		String languageType = JavaTypeFactory.obj().getLanguageType(xmlType);
		if (null != languageType) {
			return languageType;
		} else {
			return changeNamespace(namespace, xmlType);
		}
	}

	/**
	 * 参数为编程语言类型
	 * 
	 * @param value
	 * @return
	 */
	protected boolean isValidBeanValue(String value) {
		return JavaTypeFactory.obj().isOrignType(value)
				|| beans.containsKey(value);
	}

	protected Type getInnerType(String vType) {
		return JavaTypeFactory.obj().getInnerType(vType);
	}

	protected boolean isInnerType(String vType) {
		return JavaTypeFactory.obj().isInnerType(vType);
	}

	@Override
	protected Variable variable(String vName, String vType, String vDefault,
			String vKey, String vValue, String commnet) {
		return new JavaVariable(vName, vType, vDefault, vKey, vValue, commnet);
	}

}
