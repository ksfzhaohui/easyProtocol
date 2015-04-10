package com.ep.genpro.generator;

import java.io.File;
import java.util.Set;

import com.ep.config.ConfigInfo;
import com.ep.genpro.Generator;
import com.ep.genpro.Struct;
import com.ep.genpro.generator.bean.JavaBeanGenerator;
import com.ep.genpro.generator.protocol.JavaPGenerator;
import com.ep.genpro.sign.JavaSign;

/**
 * java版解析器
 * 
 * @author zhaohui
 * 
 */
public class JavaGenerator extends Generator {

	public JavaGenerator(Struct struct, ConfigInfo config) {
		this.config = config;
		this.struct = struct;

		String encode = config.getEncode();
		String endfix = config.getEndfix();

		JavaSign.CharSet = config.getCharSet();
		this.beanGenerator = new JavaBeanGenerator(config,
				null != encode ? encode : DEFAULT_ENCODE, endfix);
		this.pGenerator = new JavaPGenerator(config, null != encode ? encode
				: DEFAULT_ENCODE, endfix);
	}

	@Override
	protected void makeDirs() {
		Set<String> namespaces = struct.getNamespaces();
		for (String namespace : namespaces) {
			if (!namespace.contains(".")) {
				File rootProtocolDir = new File(formatPath(
						config.getProcRoot(), namespace));
				if (!rootProtocolDir.exists()) {
					rootProtocolDir.mkdirs();
				}
				File rootProtocolBeanDir = new File(formatPath(
						config.getBeanRoot(), namespace));
				if (!rootProtocolBeanDir.exists()) {
					rootProtocolBeanDir.mkdirs();
				}
			} else {

				String[] dirs = namespace.split("\\.");
				// protocols
				StringBuilder sb = new StringBuilder();
				sb.append(config.getProcRoot() + "/");
				for (int i = 0; i != dirs.length; ++i) {
					if (dirs[i].equals(""))
						continue;
					sb.append(dirs[i]).append("/");
					File dir = new File(formatPath(sb.toString()));
					if (!dir.exists())
						dir.mkdirs();
				}
				// beans
				sb = new StringBuilder();
				sb.append(config.getBeanRoot() + "/");
				for (int i = 0; i != dirs.length; ++i) {
					if (dirs[i].equals(""))
						continue;
					sb.append(dirs[i]).append("/");
					File dir = new File(formatPath(sb.toString()));
					if (!dir.exists())
						dir.mkdirs();
				}
			}
		}
	}

}
