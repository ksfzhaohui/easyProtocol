package com.ep.genpro.typeFactory;

import com.ep.genpro.TypeFactory;
import com.ep.genpro.type.java.JavaTypeArray;
import com.ep.genpro.type.java.JavaTypeBean;
import com.ep.genpro.type.java.JavaTypeBoolean;
import com.ep.genpro.type.java.JavaTypeByte;
import com.ep.genpro.type.java.JavaTypeDouble;
import com.ep.genpro.type.java.JavaTypeFloat;
import com.ep.genpro.type.java.JavaTypeInt;
import com.ep.genpro.type.java.JavaTypeList;
import com.ep.genpro.type.java.JavaTypeLong;
import com.ep.genpro.type.java.JavaTypeMap;
import com.ep.genpro.type.java.JavaTypeOctets;
import com.ep.genpro.type.java.JavaTypeSet;
import com.ep.genpro.type.java.JavaTypeShort;
import com.ep.genpro.type.java.JavaTypeString;
import com.ep.genpro.type.java.JavaTypeVector;

public class JavaTypeFactory extends TypeFactory {

	private static JavaTypeFactory typeFactory = new JavaTypeFactory();

	public JavaTypeFactory() {
		declare2Type.put("bool", JavaTypeBoolean.name);
		declare2Type.put("boolean", JavaTypeBoolean.name);
		declare2Type.put("byte", JavaTypeByte.name);
		declare2Type.put("short", JavaTypeShort.name);
		declare2Type.put("int", JavaTypeInt.name);
		declare2Type.put("long", JavaTypeLong.name);
		declare2Type.put("float", JavaTypeFloat.name);
		declare2Type.put("double", JavaTypeDouble.name);
		declare2Type.put("string", JavaTypeString.name);
		declare2Type.put("octets", JavaTypeOctets.name);
		declare2Type.put("set", JavaTypeSet.name);
		declare2Type.put("map", JavaTypeMap.name);
		declare2Type.put("array", JavaTypeArray.name);
		declare2Type.put("list", JavaTypeList.name);
		declare2Type.put("vector", JavaTypeVector.name);

		innerTypes.put(JavaTypeByte.name, new JavaTypeByte());
		innerTypes.put(JavaTypeShort.name, new JavaTypeShort());
		innerTypes.put(JavaTypeInt.name, new JavaTypeInt());
		innerTypes.put(JavaTypeLong.name, new JavaTypeLong());
		innerTypes.put(JavaTypeFloat.name, new JavaTypeFloat());
		innerTypes.put(JavaTypeDouble.name, new JavaTypeDouble());
		innerTypes.put(JavaTypeBoolean.name, new JavaTypeBoolean());
		innerTypes.put(JavaTypeString.name, new JavaTypeString());
		innerTypes.put(JavaTypeOctets.name, new JavaTypeOctets());

		containerTypes.put(JavaTypeMap.name, new JavaTypeMap());
		containerTypes.put(JavaTypeSet.name, new JavaTypeSet());
		containerTypes.put(JavaTypeList.name, new JavaTypeList());
		containerTypes.put(JavaTypeArray.name, new JavaTypeArray());
		containerTypes.put(JavaTypeVector.name, new JavaTypeVector());

		beanType = new JavaTypeBean();
	}

	public static TypeFactory obj() {
		return typeFactory;
	}
}
