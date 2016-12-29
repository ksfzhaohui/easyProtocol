package com.ep.genpro.variable;

import com.ep.genpro.Type;
import com.ep.genpro.Variable;
import com.ep.genpro.typeFactory.JavaTypeFactory;

public class JavaVariable extends Variable {

	public JavaVariable(String name, String type, String initial, String key,
			String value, String comment) {
		super(name, type, initial, key, value, comment);
	}

	@Override
	public String fullType() {
		Type t = JavaTypeFactory.obj().getAllType(type, this);
		return t.getFullTypeName(key, value);
	}

}
