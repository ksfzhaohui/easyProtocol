package com.ep.genpro.helper;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.ep.config.Bean;
import com.ep.genpro.IGeneratorHelper;
import com.ep.genpro.Type;
import com.ep.genpro.Variable;
import com.ep.genpro.sign.JavaSign;
import com.ep.genpro.typeFactory.JavaTypeFactory;

public class JavaGeneratorHelper implements IGeneratorHelper {

	@Override
	public void implementClassHashCode(String prefix, PrintStream out, Bean b) {
		String result = "_h_";
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().DECLARE_HASHCODE);
		out.println(JavaSign.getInstance().Tab[1] + "{");
		out.println(JavaSign.getInstance().Tab[2] + "int " + result + " = 0;");

		for (Variable v : b.getVariables()) {
			String hashCoder = JavaTypeFactory.obj().getAllType(v.getType(), v)
					.formatHashCode(v.getName());
			out.println(JavaSign.getInstance().Tab[2] + result + " = 31 * "
					+ result + " + " + hashCoder + ";");
		}
		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return + result + ";");
		out.println(JavaSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void implementClassToString(String prefix, PrintStream out, Bean b) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().DECLARE_TOSTRING);
		out.println(JavaSign.getInstance().Tab[1] + "{");

		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return + "toString(0);");
		out.println(JavaSign.getInstance().Tab[1] + "}");
		out.println();

	}

	@Override
	public void implementClassToString0(String prefix, PrintStream out, Bean b) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().DECLARE_TOSTRING0 + " {");
		out.println(JavaSign.getInstance().Tab[2] + "depth++;");
		out.println(JavaSign.getInstance().Tab[2]
				+ "StringBuilder buffer = new StringBuilder();");
		out.println(JavaSign.getInstance().Tab[2] + "buffer.append('\\n');");
		out.println(JavaSign.getInstance().Tab[2]
				+ "for (int i = 0; i < depth - 1; i++){");
		out.println(JavaSign.getInstance().Tab[3] + "buffer.append('\\t');");
		out.println(JavaSign.getInstance().Tab[2] + "}");
		out.println(JavaSign.getInstance().Tab[2] + "buffer.append('{');");
		out.println(JavaSign.getInstance().Tab[2] + "buffer.append('\\n');");

		out.println();

		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);
			if (v.getType().equals("List")) {
				out.println(JavaSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++) {");
				out.println(JavaSign.getInstance().Tab[3]
						+ "buffer.append('\\t');");
				out.println(JavaSign.getInstance().Tab[2] + "}");
				out.println(JavaSign.getInstance().Tab[2] + "buffer.append(\""
						+ v.getName() + ": \");");
				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append('\\n');");
				out.println(JavaSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++){");
				out.println(JavaSign.getInstance().Tab[3]
						+ "buffer.append('\\t');");
				out.println(JavaSign.getInstance().Tab[2] + "}");
				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append('{');");
				out.println();

				out.println(JavaSign.getInstance().Tab[2] + "for ( "
						+ v.getValue() + " mb : " + v.getName() + " ) {");
				if (v.getValue().startsWith("com.wistone")) {
					out.println(JavaSign.getInstance().Tab[3]
							+ "buffer.append(mb.toString(depth+1));");
				} else {
					out.println(JavaSign.getInstance().Tab[3]
							+ "buffer.append(mb).append(\"\\n,\");");
				}
				out.println(JavaSign.getInstance().Tab[2] + "}");
				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append(\"\\n\");");

				out.println(JavaSign.getInstance().Tab[2]
						+ "for ( int i = 0 ; i < depth ; i++ ) {");
				out.println(JavaSign.getInstance().Tab[3]
						+ "buffer.append('\\t');");
				out.println(JavaSign.getInstance().Tab[2] + "}");
				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append('}');");

				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append(\"\\n\");");
			} else if (v.getName().equals("reqBase")
					|| v.getName().equals("respBase")) {
				out.println(JavaSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++){");
				out.println(JavaSign.getInstance().Tab[3]
						+ "buffer.append('\\t');");
				out.println(JavaSign.getInstance().Tab[2] + "}");

				out.println(JavaSign.getInstance().Tab[2] + "buffer.append(\""
						+ v.getName() + "\").append(\":\");");
				out.println(JavaSign.getInstance().Tab[2] + "buffer.append("
						+ v.getName() + ".toString(depth));");
				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append(\"\\n\");");
			} else {
				out.println(JavaSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++){");
				out.println(JavaSign.getInstance().Tab[3]
						+ "buffer.append('\\t');");
				out.println(JavaSign.getInstance().Tab[2] + "}");

				out.println(JavaSign.getInstance().Tab[2] + "buffer.append(\""
						+ v.getName() + "\").append(\": \");");
				out.println(JavaSign.getInstance().Tab[2] + "buffer.append("
						+ v.getName() + ");");
				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append(';');");
				out.println(JavaSign.getInstance().Tab[2]
						+ "buffer.append(\"\\n\");");
				out.println();
			}
			out.println();
		}

		out.println(JavaSign.getInstance().Tab[2]
				+ "for ( int i = 0 ; i < depth-1 ; i++ ) {");
		out.println(JavaSign.getInstance().Tab[3] + "buffer.append('\\t');");
		out.println(JavaSign.getInstance().Tab[2] + "}");
		out.println(JavaSign.getInstance().Tab[2] + "buffer.append('}');");
		out.println(JavaSign.getInstance().Tab[2] + "buffer.append(';');");
		out.println(JavaSign.getInstance().Tab[2] + "return buffer.toString();");

		out.println(JavaSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void implementCalssMarshal(String prefix, PrintStream out, Bean b) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().DECLARE_MARSHAL);
		out.println(JavaSign.getInstance().Tab[1] + "{");

		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);

			Type t = JavaTypeFactory.obj().getAllType(v.getType(), b);

			String s[] = t.formatMarshal(v.getName(), v.getKey(), v.getValue());

			for (String each : s)
				out.println(JavaSign.getInstance().Tab[2] + each);
		}

		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return
				+ JavaSign.getInstance().OS_Parameter + ";");
		out.println(JavaSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void implementCalssUnmarshal(String prefix, PrintStream out, Bean b) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().DECLARE_UNMARSHAL);
		out.println(JavaSign.getInstance().Tab[1] + "{");

		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);

			Type t = JavaTypeFactory.obj().getAllType(v.getType(), b);

			String s[] = t.formatUnmarshal(v.getName(), v.getKey(),
					v.getValue(), v.getName());

			for (String each : s)
				out.println(JavaSign.getInstance().Tab[2] + each);
		}

		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return
				+ JavaSign.getInstance().OS_Parameter + ";");
		out.println(JavaSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void declareImport(PrintStream out, Bean b) {
		Set<String> alreadyImport = new HashSet<String>();
		alreadyImport.add(b.getName());
		for (String imp : JavaSign.getInstance().Using) {
			alreadyImport.add(imp);
		}
		for (Variable v : b.getVariables()) {
			Type t = JavaTypeFactory.obj().getOrignType(v.getType());
			if (null != t && null != t.getImport()) {
				for (String line : t.getImport()) {
					alreadyImport.add(JavaSign.getInstance().Import + line);
				}
			}
		}
		alreadyImport.remove(b.getName());
		Object[] list = alreadyImport.toArray();
		Arrays.sort(list);
		for (Object line : list) {
			out.println(line);
		}
	}

	@Override
	public void implementClassMember(String prefix, PrintStream out, Variable v) {
		out.println(JavaSign.getInstance().Tab[1] + v.getComment());
		String str = JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Public + v.fullType() + " "
				+ v.getName();
		out.println(str + ";");
	}

	@Override
	public void implementClassConstructor(String prefix, PrintStream out, Bean b) {
		// 默认构造函数
		boolean hasInnter = false, hasComplex = false;
		if (!b.getVariables().isEmpty()) {
			out.println(JavaSign.getInstance().Tab[1]
					+ JavaSign.getInstance().Public + shortName(b.getName())
					+ "()");
			out.println(JavaSign.getInstance().Tab[1] + "{");
			for (Variable v : b.getVariables()) {
				hasInnter |= JavaTypeFactory.obj().isInnerType(v.getType());
				hasComplex |= !JavaTypeFactory.obj().isInnerType(v.getType());
				out.println(JavaSign.getInstance().Tab[2] + "this."
						+ v.getName() + " = " + initializer(v) + ";");
			}
			out.println(JavaSign.getInstance().Tab[1] + "}");
			out.println();
		}
		// 全部简单变量构造函数
		if (hasInnter && hasComplex) {
			StringBuffer ss = new StringBuffer();
			for (int i = 0; i != b.getVariables().size(); ++i) {
				Variable v = b.getVariables().get(i);
				if (JavaTypeFactory.obj().isInnerType(v.getType())) {
					if (ss.length() != 0)
						ss.append(", ");
					ss.append(v.fullType() + " _" + v.getName() + "_");
				}
			}
			out.println(JavaSign.getInstance().Tab[1]
					+ JavaSign.getInstance().Public + shortName(b.getName())
					+ "(" + ss.toString() + ")");
			out.println(JavaSign.getInstance().Tab[1] + "{");
			for (Variable v : b.getVariables()) {
				if (JavaTypeFactory.obj().isInnerType(v.getType())) {
					out.println(JavaSign.getInstance().Tab[2] + "this."
							+ v.getName() + " = _" + v.getName() + "_;");
				} else {
					out.println(JavaSign.getInstance().Tab[2] + "this."
							+ v.getName() + " = " + initializer(v) + ";");
				}
			}
			out.println(JavaSign.getInstance().Tab[1] + "}");
			out.println();
		}
		// 全部变量构造函数
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);
			if (i != 0)
				sb.append(", ");
			sb.append(v.fullType() + " _" + v.getName() + "_");
		}
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Public + shortName(b.getName()) + "("
				+ sb.toString() + ")");
		out.println(JavaSign.getInstance().Tab[1] + "{");
		for (Variable v : b.getVariables()) {
			out.println(JavaSign.getInstance().Tab[2] + "this." + v.getName()
					+ " = _" + v.getName() + "_;");
		}
		out.println(JavaSign.getInstance().Tab[1] + "}");
		out.println();
	}

	private String initializer(Variable v) {
		return JavaTypeFactory
				.obj()
				.getAllType(v.getType(), v)
				.formatInitialExpressionP(v.getInitial(), v.getKey(),
						v.getValue());
	}

	private String shortName(String fullName) {
		return fullName.substring(fullName.lastIndexOf(".") + 1,
				fullName.length());
	}

	@Override
	public void implementClassEquals(PrintStream out, Bean b) {
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().Override);
		out.println(JavaSign.getInstance().Tab[1]
				+ JavaSign.getInstance().DECLARE_EQUALS);
		out.println(JavaSign.getInstance().Tab[1] + "{");
		out.println(JavaSign.getInstance().Tab[2] + JavaSign.getInstance().If
				+ "(this == " + JavaSign.getInstance().OBJECT_Parameter + ")");
		out.println(JavaSign.getInstance().Tab[3]
				+ JavaSign.getInstance().Return + JavaSign.getInstance().True
				+ ";");
		out.println(JavaSign.getInstance().Tab[2] + JavaSign.getInstance().If
				+ "(!(" + JavaSign.getInstance().OBJECT_Parameter + " "
				+ JavaSign.getInstance().Instanceof + "" + b.getName() + "))");
		out.println(JavaSign.getInstance().Tab[3]
				+ JavaSign.getInstance().Return + JavaSign.getInstance().False
				+ ";");
		if (!b.getVariables().isEmpty()) {
			out.println(JavaSign.getInstance().Tab[2] + b.getName() + " "
					+ JavaSign.getInstance().OBJECT_Parameter + "t = ("
					+ b.getName() + ")"
					+ JavaSign.getInstance().OBJECT_Parameter + ";");
			for (Variable v : b.getVariables()) {
				String equal = JavaTypeFactory
						.obj()
						.getAllType(v.getType(), v)
						.formatEquals(
								v.getName(),
								JavaSign.getInstance().OBJECT_Parameter + "t."
										+ v.getName(), false);
				out.println(JavaSign.getInstance().Tab[2]
						+ JavaSign.getInstance().If + "(" + equal + ")");
				out.println(JavaSign.getInstance().Tab[3]
						+ JavaSign.getInstance().Return
						+ JavaSign.getInstance().False + ";");
			}
		}
		out.println(JavaSign.getInstance().Tab[2]
				+ JavaSign.getInstance().Return + JavaSign.getInstance().True
				+ ";");
		out.println(JavaSign.getInstance().Tab[1] + "}");
	}
}
