package com.ep.net.packet;

import com.ep.stream.MarshalException;
import com.ep.stream.OctetsStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

//This File Is Generated By DB Generator, Do Not Modify.
/**
 *  响应-协议1100(测试)
 */
public class Protocol11100 extends com.ep.stream.Protocol implements com.ep.stream.Marshal
{
	public static final int PROTOCOL_TYPE = 11100;

	@Override
	public int getType()
	{
		return PROTOCOL_TYPE;
	}

	@Override
	public int getMaxSize()
	{
		return 65535;
	}

	@Override
	public byte[] toByte()
	{
		return new OctetsStream().marshal(this).getBytes();
	}

	@Override
	public void parseFrom(byte[] buff) throws MarshalException
	{
		unmarshal(OctetsStream.wrap(new com.ep.stream.Octets(buff)));
	}

	
	public com.ep.net.packet.bean.MBResponseBase respBase;
	// 玩家登陆状态（1第一关未完成，2第一关已完成但未获得主城，3已获得主城的老玩家）
	public int loginStage;
	// 性别
	public byte sex;
	// 性别
	public boolean param2;
	// 名称
	public String name;
	// 元宝
	public float ingot;
	// 金币
	public double glod;
	// 经验
	public short exp;
	// 玩家id
	public long pid;
	// 玩家参数
	public Map<String, Integer> param1;
	// 出战武将列表
	public Set<com.ep.net.packet.bean.MBBattlePGeneral> battleGeneralSet;
	// 出战武将列表
	public List<com.ep.net.packet.bean.MBBattlePGeneral> battleGeneralVector;
	// 出战武将列表
	public List<com.ep.net.packet.bean.MBBattlePGeneral> battleGenerals;
	// 出战武将列表
	public com.ep.net.packet.bean.MBBattlePGeneral[] battleGeneralArray;

	public Protocol11100()
	{
		this.respBase = new com.ep.net.packet.bean.MBResponseBase();
		this.loginStage = 0;
		this.sex = (byte)0;
		this.param2 = true;
		this.name = "";
		this.ingot = 0.0f;
		this.glod = 0.0;
		this.exp = (short)0;
		this.pid = 0l;
		this.param1 = new java.util.HashMap<String, Integer>();
		this.battleGeneralSet = new java.util.HashSet<com.ep.net.packet.bean.MBBattlePGeneral>();
		this.battleGeneralVector = new java.util.ArrayList<com.ep.net.packet.bean.MBBattlePGeneral>();
		this.battleGenerals = new java.util.LinkedList<com.ep.net.packet.bean.MBBattlePGeneral>();
		this.battleGeneralArray = new com.ep.net.packet.bean.MBBattlePGeneral[0];
	}

	public Protocol11100(int _loginStage_, byte _sex_, boolean _param2_, String _name_, float _ingot_, double _glod_, short _exp_, long _pid_)
	{
		this.respBase = new com.ep.net.packet.bean.MBResponseBase();
		this.loginStage = _loginStage_;
		this.sex = _sex_;
		this.param2 = _param2_;
		this.name = _name_;
		this.ingot = _ingot_;
		this.glod = _glod_;
		this.exp = _exp_;
		this.pid = _pid_;
		this.param1 = new java.util.HashMap<String, Integer>();
		this.battleGeneralSet = new java.util.HashSet<com.ep.net.packet.bean.MBBattlePGeneral>();
		this.battleGeneralVector = new java.util.ArrayList<com.ep.net.packet.bean.MBBattlePGeneral>();
		this.battleGenerals = new java.util.LinkedList<com.ep.net.packet.bean.MBBattlePGeneral>();
		this.battleGeneralArray = new com.ep.net.packet.bean.MBBattlePGeneral[0];
	}

	public Protocol11100(com.ep.net.packet.bean.MBResponseBase _respBase_, int _loginStage_, byte _sex_, boolean _param2_, String _name_, float _ingot_, double _glod_, short _exp_, long _pid_, Map<String, Integer> _param1_, Set<com.ep.net.packet.bean.MBBattlePGeneral> _battleGeneralSet_, List<com.ep.net.packet.bean.MBBattlePGeneral> _battleGeneralVector_, List<com.ep.net.packet.bean.MBBattlePGeneral> _battleGenerals_, com.ep.net.packet.bean.MBBattlePGeneral[] _battleGeneralArray_)
	{
		this.respBase = _respBase_;
		this.loginStage = _loginStage_;
		this.sex = _sex_;
		this.param2 = _param2_;
		this.name = _name_;
		this.ingot = _ingot_;
		this.glod = _glod_;
		this.exp = _exp_;
		this.pid = _pid_;
		this.param1 = _param1_;
		this.battleGeneralSet = _battleGeneralSet_;
		this.battleGeneralVector = _battleGeneralVector_;
		this.battleGenerals = _battleGenerals_;
		this.battleGeneralArray = _battleGeneralArray_;
	}


