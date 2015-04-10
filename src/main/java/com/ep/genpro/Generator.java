package com.ep.genpro;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.ep.config.Bean;
import com.ep.config.ConfigInfo;
import com.ep.config.Protocol;
import com.ep.config.ProtocolInfo;

public abstract class Generator {

	public static final String DEFAULT_ENCODE = "UTF-8";//默认编码格式

	protected Struct struct;
	protected ConfigInfo config;

	protected PGenerator pGenerator;//bean生成器
	protected BeanGenerator beanGenerator;//协议生成器

	public static Set<ProtocolInfo> sets = new HashSet<ProtocolInfo>();
	public static Set<Protocol> psets = new HashSet<Protocol>();

	/**
	 * 生成bean以及协议类
	 * @return
	 */
	public Set<ProtocolInfo> generate() {
		Set<ProtocolInfo> set = new HashSet<ProtocolInfo>();
		makeDirs();
		for (Entry<String, Bean> e : struct.getBeanStructs().entrySet()) {
			beanGenerator.generateBean(e.getKey(), e.getValue());
		}
		for (Entry<String, Protocol> e : struct.getProtocolStructs().entrySet()) {
			pGenerator.generateProtocol(e.getKey(), e.getValue());
			ProtocolInfo info = new ProtocolInfo(e.getKey());
			info.setTypeId(e.getValue().getType());
			info.setMaxSize(e.getValue().getMaxSize());
			set.add(info);

			psets.add(e.getValue());
		}
		sets.addAll(set);
		return set;
	}

	/**
	 * 创建目录
	 */
	protected abstract void makeDirs();

	protected String formatPath(String root, String namespace) {
		if (root.endsWith("/")) {
			return root + namespace;
		} else {
			return root + "/" + namespace;
		}
	}

	protected String formatPath(String filepath) {
		filepath = filepath.replaceAll("//", "/");
		if (filepath.endsWith("/")) {
			return filepath.substring(0, filepath.length() - 1);
		} else {
			return filepath;
		}
	}

	public ConfigInfo getConfig() {
		return config;
	}

	public void setConfig(ConfigInfo config) {
		this.config = config;
	}

	public Struct getStruct() {
		return struct;
	}

	public void setStruct(Struct struct) {
		this.struct = struct;
	}

}
