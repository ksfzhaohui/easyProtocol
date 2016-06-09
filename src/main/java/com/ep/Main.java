package com.ep;

import com.ep.config.ConfigInfo;

/**
 * 
 * @author zhaohui
 * 
 */
public class Main {

	public static void main(String[] args) throws Exception {
		if (args == null || args.length == 0) {
			args = new String[2];
			args[0] = "D:\\easyProtocol-1.0.1-beta\\config.xml";
			args[1] = "D:\\easyProtocol-1.0.1-beta\\progen.xml";
		}

		ConfigInfo config = getConfig(args);
		config.init();

		ProtoGen.genFile(config);
	}

	private static ConfigInfo getConfig(String[] args) {
		if (args == null || args.length < 1) {
			throw new RuntimeException("error init param:[configXml,progenXml]");
		}
		if (args.length != 2) {
			throw new RuntimeException("error init param:[configXml,progenXml]");
		}
		ConfigInfo config = null;
		if (args.length == 2) {
			config = new ConfigInfo(args[0], args[1]);
		}
		return config;
	}
}
