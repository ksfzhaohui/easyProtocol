package com.ep.genpro.type;

import com.ep.genpro.Type;
import com.ep.genpro.sign.JavaSign;

public abstract class JavaType extends Type {

	public JavaType(String typeName) {
		super(typeName);
	}

	@Override
	public String[] formatMarshal(String parameterName, String keyType,
			String valueType) {
		return new String[] { JavaSign.getInstance().OS_Parameter + ".marshal("
				+ parameterName + ");" };
	}

	@Override
	public String[] formatUnmarshal(String paraneterName, String keyType,
			String valueType, String varName) {
		return new String[] { paraneterName + " = "
				+ JavaSign.getInstance().OS_Parameter + ".unmarshal_"
				+ typeName + "();" };
	}
}
