package com.ep.genpro.struct;

import com.ep.genpro.Struct;
import com.ep.genpro.Type;
import com.ep.genpro.Variable;
import com.ep.genpro.typeFactory.JavaTypeFactory;
import com.ep.genpro.variable.JavaVariable;

public class JavaStruct extends Struct {

	@Override
	protected String getFormatType(String namespace, String xmlType) {
		String languageType = JavaTypeFactory.obj().getLanguageType(xmlType);
		if (null != languageType) {
			return languageType;
		} else {
			return beanTypeFormat(namespace, xmlType);
		}
	}

	@Override
	protected boolean isValidBeanValue(String value) {
		return JavaTypeFactory.obj().isOrignType(value)
				|| beans.containsKey(value);
	}

	@Override
	protected Type getInnerType(String vType) {
		return JavaTypeFactory.obj().getInnerType(vType);
	}

	@Override
	protected boolean isInnerType(String vType) {
		return JavaTypeFactory.obj().isInnerType(vType);
	}

	@Override
	protected Variable variable(String vName, String vType, String vDefault,
			String vKey, String vValue, String commnet) {
		return new JavaVariable(vName, vType, vDefault, vKey, vValue, commnet);
	}

}
