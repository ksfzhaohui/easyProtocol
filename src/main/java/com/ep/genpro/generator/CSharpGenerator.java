package com.ep.genpro.generator;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.ep.config.ConfigInfo;
import com.ep.genpro.Generator;
import com.ep.genpro.Struct;
import com.ep.genpro.generator.bean.CSharpBeanGenerator;
import com.ep.genpro.generator.protocol.CSharpPGenerator;
import com.ep.genpro.sign.CSharpSign;

public class CSharpGenerator extends Generator {

	public CSharpGenerator(Struct struct, ConfigInfo config) {
		this.config = config;
		this.struct = struct;

		String encode = config.getEncode();
		String endfix = config.getEndfix();

		CSharpSign.CharSet = config.getCharSet();
		this.beanGenerator = new CSharpBeanGenerator(config,
				null != encode ? encode : DEFAULT_ENCODE, endfix);
		this.pGenerator = new CSharpPGenerator(config, null != encode ? encode
				: DEFAULT_ENCODE, endfix);
	}

	@Override
	protected void makeDirs() {
		Set<String> nameset = new HashSet<String>();
		nameset.add(config.getProcRoot());
		nameset.add(config.getBeanRoot());
		for (String namespace : nameset) {
			File rootProtocolDir = new File(formatPath(namespace));
			if (!rootProtocolDir.exists()) {
				rootProtocolDir.mkdirs();
			}
		}
	}
}
