package com.ep.stream;

@SuppressWarnings("serial")
public class OctetsStream extends Octets {
	private static final int MAXSPARE = 16384;
	private int pos = 0;
	private int tranpos = 0;

	public OctetsStream() {
	}

	public OctetsStream(int size) {
		super(size);
	}

	public OctetsStream(Octets o) {
		super(o);
	}

	public static OctetsStream wrap(Octets o) {
		OctetsStream os = new OctetsStream();
		os.swap(o);
		return os;
	}

	public Object clone() {
		return (OctetsStream) super.clone();
	}

	public final boolean eos() {
		return pos == size();
	}

	public final int position(int pos) {
		this.pos = pos;
		return this.pos;
	}

	public final int position() {
		return pos;
	}

	public final int remain() {
		return size() - pos;
	}

	public OctetsStream marshal(byte x) {
		push_back(x);
		return this;
	}

	public OctetsStream marshal(boolean b) {
		push_back((byte) (b ? 1 : 0));
		return this;
	}

	public OctetsStream marshal(short x) {
		return marshal((byte) (x >> 8)).marshal((byte) (x));
	}

	public OctetsStream marshal(char x) {
		return marshal((byte) (x >> 8)).marshal((byte) (x));
	}

	public OctetsStream marshal(int x) {
		return marshal((byte) (x >> 24)).marshal((byte) (x >> 16))
				.marshal((byte) (x >> 8)).marshal((byte) (x));
	}

	public OctetsStream marshal(long x) {
		return marshal((byte) (x >> 56)).marshal((byte) (x >> 48))
				.marshal((byte) (x >> 40)).marshal((byte) (x >> 32))
				.marshal((byte) (x >> 24)).marshal((byte) (x >> 16))
				.marshal((byte) (x >> 8)).marshal((byte) (x));
	}

	public OctetsStream marshal(float x) {
		return marshal(Float.floatToRawIntBits(x));
	}

	public OctetsStream marshal(double x) {
		return marshal(Double.doubleToRawLongBits(x));
	}

	public OctetsStream compact_uint32(int x) {
		if (x < 0x40)
			return marshal((byte) x);
		else if (x < 0x4000)
			return marshal((short) (x | 0x8000));
		else if (x < 0x20000000)
			return marshal(x | 0xc0000000);
		marshal((byte) 0xe0);
		return marshal(x);
	}

	public OctetsStream compact_sint32(int x) {
		if (x >= 0) {
			if (x < 0x40)
				return marshal((byte) x);
			else if (x < 0x2000)
				return marshal((short) (x | 0x8000));
			else if (x < 0x10000000)
				return marshal(x | 0xc0000000);
			marshal((byte) 0xe0);
			return marshal(x);
		}
		if (-x > 0) {
			x = -x;
			if (x < 0x40)
				return marshal((byte) (x | 0x40));
			else if (x < 0x2000)
				return marshal((short) (x | 0xa000));
			else if (x < 0x10000000)
				return marshal(x | 0xd0000000);
			marshal((byte) 0xf0);
			return marshal(x);
		}
		marshal((byte) 0xf0);
		return marshal(x);
	}

	public OctetsStream marshal(Marshal m) {
		return m.marshal(this);
	}

	public OctetsStream marshal(Octets o) {
		compact_uint32(o.size());
		insert(size(), o);
		return this;
	}

