package com.ep.stream;

public abstract class Protocol implements Marshal {

	/**
	 * 协议号
	 * @return
	 */
	public abstract int getType();

	/**
	 * 将字节流解析为对象
	 * @param os
	 * @throws MarshalException
	 */
	public abstract void parseFrom(byte[] os) throws MarshalException;

	/**
	 *  转换获得消息byte流
	 * @return
	 */
	public abstract byte[] toByte();

	/**
	 * 协议最大长度
	 * @return
	 */
	public abstract int getMaxSize();
}
