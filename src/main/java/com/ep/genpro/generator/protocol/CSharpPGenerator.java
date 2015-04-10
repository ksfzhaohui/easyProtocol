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
import com.ep.genpro.helper.CSharpGeneratorHelper;
import com.ep.genpro.sign.CSharpSign;

public class CSharpPGenerator extends PGenerator {

	public CSharpPGenerator(ConfigInfo config, String encode, String endfix) {
		super(config, encode, endfix);
		this.generatorHelper = new CSharpGeneratorHelper();
	}

	private final List<String> reserveCodes = new ArrayList<String>();

	@Override
	public void generateProtocol(String protocolName, Protocol p) {
		String fileName = getLastName(p.getName());
		String fullName = config.getProcRoot() + "\\" + fileName + endfix;
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
					if (code.equals(CSharpSign.getInstance().Protocol_Reserve_End))
						break;
					if (save)
						reserveCodes.add(code);
					if (code.equals(CSharpSign.getInstance().Protocol_Reserve_Begin))
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
			throw new RuntimeException(e);
		}
		generatePClass(ps, p);
		ps.close();
	}

	private void generatePClass(PrintStream out, Protocol p) {
		// import
		generatorHelper.declareImport(out, p);

		out.println();
		out.println(CSharpSign.getInstance().DECLARE_NO_MODIFY);
		out.println();

		// namespace
		out.println(CSharpSign.getInstance().Package
				+ p.getName().substring(0, p.getName().lastIndexOf(".")));
		out.println("{");

		declareClass(out, p);
		out.println(CSharpSign.getInstance().Tab[0] + "{");

		// members
		for (Variable v : p.getVariables()) {
			generatorHelper.implementClassMember("", out, v);
		}
		out.println();

		generatorHelper.implementClassConstructor("", out, p);
		out.println();

		// marshal
		generatorHelper.implementCalssMarshal("", out, p);
		out.println();

		// unmarshal
		generatorHelper.implementCalssUnmarshal("", out, p);
		out.println();

		// hashCode
		generatorHelper.implementClassHashCode("", out, p);
		out.println();

		// toString
		generatorHelper.implementClassToString("", out, p);
		// toString0
		generatorHelper.implementClassToString0("", out, p);
		out.println();

		implementCalssToBytes("", out, p);
		out.println();

		implementCalssParseFrom("", out, p);
		out.println();

		// type
		declareType(out, p);
		out.println();
		//
		// maxSize
		declareMaxSize(out, p);
		out.println();

		out.println(CSharpSign.getInstance().Tab[0] + "}");// End of Protocol
		out.println();
		out.println("}");

	}

	private void declareClass(PrintStream out, Bean b) {
		if (null != b.getComment()) {
			out.println(CSharpSign.getInstance().Tab[0] + "/**");
			out.println(CSharpSign.getInstance().Tab[0] + " *  "
					+ b.getComment());
			out.println(CSharpSign.getInstance().Tab[0] + " */");
		}
		out.println(CSharpSign.getInstance().Tab[0]
				+ CSharpSign.getInstance().Public
				+ CSharpSign.getInstance().Class + shortName(b.getName()) + " "
				+ CSharpSign.getInstance().Implements
				+ CSharpSign.getInstance().Iprotocol + ", "
				+ CSharpSign.getInstance().Imarshal);
	}

	private void implementCalssToBytes(String prefix, PrintStream out, Bean b) {
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_TOBYTES);
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");

		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "return new OctetsStream().Marshal(this).GetBytes();");

		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");
	}

	private void implementCalssParseFrom(String prefix, PrintStream out, Bean b) {
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_PARSEFROM);
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");

		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "Unmarshal(new OctetsStream(new Octets(stream)));");

		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");
	}

	private void declareType(PrintStream out, Protocol p) {
		out.println(CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().Public
				+ CSharpSign.getInstance().Final + "int PROTOCOL_TYPE = "
				+ p.getType() + ";");
		out.println();
		out.println(CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().Public + "int GetType()");
		out.println(CSharpSign.getInstance().Tab[1] + "{");
		out.println(CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().Return + "PROTOCOL_TYPE;");
		out.println(CSharpSign.getInstance().Tab[1] + "}");
	}

	private void declareMaxSize(PrintStream out, Protocol p) {
		out.println(CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().Public + "int GetMaxSize()");
		out.println(CSharpSign.getInstance().Tab[1] + "{");
		out.println(CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().Return + p.getMaxSize() + ";");
		out.println(CSharpSign.getInstance().Tab[1] + "}");
	}

	private String formatPath(String filepath) {
		return filepath.replaceAll("//", "/").replaceAll("\\\\", "/");
	}

	private String getLastName(String fullName) {
		int index = fullName.lastIndexOf(".");
		return index >= 0 ? fullName.substring(index + 1) : fullName;
	}

	private String shortName(String fullName) {
		return fullName.substring(fullName.lastIndexOf(".") + 1,
				fullName.length());
	}

}
