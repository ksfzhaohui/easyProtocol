package com.ep.stream;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class Octets implements Cloneable, Comparable<Object>, Serializable {
	private static final long serialVersionUID = -6683312389154167084L;
	private static final int DEFAULT_SIZE = 128;
	private static String DEFAULT_CHARSET = "ISO-8859-1";
	private byte[] buffer = null;
	private int count = 0;

	private byte[] roundup(int size) {
		int capacity = 16;
		while (size > capacity)
			capacity <<= 1;
		return new byte[capacity];
	}

	public void reserve(int size) {
		if (buffer == null) {
			buffer = roundup(size);
		} else if (size > buffer.length) {
			byte[] tmp = roundup(size);
			System.arraycopy(buffer, 0, tmp, 0, count);
			buffer = tmp;
		}
	}

	public Octets replace(byte[] data, int pos, int size) {
		reserve(size);
		System.arraycopy(data, pos, buffer, 0, size);
		count = size;
		return this;
	}

	public Octets replace(Octets data, int pos, int size) {
		return replace(data.buffer, pos, size);
	}

	public Octets replace(byte[] data) {
		return replace(data, 0, data.length);
	}

	public Octets replace(Octets data) {
		return replace(data.buffer, 0, data.count);
	}

	public Octets() {
		reserve(DEFAULT_SIZE);
	}

	public Octets(int size) {
		reserve(size);
	}

	public Octets(Octets rhs) {
		replace(rhs);
	}

	public Octets(byte[] rhs) {
		replace(rhs);
	}

	private Octets(byte[] bytes, int length) {
		this.buffer = bytes;
		this.count = length;
	}

	public static Octets wrap(byte[] bytes, int length) {
		return new Octets(bytes, length);
	}

	public static Octets wrap(byte[] bytes) {
		return wrap(bytes, bytes.length);
	}

	public static Octets wrap(String str, String encoding) {
		try {
			return wrap(str.getBytes(encoding));
		} catch (java.io.UnsupportedEncodingException x) {
			throw new RuntimeException(x);
		}
	}

	public Octets(byte[] rhs, int pos, int size) {
		replace(rhs, pos, size);
	}

	public Octets(Octets rhs, int pos, int size) {
		replace(rhs, pos, size);
	}

	public Octets resize(int size) {
		reserve(size);
		count = size;
		return this;
	}

	public int size() {
		return count;
	}

	public int capacity() {
		return buffer.length;
	}

	public Octets clear() {
		count = 0;
		return this;
	}

	public Octets swap(Octets rhs) {
		int size = count;
		count = rhs.count;
		rhs.count = size;
		byte[] tmp = rhs.buffer;
		rhs.buffer = buffer;
		buffer = tmp;
		return this;
	}

	public Octets push_back(byte data) {
		reserve(count + 1);
		buffer[count++] = data;
		return this;
	}

	public Octets erase(int from, int to) {
		// System.arraycopy(buffer, to, buffer, from, count -= to - from);
		System.arraycopy(buffer, to, buffer, from, count - to);
		count -= to - from;
		return this;
	}

	public Octets insert(int from, byte[] data, int pos, int size) {
		reserve(count + size);
		System.arraycopy(buffer, from, buffer, from + size, count - from);
		System.arraycopy(data, pos, buffer, from, size);
		count += size;
		return this;
	}

	public Octets insert(int from, Octets data, int pos, int size) {
		return insert(from, data.buffer, pos, size);
	}

	public Octets insert(int from, byte[] data) {
		return insert(from, data, 0, data.length);
	}

	public Octets insert(int from, Octets data) {
		return insert(from, data.buffer, 0, data.size());
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public int compareTo(Octets rhs) {
		// compare count first
		int c = count - rhs.count;
		if (c != 0)
			return c;
		byte[] v1 = buffer;
		byte[] v2 = rhs.buffer;
		for (int i = 0; i < count; i++) {
			int v = v1[i] - v2[i];
			if (v != 0)
				return v;
		}
		return 0;
	}

	public int compareTo(Object o) {
		return compareTo((Octets) o);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Octets)) {
			return false;
		}
		return compareTo(o) == 0;
	}

	public int hashCode() {
		// same as java.util.Arrays.java
		if (buffer == null)
			return 0;
		int result = 1;
		for (int i = 0; i < count; i++)
			result = 31 * result + buffer[i];
		return result;
	}

	// for debug
	public String toString() {
		return "octets.size=" + count;
	}

	public byte[] getBytes() {
		byte[] tmp = new byte[count];
		System.arraycopy(buffer, 0, tmp, 0, count);
		return tmp;
	}

	public byte[] array() {
		return buffer;
	}

	public byte getByte(int pos) {
		return buffer[pos];
	}

	public void setByte(int pos, byte b) {
		buffer[pos] = b;
	}

	public ByteBuffer getByteBuffer(int off, int size) {
		return ByteBuffer.wrap(buffer, off, size);
	}

	public ByteBuffer getByteBuffer(int off) {
		return ByteBuffer.wrap(buffer, off, count - off);
	}

	public ByteBuffer getByteBuffer() {
		return ByteBuffer.wrap(buffer, 0, count);
	}

	public String getString() throws Exception {
		return new String(buffer, 0, count, DEFAULT_CHARSET);
	}

	public String getString(String encoding) {
		try {
			return new String(buffer, 0, count, encoding);
		} catch (java.io.UnsupportedEncodingException x) {
			throw new RuntimeException(x);
		}
	}

	public void setString(String str) throws Exception {
		buffer = str.getBytes(DEFAULT_CHARSET);
		count = buffer.length;
	}

	public void dump() {
		for (int i = 0; i < size(); i++)
			System.out.printf("%02x ", buffer[i]);
		System.out.printf("\n");
	}

	static public void setDefaultCharset(String name) {
		DEFAULT_CHARSET = name;
	}

}
