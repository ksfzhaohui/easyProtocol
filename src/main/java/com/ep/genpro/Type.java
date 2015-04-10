package com.ep.genpro;

import com.ep.config.Bean;

/**
 * @notice declareName xml文件中定义的类型名称 int list string bool octets
 * @notice typeName 编程语言中和declareName对应的数据类型 int List String boolean Octets
 * @notice boxType  编程语言的装箱类型 Integer List String Boolean Octets
 * 
 * @author zhaohui
 */
public abstract class Type {
	protected final String typeName;

	public Type(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 获取类型名
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 获取装箱名
	 * @return
	 */
	public abstract String getBoxName();

	/**
	 * 获取类型全名 主要用于容器类型
	 * @param key
	 * @param value
	 * @return
	 */
	public String getFullTypeName(String key, String value) {
		return typeName;
	}

	/**
	 * 判定给定的初始值是否合法
	 * @param initial
	 * @return
	 */
	public boolean isInitialValid(String initial) {
		return false;
	}

	public abstract String formatInitialExpression(String initial, String key,
			String value, String varName);

	public String formatInitialExpressionP(String initial, String key,
			String value) {
		return formatInitialExpression(initial, key, value, null);
	}

	public abstract String[] formatMarshal(String parameterName,
			String keyType, String valueType);

	public abstract String[] formatUnmarshal(String paraneterName,
			String keyType, String valueType, String varName);

	public String formatEquals(String leftValue, String rightValue,
			boolean equal) {
		if (equal) {
			return leftValue + " == " + rightValue;
		} else {
			return leftValue + " != " + rightValue;
		}
	}

	public void decorateBean(Variable variable) {
	}

	public void decorateBean(Bean bean) {
	}

	public String formatHashCode(String value) {
		return value;
	}

	public String[] getImport() {
		return null;
	}

	public String formatTostring(String value) {
		return value;
	}
}
