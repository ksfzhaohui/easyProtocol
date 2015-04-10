package com.ep.genpro.generator.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import com.ep.config.Bean;
import com.ep.config.ConfigInfo;
import com.ep.genpro.BeanGenerator;
import com.ep.genpro.Variable;
import com.ep.genpro.helper.CSharpGeneratorHelper;
import com.ep.genpro.sign.CSharpSign;

public class CSharpBeanGenerator extends BeanGenerator {

	public CSharpBeanGenerator(ConfigInfo config, String encode, String endfix) {
		super(config, encode, endfix);
		this.generatorHelper = new CSharpGeneratorHelper();
	}

	protected String getLastName(String fullName) {
		int index = fullName.lastIndexOf(".");
		return index >= 0 ? fullName.substring(index + 1) : fullName;
	}

	protected String formatComment(String filepath) {
		return filepath.replaceAll("//", "").replaceAll("\\\\", "/");
	}

	protected String formatPath(String filepath) {
		return filepath.replaceAll("//", "/").replaceAll("\\\\", "/");
	}

	public static Set<Bean> bsets = new HashSet<Bean>();

	@Override
	public void generateBean(String beanName, Bean b) {
		String fileName = getLastName(b.getName());
		String fullName = config.getBeanRoot() + "\\" + fileName + endfix;
		File beanFile = new File(formatPath(fullName));
		PrintStream ps = null;
		if (beanFile.exists()) {
			beanFile.delete();
		}
		try {
			beanFile.createNewFile();
			ps = new PrintStream(new FileOutputStream(beanFile), false, encode);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		generateBeanClass(ps, b);
		ps.close();

	}

	private void generateBeanClass(PrintStream out, Bean b) {
		bsets.add(b);

		// import
		generatorHelper.declareImport(out, b);
		out.println();
		out.println(CSharpSign.getInstance().DECLARE_NO_MODIFY);
		out.println();

		// namespace
		out.println(CSharpSign.getInstance().Package
				+ b.getName().substring(0, b.getName().lastIndexOf(".")));
		out.println("{");

		declareClass(out, b);
		out.println(CSharpSign.getInstance().Tab[0] + "{");
		// members
		for (Variable v : b.getVariables()) {
			generatorHelper.implementClassMember("", out, v);
		}
		out.println();
		// constructor
		generatorHelper.implementClassConstructor("", out, b);
		out.println();
		// marshal
		generatorHelper.implementCalssMarshal("", out, b);
		out.println();
		// unmarshal
		generatorHelper.implementCalssUnmarshal("", out, b);
		out.println();
		// equal
		generatorHelper.implementClassEquals(out, b);
		out.println();
		// hashcode
		generatorHelper.implementClassHashCode("", out, b);
		out.println();
		// toString
		generatorHelper.implementClassToString("", out, b);
		generatorHelper.implementClassToString0("", out, b);

		out.println(CSharpSign.getInstance().Tab[0] + "}");
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
				+ CSharpSign.getInstance().Imarshal);
	}

	private String shortName(String fullName) {
		return fullName.substring(fullName.lastIndexOf(".") + 1,
				fullName.length());
	}
}
