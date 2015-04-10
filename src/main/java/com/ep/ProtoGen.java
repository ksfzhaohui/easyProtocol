package com.ep;

import com.ep.config.ConfigInfo;
import com.ep.config.File;
import com.ep.genpro.Generator;
import com.ep.genpro.Struct;

public class ProtoGen {

	/**
	 * 生成协议
	 * 
	 * @param config
	 *            配置信息
	 */
	public static void genFile(ConfigInfo config) {
		config.checkConfig();

		for (File file : config.getFileList()) {
			// 协议文件结构
			Struct struct = Factory.getStruct(config.getLanguage());
			struct.parseFileStruct(file.getName());

			// 协议文件生成器
			Generator generator = Factory.getGenerator(config, struct);
			generator.generate();
		}
	}

}
