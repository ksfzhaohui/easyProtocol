package com.ep.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ep.net.packet.Protocol11100;
import com.ep.net.packet.bean.MBBattlePGeneral;
import com.ep.net.packet.bean.MBResponseBase;

public class Java2JavaTest {
	public static void main(String[] args) throws Exception {
		//		java2java();
		csharp2java();
	}

	public static void java2java() throws Exception {
		Protocol11100 p1 = new Protocol11100();
		p1.respBase = new MBResponseBase(1, "ok", 10000);
		p1.loginStage = 1;
		p1.sex = 0;
		p1.param2 = true;
		p1.name = "赵辉";
		p1.ingot = 1.1f;
		p1.glod = 2.1;
		p1.exp = 10;
		p1.pid = 1001;
		Map<String, Integer> p1Map = new HashMap<String, Integer>();
		p1Map.put("aa", 11);
		p1.param1 = p1Map;
		Set<MBBattlePGeneral> gset = new HashSet<MBBattlePGeneral>();
		gset.add(new MBBattlePGeneral(1, 11, false, "xxx1"));
		p1.battleGeneralSet = gset;
		List<MBBattlePGeneral> gvector = new ArrayList<MBBattlePGeneral>();
		gvector.add(new MBBattlePGeneral(2, 22, false, "xxx2"));
		p1.battleGeneralVector = gvector;
		List<MBBattlePGeneral> glist = new LinkedList<MBBattlePGeneral>();
		glist.add(new MBBattlePGeneral(3, 33, false, "xxx3"));
		p1.battleGenerals = glist;
		MBBattlePGeneral gArray[] = new MBBattlePGeneral[1];
		gArray[0] = new MBBattlePGeneral(4, 44, false, "xxx4");
		p1.battleGeneralArray = gArray;

		//转换成字节
		byte info[] = p1.toByte();

		//将字节写入文件,方便c#调用
		FileOutputStream io = new FileOutputStream(new File(
				"D:/C#workspace/easyProtocol/easyProtocol/javabuffer"));
		io.write(info);
		io.close();

		//转换成对象
		Protocol11100 p2 = new Protocol11100();
		p2.parseFrom(info);
		System.out.println(p2.toString());
	}

	public static void csharp2java() throws Exception {
		FileInputStream io = new FileInputStream(new File(
				"D:/C#workspace/easyProtocol/easyProtocol/csharpbuffer"));
		byte[] inOutb = new byte[io.available()];
		io.read(inOutb);
		Protocol11100 p2 = new Protocol11100();
		p2.parseFrom(inOutb);
		System.out.println(p2.toString());
		io.close();
	}

}
