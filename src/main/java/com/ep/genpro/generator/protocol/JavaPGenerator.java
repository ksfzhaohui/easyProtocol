package com.ep.genpro.generator.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.ep.config.Bean;
import com.ep.config.ConfigInfo;
import com.ep.config.Protocol;
import com.ep.genpro.PGenerator;
import com.ep.genpro.Variable;
import com.ep.genpro.helper.JavaGeneratorHelper;
import com.ep.genpro.sign.JavaSign;

public class JavaPGenerator extends PGenerator {

	private final List<String> reserveCodes = new ArrayList<String>();

	public JavaPGenerator(ConfigInfo config, String encode, String endfix) {
		super(config, encode, endfix);
		this.generatorHelper = new JavaGeneratorHelper();
	}

	@Override
	public void generateProtocol(String protocolName, Protocol protocol) {
		String name = protocol.getName();
		String fullName = config.getProcRoot() + "\\" + name.replace('.', '\\')
				+ endfix;
		File protocolFile = new File(formatPath(fullName));
		PrintStream ps = null;
		reserveCodes.clear();
		if (protocolFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(
								formatPath(fullName)), encode));
				boolean save = false;
				String code = null;
				while ((code = reader.readLine()) != null) {
					if (code.equals(JavaSign.getInstance().Protocol_Reserve_End))
						break;
					if (save)
						reserveCodes.add(code);
					if (code.equals(JavaSign.getInstance().Protocol_Reserve_Begin))
						save = true;
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			protocolFile.delete();
		}
		try {
			protocolFile.createNewFile();
			ps = new PrintStream(new FileOutputStream(protocolFile), false,
					encode);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(fullName + e);
		}
		generatePClass(ps, protocol);
		ps.close();
	}

	private void generatePClass(PrintStream out, Protocol p) {
		// package
		out.println(JavaSign.getInstance().Package
				+ p.getName().substring(0, p.getName().lastIndexOf(".")) + ";");
		out.println();
		// import
		generatorHelper.declareImport(out, p);
		out.println();
		out.println(JavaSign.getInstance().DECLARE_NO_MODIFY);
		declareClass(out, p);
		out.println("{");
		// type
		declareType(out, p);
		out.println();
		// maxSize
		declareMaxSize(out, p);
		out.println();
		// toByte
		declareToByte(out, p);
		out.println();
		// parseFrom
		declareParseFrom(out, p);
		out.println();
		// members
		for (Variable v : p.getVariables())
			generatorHelper.implementClassMember(null, out, v);
		out.println();
		// constructor
		generatorHelper.implementClassConstructor(null, out, p);
		out.println();
		// marshal
		generatorHelper.implementCalssMarshal(null, out, p);
		out.println();
		// unmarshal
		generatorHelper.implementCalssUnmarshal(null, out, p);
		out.println();
		// equal
		generatorHelper.implementClassEquals(out, p);
		out.println();
		// hashCode
		generatorHelper.implementClassHashCode(null, out, p);
		out.println();
		// toString
		generatorHelper.implementClassToString(null, out, p);
		// toString0
		generatorHelper.implementClassToString0(null, out, p);
		out.println("}");
	}

	private void declareClass(PrintStream out, Bean b) {
		if (null != b.getComment()) {
			out.println("/**");
			out.println(" *  " + b.getComment());
			out.println(" */");
		}
		out.println(JavaSign.getInstance().Public
				+ JavaSign.getInstance().Class + shortName(b.getName()) + " "
				+ JavaSign.getInstance().Extends
				+ JavaSign.getInstance().ObjPath[0] + " "
				+ JavaSign.getInstance().Implements
				+ JavaSign.getInstance().ObjPath[1]);
	}

	private void declareType(PrintStream out, Protocol p) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Public + JavaSign.getInstance().Static
				+ JavaSign.getInstance().Final + "int PROTOCOL_TYPE = "
				+ p.getType() + ";");
		out.println();
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Public + "int getType()");
		out.println(JavaSign.getInstance().Tab[1] + "{");
		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return + "PROTOCOL_TYPE;");
		out.println(JavaSign.getInstance().Tab[1] + "}");
	}

	private void declareMaxSize(PrintStream out, Protocol p) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Public + "int getMaxSize()");
		out.println(JavaSign.getInstance().Tab[1] + "{");
		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return + p.getMaxSize() + ";");
		out.println(JavaSign.getInstance().Tab[1] + "}");
	}

	private void declareToByte(PrintStream out, Protocol p) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Public + "byte[] toByte()");
		out.println(JavaSign.getInstance().Tab[1] + "{");
		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return
				+ "new OctetsStream().marshal(this).getBytes();");
		out.println(JavaSign.getInstance().Tab[1] + "}");
	}

	private void declareParseFrom(PrintStream out, Protocol p) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Public
				+ "void parseFrom(byte[] buff) throws MarshalException");
		out.println(JavaSign.getInstance().Tab[1] + "{");
		out.println(JavaSign.getInstance().Tab[2]
				+ "unmarshal(OctetsStream.wrap(new "
				+ JavaSign.getInstance().ObjPath[4] + "(buff)));");
		out.println(JavaSign.getInstance().Tab[1] + "}");
	}

	private String formatPath(String filepath) {
		return filepath.replaceAll("//", "/").replaceAll("\\\\", "/");
	}

	private String shortName(String fullName) {
		return fullName.substring(fullName.lastIndexOf(".") + 1,
				fullName.length());
	}

}
