package com.ep.genpro.struct;

import com.ep.genpro.Struct;
import com.ep.genpro.Type;
import com.ep.genpro.Variable;
import com.ep.genpro.typeFactory.CSharpTypeFactory;
import com.ep.genpro.variable.CSharpVariable;

public class CSharpStruct extends Struct {

	@Override
	protected String getFormatType(String namespace, String xmlType) {
		String languageType = CSharpTypeFactory.obj().getLanguageType(xmlType);
		if (null != languageType) {
			return languageType;
		} else {
			return beanTypeFormat(namespace, xmlType);
		}
	}

	@Override
	protected boolean isValidBeanValue(String value) {
		return CSharpTypeFactory.obj().isOrignType(value)
				|| beans.containsKey(value);
	}

	@Override
	protected Type getInnerType(String vType) {
		return CSharpTypeFactory.obj().getInnerType(vType);
	}

	@Override
	protected boolean isInnerType(String vType) {
		return CSharpTypeFactory.obj().isInnerType(vType);
	}

	@Override
	protected Variable variable(String vName, String vType, String vDefault,
			String vKey, String vValue, String commnet) {
		return new CSharpVariable(vName, vType, vDefault, vKey, vValue, commnet);
	}
}