	@Override
	public OctetsStream marshal(OctetsStream _os_)
	{
		respBase.marshal(_os_);
		_os_.marshal(loginStage);
		_os_.marshal(sex);
		_os_.marshal(param2);
		_os_.marshal(name,"UTF-8");
		_os_.marshal(ingot);
		_os_.marshal(glod);
		_os_.marshal(exp);
		_os_.marshal(pid);
		_os_.compact_uint32(param1.size());
		for(Entry<String , Integer> _e_ : param1.entrySet())
		{
			_os_.marshal(_e_.getKey(),"UTF-8");
			_os_.marshal(_e_.getValue());
		}
		_os_.compact_uint32(battleGeneralSet.size());
		for(com.ep.net.packet.bean.MBBattlePGeneral _v : battleGeneralSet)
		{
			_os_.marshal(_v);
		}
		_os_.compact_uint32(battleGeneralVector.size());
		for(com.ep.net.packet.bean.MBBattlePGeneral _v : battleGeneralVector)
		{
			_os_.marshal(_v);
		}
		_os_.compact_uint32(battleGenerals.size());
		for(com.ep.net.packet.bean.MBBattlePGeneral _v : battleGenerals)
		{
			_os_.marshal(_v);
		}
		_os_.compact_uint32(battleGeneralArray.length);
		for(com.ep.net.packet.bean.MBBattlePGeneral _v : battleGeneralArray)
		{
			_os_.marshal(_v);
		}
		return _os_;
	}

	@Override
	public OctetsStream unmarshal(OctetsStream _os_) throws MarshalException
	{
		respBase.unmarshal(_os_);
		loginStage = _os_.unmarshal_int();
		sex = _os_.unmarshal_byte();
		param2 = _os_.unmarshal_boolean();
		name = _os_.unmarshal_String("UTF-8");
		ingot = _os_.unmarshal_float();
		glod = _os_.unmarshal_double();
		exp = _os_.unmarshal_short();
		pid = _os_.unmarshal_long();
		for(int _i = _os_.uncompact_uint32(); _i > 0; --_i)
		{
			String _k_ = _os_.unmarshal_String("UTF-8");
			Integer _v_ = _os_.unmarshal_int();
			this.param1.put(_k_, _v_);
		}
		for(int _i = _os_.uncompact_uint32(); _i > 0; --_i)
		{
			com.ep.net.packet.bean.MBBattlePGeneral _v_ = new com.ep.net.packet.bean.MBBattlePGeneral();
			 _v_.unmarshal(_os_);
			this.battleGeneralSet.add(_v_);
		}
		for(int _i = _os_.uncompact_uint32(); _i > 0; --_i)
		{
			com.ep.net.packet.bean.MBBattlePGeneral _v_ = new com.ep.net.packet.bean.MBBattlePGeneral();
			 _v_.unmarshal(_os_);
			battleGeneralVector.add(_v_);
		}
		for(int _i = _os_.uncompact_uint32(); _i > 0; --_i)
		{
			com.ep.net.packet.bean.MBBattlePGeneral _v_ = new com.ep.net.packet.bean.MBBattlePGeneral();
			 _v_.unmarshal(_os_);
			battleGenerals.add(_v_);
		}
		battleGeneralArray = new com.ep.net.packet.bean.MBBattlePGeneral[_os_.uncompact_uint32()];
		for(int _i = battleGeneralArray.length; _i > 0; --_i)
		{
			com.ep.net.packet.bean.MBBattlePGeneral _v_ = new com.ep.net.packet.bean.MBBattlePGeneral();
			 _v_.unmarshal(_os_);
			battleGeneralArray[battleGeneralArray.length - _i] = _v_;
		}
		return _os_;
	}