	public OctetsStream marshal(String str, String charset) {
		try {
			marshal((charset == null) ? str.getBytes() : str.getBytes(charset));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	public OctetsStream Begin() {
		tranpos = pos;
		return this;
	}

	public OctetsStream Rollback() {
		pos = tranpos;
		return this;
	}

	public OctetsStream Commit() {
		if (pos >= MAXSPARE) {
			erase(0, pos);
			pos = 0;
		}
		return this;
	}

	public byte unmarshal_byte() throws MarshalException {
		if (pos + 1 > size())
			throw new MarshalException();
		return getByte(pos++);
	}

	public boolean unmarshal_boolean() throws MarshalException {
		return unmarshal_byte() == 1;
	}

	public short unmarshal_short() throws MarshalException {
		if (pos + 2 > size())
			throw new MarshalException();
		byte b0 = getByte(pos++);
		byte b1 = getByte(pos++);
		return (short) ((b0 << 8) | (b1 & 0xff));
	}

	public char unmarshal_char() throws MarshalException {
		if (pos + 2 > size())
			throw new MarshalException();
		byte b0 = getByte(pos++);
		byte b1 = getByte(pos++);
		return (char) ((b0 << 8) | (b1 & 0xff));
	}

	public int unmarshal_int() throws MarshalException {
		if (pos + 4 > size())
			throw new MarshalException();
		byte b0 = getByte(pos++);
		byte b1 = getByte(pos++);
		byte b2 = getByte(pos++);
		byte b3 = getByte(pos++);
		return (int) ((((b0 & 0xff) << 24) | ((b1 & 0xff) << 16)
				| ((b2 & 0xff) << 8) | ((b3 & 0xff) << 0)));
	}

	public long unmarshal_long() throws MarshalException {
		if (pos + 8 > size())
			throw new MarshalException();
		byte b0 = getByte(pos++);
		byte b1 = getByte(pos++);
		byte b2 = getByte(pos++);
		byte b3 = getByte(pos++);
		byte b4 = getByte(pos++);
		byte b5 = getByte(pos++);
		byte b6 = getByte(pos++);
		byte b7 = getByte(pos++);
		return ((((long) b0 & 0xff) << 56) | (((long) b1 & 0xff) << 48)
				| (((long) b2 & 0xff) << 40) | (((long) b3 & 0xff) << 32)
				| (((long) b4 & 0xff) << 24) | (((long) b5 & 0xff) << 16)
				| (((long) b6 & 0xff) << 8) | (((long) b7 & 0xff) << 0));
	}

	public float unmarshal_float() throws MarshalException {
		return Float.intBitsToFloat(unmarshal_int());
	}

	public double unmarshal_double() throws MarshalException {
		return Double.longBitsToDouble(unmarshal_long());
	}

	public int uncompact_uint32() throws MarshalException {
		if (pos == size())
			throw new MarshalException();
		switch (getByte(pos) & 0xe0) {
		case 0xe0:
			unmarshal_byte();
			return unmarshal_int();
		case 0xc0:
			return unmarshal_int() & ~0xc0000000;
		case 0xa0:
		case 0x80:
			return unmarshal_short() & ~(short) 0x8000;
		}
		return unmarshal_byte();
	}

	public int uncompact_sint32() throws MarshalException {
		if (pos == size())
			throw new MarshalException();
		switch (getByte(pos) & 0xf0) {
		case 0xf0:
			unmarshal_byte();
			return -unmarshal_int();
		case 0xe0:
			unmarshal_byte();
			return unmarshal_int();
		case 0xd0:
			return -(unmarshal_int() & ~0xd0000000);
		case 0xc0:
			return unmarshal_int() & ~0xc0000000;
		case 0xb0:
		case 0xa0:
			return -(unmarshal_short() & ~(short) 0xa000);
		case 0x90:
		case 0x80:
			return unmarshal_short() & ~(short) 0x8000;
		case 0x70:
		case 0x60:
		case 0x50:
		case 0x40:
			return -(unmarshal_byte() & ~0x40);
		}
		return unmarshal_byte();
	}

	public Octets unmarshal_Octets() throws MarshalException {
		int size = uncompact_uint32();
		if (pos + size > size())
			throw new MarshalException();
		Octets o = new Octets(this, pos, size);
		pos += size;
		return o;
	}

	public byte[] unmarshal_bytes() throws MarshalException {
		int size = uncompact_uint32();
		if (pos + size > size())
			throw new MarshalException();
		byte[] copy = new byte[size];
		System.arraycopy(array(), pos, copy, 0, size);
		pos += size;
		return copy;
	}

	public OctetsStream marshal(byte[] bytes) {
		compact_uint32(bytes.length);
		insert(size(), bytes);
		return this;
	}

	public OctetsStream unmarshal(Octets os) throws MarshalException {
		int size = uncompact_uint32();
		if (pos + size > size())
			throw new MarshalException();
		os.replace(this, pos, size);
		pos += size;
		return this;
	}

	public String unmarshal_String(String charset) throws MarshalException {
		try {
			int size = uncompact_uint32();
			if (pos + size > size())
				throw new MarshalException();
			int cur = pos;
			pos += size;
			return (charset == null) ? new String(array(), cur, size)
					: new String(array(), cur, size, charset);
		} catch (Exception e) {
			throw new MarshalException();
		}
	}

	public OctetsStream unmarshal(Marshal m) throws MarshalException {
		return m.unmarshal(this);
	}

}
