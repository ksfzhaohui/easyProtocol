package com.ep.genpro;

import com.ep.config.ConfigInfo;
import com.ep.config.Protocol;

public abstract class PGenerator {

	protected ConfigInfo config;
	protected String encode;
	protected String endfix;
	protected IGeneratorHelper generatorHelper;

	public abstract void generateProtocol(String protocolName, Protocol protocol);

	public PGenerator(ConfigInfo config, String encode, String endfix) {
		this.config = config;
		this.encode = encode;
		this.endfix = endfix;
	}
}
