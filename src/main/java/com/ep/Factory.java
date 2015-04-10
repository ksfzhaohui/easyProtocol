package com.ep;

import com.ep.config.ConfigInfo;
import com.ep.genpro.Generator;
import com.ep.genpro.Struct;
import com.ep.genpro.generator.CSharpGenerator;
import com.ep.genpro.generator.JavaGenerator;
import com.ep.genpro.struct.CSharpStruct;
import com.ep.genpro.struct.JavaStruct;

/**
 * 工厂类
 * 
 * @author zhaohui
 * 
 */
public class Factory {

	public static Struct getStruct(String language) {
		if (language.equalsIgnoreCase("java")) {
			return new JavaStruct();
		} else if (language.equalsIgnoreCase("csharp")) {
			return new CSharpStruct();
		}
		throw new RuntimeException(" unsupport language !");
	}

	public static Generator getGenerator(ConfigInfo config, Struct struct) {
		String language = config.getLanguage();
		if (language.equalsIgnoreCase("java")) {
			return new JavaGenerator(struct, config);
		} else if (language.equalsIgnoreCase("csharp")) {
			return new CSharpGenerator(struct, config);
		}
		throw new RuntimeException(" unsupport language !");
	}

	public static String getEndfix(String language) {
		if (language.equalsIgnoreCase("java")) {
			return ".java";
		} else if (language.equalsIgnoreCase("csharp")) {
			return ".cs";
		}
		throw new RuntimeException(" unsupport language !");
	}

}
