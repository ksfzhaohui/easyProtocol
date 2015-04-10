package com.ep.genpro.type;

import com.ep.genpro.Type;
import com.ep.genpro.sign.CSharpSign;

public abstract class CSharpType extends Type {

	public CSharpType(String typeName) {
		super(typeName);
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		return new String[] { CSharpSign.getInstance().OS_Parameter
				+ ".Marshal(" + parameterName + ");" };
	}

	@Override
	public String[] formatUnmarshal(String paraneterName, String keyType,
			String valueType, String varName) {
		return new String[] { paraneterName + " = "
				+ CSharpSign.getInstance().OS_Parameter + ".Unmarshal_"
				+ typeName + "();" };
	}
}