	@Override
	public final boolean equals(Object _o_)
	{
		if(this == _o_)
			return true;
		if(!(_o_ instanceof com.ep.net.packet.Protocol11100))
			return false;
		com.ep.net.packet.Protocol11100 _o_t = (com.ep.net.packet.Protocol11100)_o_;
		if(!respBase.equals(_o_t.respBase))
			return false;
		if(loginStage != _o_t.loginStage)
			return false;
		if(sex != _o_t.sex)
			return false;
		if(param2 != _o_t.param2)
			return false;
		if(!name.equals(_o_t.name))
			return false;
		if(Float.floatToIntBits(ingot) != Float.floatToIntBits(_o_t.ingot))
			return false;
		if(Double.doubleToLongBits(glod) != Double.doubleToLongBits(_o_t.glod))
			return false;
		if(exp != _o_t.exp)
			return false;
		if(pid != _o_t.pid)
			return false;
		if(!param1.equals(_o_t.param1))
			return false;
		if(!battleGeneralSet.equals(_o_t.battleGeneralSet))
			return false;
		if(!battleGeneralVector.equals(_o_t.battleGeneralVector))
			return false;
		if(!battleGenerals.equals(_o_t.battleGenerals))
			return false;
		if(!java.util.Arrays.equals(battleGeneralArray,_o_t.battleGeneralArray))
			return false;
		return true;
	}

	@Override
	public final int hashCode()
	{
		int _h_ = 0;
		_h_ = 31 * _h_ + respBase.hashCode();
		_h_ = 31 * _h_ + loginStage;
		_h_ = 31 * _h_ + sex;
		_h_ = 31 * _h_ + (param2 ? 1231 : 1237);
		_h_ = 31 * _h_ + name.hashCode();
		_h_ = 31 * _h_ + Float.floatToIntBits(ingot);
		_h_ = 31 * _h_ + (int)(Double.doubleToLongBits(glod) ^ ( Double.doubleToLongBits(glod) >>> 32));
		_h_ = 31 * _h_ + exp;
		_h_ = 31 * _h_ + (int)(pid ^ (pid >>> 32));
		_h_ = 31 * _h_ + param1.hashCode();
		_h_ = 31 * _h_ + battleGeneralSet.hashCode();
		_h_ = 31 * _h_ + battleGeneralVector.hashCode();
		_h_ = 31 * _h_ + battleGenerals.hashCode();
		_h_ = 31 * _h_ + java.util.Arrays.hashCode(battleGeneralArray);
		return _h_;
	}

	@Override
	public final String toString()
	{
		return toString(0);
	}

	public final String toString(int depth) {
		depth++;
		StringBuilder buffer = new StringBuilder();
		buffer.append('\n');
		for (int i = 0; i < depth - 1; i++){
			buffer.append('\t');
		}
		buffer.append('{');
		buffer.append('\n');

		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("respBase").append(":");
		buffer.append(respBase.toString(depth));
		buffer.append("\n");

		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("loginStage").append(": ");
		buffer.append(loginStage);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("sex").append(": ");
		buffer.append(sex);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("param2").append(": ");
		buffer.append(param2);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("name").append(": ");
		buffer.append(name);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("ingot").append(": ");
		buffer.append(ingot);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("glod").append(": ");
		buffer.append(glod);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("exp").append(": ");
		buffer.append(exp);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("pid").append(": ");
		buffer.append(pid);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("param1").append(": ");
		buffer.append(param1);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("battleGeneralSet").append(": ");
		buffer.append(battleGeneralSet);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("battleGeneralVector").append(": ");
		buffer.append(battleGeneralVector);
		buffer.append(';');
		buffer.append("\n");


		for (int i = 0; i < depth; i++) {
			buffer.append('\t');
		}
		buffer.append("battleGenerals: ");
		buffer.append('\n');
		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append('{');

		for ( com.ep.net.packet.bean.MBBattlePGeneral mb : battleGenerals ) {
			buffer.append(mb).append("\n,");
		}
		buffer.append("\n");
		for ( int i = 0 ; i < depth ; i++ ) {
			buffer.append('\t');
		}
		buffer.append('}');
		buffer.append("\n");

		for (int i = 0; i < depth; i++){
			buffer.append('\t');
		}
		buffer.append("battleGeneralArray").append(": ");
		buffer.append(battleGeneralArray);
		buffer.append(';');
		buffer.append("\n");


		for ( int i = 0 ; i < depth-1 ; i++ ) {
			buffer.append('\t');
		}
		buffer.append('}');
		buffer.append(';');
		return buffer.toString();
	}
}