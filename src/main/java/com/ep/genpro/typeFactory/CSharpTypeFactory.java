package com.ep.genpro.typeFactory;

import com.ep.genpro.TypeFactory;
import com.ep.genpro.type.csharp.CSharpTypeArray;
import com.ep.genpro.type.csharp.CSharpTypeBean;
import com.ep.genpro.type.csharp.CSharpTypeBoolean;
import com.ep.genpro.type.csharp.CSharpTypeByte;
import com.ep.genpro.type.csharp.CSharpTypeDouble;
import com.ep.genpro.type.csharp.CSharpTypeFloat;
import com.ep.genpro.type.csharp.CSharpTypeInt;
import com.ep.genpro.type.csharp.CSharpTypeList;
import com.ep.genpro.type.csharp.CSharpTypeLong;
import com.ep.genpro.type.csharp.CSharpTypeMap;
import com.ep.genpro.type.csharp.CSharpTypeOctets;
import com.ep.genpro.type.csharp.CSharpTypeSet;
import com.ep.genpro.type.csharp.CSharpTypeShort;
import com.ep.genpro.type.csharp.CSharpTypeString;
import com.ep.genpro.type.csharp.CSharpTypeVector;

public class CSharpTypeFactory extends TypeFactory {

	private static CSharpTypeFactory typeFactory = new CSharpTypeFactory();

	public CSharpTypeFactory() {
		declare2Type.put("bool", CSharpTypeBoolean.name);
		declare2Type.put("boolean", CSharpTypeBoolean.name);
		declare2Type.put("byte", CSharpTypeByte.name);
		declare2Type.put("short", CSharpTypeShort.name);
		declare2Type.put("int", CSharpTypeInt.name);
		declare2Type.put("long", CSharpTypeLong.name);
		declare2Type.put("float", CSharpTypeFloat.name);
		declare2Type.put("double", CSharpTypeDouble.name);
		declare2Type.put("string", CSharpTypeString.name);
		declare2Type.put("octets", CSharpTypeOctets.name);
		declare2Type.put("set", CSharpTypeSet.name);
		declare2Type.put("map", CSharpTypeMap.name);
		declare2Type.put("array", CSharpTypeArray.name);
		declare2Type.put("list", CSharpTypeList.name);
		declare2Type.put("vector", CSharpTypeVector.name);

		innerTypes.put(CSharpTypeByte.name, new CSharpTypeByte());
		innerTypes.put(CSharpTypeShort.name, new CSharpTypeShort());
		innerTypes.put(CSharpTypeInt.name, new CSharpTypeInt());
		innerTypes.put(CSharpTypeLong.name, new CSharpTypeLong());
		innerTypes.put(CSharpTypeFloat.name, new CSharpTypeFloat());
		innerTypes.put(CSharpTypeDouble.name, new CSharpTypeDouble());
		innerTypes.put(CSharpTypeBoolean.name, new CSharpTypeBoolean());
		innerTypes.put(CSharpTypeString.name, new CSharpTypeString());
		innerTypes.put(CSharpTypeOctets.name, new CSharpTypeOctets());

		containerTypes.put(CSharpTypeMap.name, new CSharpTypeMap());
		containerTypes.put(CSharpTypeSet.name, new CSharpTypeSet());
		containerTypes.put(CSharpTypeList.name, new CSharpTypeList());
		containerTypes.put(CSharpTypeArray.name, new CSharpTypeArray());
		containerTypes.put(CSharpTypeVector.name, new CSharpTypeVector());

		beanType = new CSharpTypeBean();
	}

	public static TypeFactory obj() {
		return typeFactory;
	}
}
