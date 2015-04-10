package com.ep;

import com.ep.config.ConfigInfo;

/**
 * 
 * @author zhaohui
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		String configXml = "config.xml";
		String progenXml = "progen.xml";

		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-c")) {
				configXml = args[i].split("=")[1];
			} else if (args[i].startsWith("-p")) {
				progenXml = args[i].split("=")[1];
			}
		}

		ConfigInfo config = new ConfigInfo();
		config.init(progenXml, configXml);

		ProtoGen.genFile(config);
	}
}
