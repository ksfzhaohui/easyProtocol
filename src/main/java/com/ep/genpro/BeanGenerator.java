package com.ep.genpro;

import com.ep.config.Bean;
import com.ep.config.ConfigInfo;

public abstract class BeanGenerator {

	protected ConfigInfo config;
	protected String encode;
	protected String endfix;
	protected IGeneratorHelper generatorHelper;

	public abstract void generateBean(String beanName, Bean b);

	public BeanGenerator(ConfigInfo config, String encode, String endfix) {
		this.config = config;
		this.encode = encode;
		this.endfix = endfix;
	}

}
