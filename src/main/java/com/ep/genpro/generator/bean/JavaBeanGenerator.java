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
import com.ep.genpro.helper.JavaGeneratorHelper;
import com.ep.genpro.sign.JavaSign;

public class JavaBeanGenerator extends BeanGenerator {

	public JavaBeanGenerator(ConfigInfo config, String encode, String endfix) {
		super(config, encode, endfix);
		this.generatorHelper = new JavaGeneratorHelper();
	}

	@Override
	public void generateBean(String beanName, Bean b) {
		String name = b.getName();
		String fullName = config.getBeanRoot() + "\\" + name.replace('.', '\\')
				+ endfix;
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

	public static Set<Bean> bsets = new HashSet<Bean>();

	protected void generateBeanClass(PrintStream out, Bean b) {
		bsets.add(b);

		// package
		out.println(JavaSign.getInstance().Package
				+ b.getName().substring(0, b.getName().lastIndexOf(".")) + ";");
		out.println();
		// import
		generatorHelper.declareImport(out, b);
		out.println();
		out.println(JavaSign.getInstance().DECLARE_NO_MODIFY);
		declareClass(out, b);
		out.println("{");
		// members
		for (Variable v : b.getVariables())
			generatorHelper.implementClassMember(null, out, v);
		out.println();
		// constructor
		generatorHelper.implementClassConstructor(null, out, b);
		out.println();
		// marshal
		generatorHelper.implementCalssMarshal(null, out, b);
		out.println();
		// unmarshal
		generatorHelper.implementCalssUnmarshal(null, out, b);
		out.println();
		// equal
		generatorHelper.implementClassEquals(out, b);
		out.println();
		// hashcode
		generatorHelper.implementClassHashCode(null, out, b);
		out.println();
		// toString
		generatorHelper.implementClassToString(null, out, b);
		// toString0
		generatorHelper.implementClassToString0(null, out, b);
		out.println("}");
	}

	private void declareClass(PrintStream out, Bean b) {
		if (null != b.getComment()) {
			out.println("/**");
			out.println(" *  " + formatComment(b.getComment()));
			out.println(" */");
		}
		out.println(JavaSign.getInstance().Public
				+ JavaSign.getInstance().Class + shortName(b.getName()) + " "
				+ JavaSign.getInstance().Implements
				+ JavaSign.getInstance().ObjPath[1]);
	}

	private String formatComment(String filepath) {
		return filepath.replaceAll("//", "").replaceAll("\\\\", "/");
	}

	private String formatPath(String filepath) {
		return filepath.replaceAll("//", "/").replaceAll("\\\\", "/");
	}

	private String shortName(String fullName) {
		return fullName.substring(fullName.lastIndexOf(".") + 1,
				fullName.length());
	}
}
