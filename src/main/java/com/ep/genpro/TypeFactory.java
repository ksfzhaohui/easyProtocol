package com.ep.genpro;

import java.util.Map;
import java.util.TreeMap;

import com.ep.config.Bean;

public abstract class TypeFactory {
	/**
	 * 从xml定义类型到编程语言基本类型的映射
	 */
	protected Map<String, String> declare2Type = new TreeMap<String, String>();

	/**
	 * 基本类型从编程语言基本类型到Type的映射
	 */
	protected Map<String, Type> innerTypes = new TreeMap<String, Type>();

	/**
	 * 容器类型从编程语言基本类型到Type的映射
	 */
	protected Map<String, Type> containerTypes = new TreeMap<String, Type>();

	protected Type beanType = null;

	/**
	 * 从xml定义类型到编程语言类型的查询
	 * 
	 * @param type
	 * @return
	 */
	public String getLanguageType(String type) {
		return declare2Type.get(type);
	}

	/**
	 * 获取容器类型 仅支持 list set map String
	 * 
	 * @param name
	 * @return
	 */
	public Type getContainerType(String name) {
		return containerTypes.get(name);
	}

	/**
	 * 获取内建类型 仅支持 short int long boolean float double
	 * 
	 * @param name
	 * @return
	 */
	public Type getInnerType(String name) {
		return innerTypes.get(name);
	}

	/**
	 * 获取原生类型 innerType + containerType
	 * 
	 * @param name
	 * @return
	 */
	public Type getOrignType(String name) {
		if (innerTypes.containsKey(name))
			return innerTypes.get(name);
		else
			return containerTypes.get(name);
	}

	/**
	 * 获取所有类型
	 * 
	 * @param name
	 * @param bean
	 * @return
	 */
	public Type getAllType(String name, Bean bean) {
		Type r = getOrignType(name);
		if (null == r) {
			r = beanType;
			beanType.decorateBean(bean);
		}
		return r;
	}

	/**
	 * 获取所有类型
	 * 
	 * @param name
	 * @param variable
	 * @return
	 */
	public Type getAllType(String name, Variable variable) {
		Type r = getOrignType(name);
		if (null == r) {
			r = beanType;
			beanType.decorateBean(variable);
		}
		return r;
	}

	/**
	 * 判断判断编程语言类型是否是内建类型
	 * 
	 * @param name
	 * @return
	 */
	public boolean isInnerType(String name) {
		return innerTypes.containsKey(name);
	}

	/**
	 * 判断编程语言类型是否是原生类型
	 * 
	 * @param name
	 * @return
	 */
	public boolean isOrignType(String name) {
		return declare2Type.values().contains(name);
	}

	/**
	 * 获取装箱名称 主要用于内建类型
	 * 
	 * @param name
	 * @return
	 */
	public String getBoxName(String name) {
		Type t = getOrignType(name);
		return null == t ? name : t.getBoxName();
	}

}
