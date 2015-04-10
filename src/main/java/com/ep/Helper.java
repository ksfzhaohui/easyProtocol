package com.ep;

/**
 * 
 * @author zhaohui
 *
 */
public class Helper {

	/**
	 * 改变字符串第一个字符的大小写
	 * @param name   名称
	 * @param upper  true大写，false小写
	 * @return
	 */
	public static String changeFirstCharName(String name, boolean upper) {
		String r = null;
		if (!name.contains(".")) {
			r = change(name, upper);
		} else {
			String[] names = name.split("\\.");
			String shortName = Helper.change(names[names.length - 1], upper);
			r = name.substring(0, name.lastIndexOf(".") + 1) + shortName;
		}
		return r;
	}

	private static String change(String name, boolean upper) {
		String r = null;
		if (upper) {
			r = name.substring(0, 1).toUpperCase();
		} else {
			r = name.substring(0, 1).toLowerCase();
		}
		if (name.length() > 1) {
			r += name.substring(1, name.length());
		}
		return r;
	}
}
