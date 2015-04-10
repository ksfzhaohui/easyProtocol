package com.ep.genpro.helper;

import java.io.PrintStream;

import com.ep.config.Bean;
import com.ep.genpro.IGeneratorHelper;
import com.ep.genpro.Type;
import com.ep.genpro.Variable;
import com.ep.genpro.sign.CSharpSign;
import com.ep.genpro.typeFactory.CSharpTypeFactory;

public class CSharpGeneratorHelper implements IGeneratorHelper {

	@Override
	public void implementClassHashCode(String prefix, PrintStream out, Bean b) {
		String result = "_h_";
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_HASHCODE);
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");

		out.println(prefix + CSharpSign.getInstance().Tab[2] + "int " + result
				+ " = 0;");

		for (Variable v : b.getVariables()) {
			String hashCoder = CSharpTypeFactory.obj()
					.getAllType(v.getType(), v).formatHashCode(v.getName());
			out.println(prefix + CSharpSign.getInstance().Tab[2] + result
					+ " = 31 * " + result + " + " + hashCoder + ";");
		}

		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().Return + result + ";");
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void implementClassToString(String prefix, PrintStream out, Bean b) {
		String p = "_sb_";
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_TOSTRING);
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");

		out.println(prefix + CSharpSign.getInstance().Tab[2] + "StringBuilder "
				+ p + " = new StringBuilder();");

		out.println(prefix + CSharpSign.getInstance().Tab[2] + p
				+ ".Append('(');");

		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);

			out.print(prefix + CSharpSign.getInstance().Tab[2] + p + ".Append("
					+ v.getName() + ")");
			if (i != b.getVariables().size() - 1)
				out.print(".Append(',');");
			else
				out.print(";");

			out.println();
		}

		out.println(prefix + CSharpSign.getInstance().Tab[2] + p
				+ ".Append(')');");

		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().Return + p + ".ToString();");
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void implementClassToString0(String prefix, PrintStream out, Bean b) {
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_TOSTRING0 + " {");
		out.println(prefix + CSharpSign.getInstance().Tab[2] + "depth++;");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "StringBuilder buffer = new StringBuilder();");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "buffer.Append('\\n');");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "for (int i = 0; i < depth - 1; i++){");
		out.println(prefix + CSharpSign.getInstance().Tab[3]
				+ "buffer.Append('\\t');");
		out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "buffer.Append('{');");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "buffer.Append('\\n');");
		out.println();

		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);
			if (v.getType().equals("List")) {
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++) {");
				out.println(prefix + CSharpSign.getInstance().Tab[3]
						+ "buffer.Append('\\t');");
				out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(\"" + v.getName() + ": \");");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append('\\n');");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++){");
				out.println(prefix + CSharpSign.getInstance().Tab[3]
						+ "buffer.Append('\\t');");
				out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append('{');");
				out.println();

				if (v.getValue().startsWith("com.wistone")) {
					out.println(prefix + CSharpSign.getInstance().Tab[2]
							+ "foreach ( " + v.getValue() + " mb in "
							+ v.getName() + " ) {");
					out.println(prefix + CSharpSign.getInstance().Tab[3]
							+ "buffer.Append(mb.ToString(depth+1));");
					out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");

					out.println(prefix + CSharpSign.getInstance().Tab[2]
							+ "buffer.Append(\"\\n\");");
				} else {
					// add
					out.println(prefix + CSharpSign.getInstance().Tab[2]
							+ "buffer.Append('\\n');");
					out.println(prefix + CSharpSign.getInstance().Tab[2]
							+ "foreach ( " + v.getValue() + " mb in "
							+ v.getName() + " ) {");
					// add
					out.println(prefix + CSharpSign.getInstance().Tab[3]
							+ "for (int i = 0; i < depth+1; i++){");
					out.println(prefix + CSharpSign.getInstance().Tab[4]
							+ "buffer.Append('\\t');");
					out.println(prefix + CSharpSign.getInstance().Tab[3] + "}");

					// fix
					out.println(prefix + CSharpSign.getInstance().Tab[3]
							+ "buffer.Append(mb.ToString()).Append(\",\\n\");");
					out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");
				}

				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "for ( int i = 0 ; i < depth ; i++ ) {");
				out.println(prefix + CSharpSign.getInstance().Tab[3]
						+ "buffer.Append('\\t');");
				out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append('}');");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(';');");

				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(\"\\n\");");
			} else if (v.getName().equals("reqBase")
					|| v.getName().equals("respBase")) {
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++){");
				out.println(prefix + CSharpSign.getInstance().Tab[3]
						+ "buffer.Append('\\t');");
				out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");

				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(\"" + v.getName()
						+ "\").Append(\":\");");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(" + v.getName() + ".ToString(depth));");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(\"\\n\");");
			} else {
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "for (int i = 0; i < depth; i++){");
				out.println(prefix + CSharpSign.getInstance().Tab[3]
						+ "buffer.Append('\\t');");
				out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");

				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(\"" + v.getName()
						+ "\").Append(\": \");");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(" + v.getName() + ");");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(';');");
				out.println(prefix + CSharpSign.getInstance().Tab[2]
						+ "buffer.Append(\"\\n\");");
				out.println();
			}
			out.println();
		}

		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "for ( int i = 0 ; i < depth-1 ; i++ ) {");
		out.println(prefix + CSharpSign.getInstance().Tab[3]
				+ "buffer.Append('\\t');");
		out.println(prefix + CSharpSign.getInstance().Tab[2] + "}");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "buffer.Append('}');");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "buffer.Append(';');");
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ "return buffer.ToString();");

		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void implementCalssMarshal(String prefix, PrintStream out, Bean b) {
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_MARSHAL);
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");
		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);
			Type t = CSharpTypeFactory.obj().getAllType(v.getType(), b);
			String s[] = t.formatMarshal(v.getName(), v.getKey(), v.getValue());
			for (String each : s)
				out.println(prefix + CSharpSign.getInstance().Tab[2] + each);
		}
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().Return
				+ CSharpSign.getInstance().OS_Parameter + ";");
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");
	}

	@Override
	public void implementCalssUnmarshal(String prefix, PrintStream out, Bean b) {
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_UNMARSHAL);
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");
		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);
			Type t = CSharpTypeFactory.obj().getAllType(v.getType(), b);
			String s[] = t.formatUnmarshal(v.getName(), v.getKey(),
					v.getValue(), v.getName());
			for (String each : s)
				out.println(prefix + CSharpSign.getInstance().Tab[2] + each);
		}
		out.println(prefix + CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().Return
				+ CSharpSign.getInstance().OS_Parameter + ";");
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");

	}

	@Override
	public void declareImport(PrintStream out, Bean b) {
		for (String imp : CSharpSign.getInstance().Using) {
			out.println(imp);
		}
	}

	@Override
	public void implementClassMember(String prefix, PrintStream out, Variable v) {
		out.println(prefix + CSharpSign.getInstance().Tab[1] + v.getComment());
		String str = CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().Public + v.fullType() + " "
				+ v.getName();
		out.println(prefix + str + ";");
	}

	@Override
	public void implementClassConstructor(String prefix, PrintStream out, Bean b) {
		// 默认构造函数
		boolean hasInnter = false, hasComplex = false;
		if (!b.getVariables().isEmpty()) {
			out.println(prefix + CSharpSign.getInstance().Tab[1]
					+ CSharpSign.getInstance().Public + getClassName(b) + "()");
			out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");
			for (Variable v : b.getVariables()) {
				hasInnter |= CSharpTypeFactory.obj().isInnerType(v.getType());
				hasComplex |= !CSharpTypeFactory.obj().isInnerType(v.getType());
				out.println(prefix + CSharpSign.getInstance().Tab[2] + "this."
						+ v.getName() + " = " + initializer(v) + ";");
			}
			out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");
			out.println();
		}
		if (hasInnter && hasComplex) {
			StringBuffer ss = new StringBuffer();
			for (int i = 0; i != b.getVariables().size(); ++i) {
				Variable v = b.getVariables().get(i);
				if (CSharpTypeFactory.obj().isInnerType(v.getType())) {
					if (ss.length() != 0)
						ss.append(", ");
					ss.append(v.fullType() + " _" + v.getName() + "_");
				}
			}
			out.println(prefix + CSharpSign.getInstance().Tab[1]
					+ CSharpSign.getInstance().Public + getClassName(b) + "("
					+ ss.toString() + ")");
			out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");
			for (Variable v : b.getVariables()) {
				if (CSharpTypeFactory.obj().isInnerType(v.getType())) {
					out.println(prefix + CSharpSign.getInstance().Tab[2]
							+ "this." + v.getName() + " = _" + v.getName()
							+ "_;");
				} else {
					out.println(prefix + CSharpSign.getInstance().Tab[2]
							+ "this." + v.getName() + " = " + initializer(v)
							+ ";");
				}
			}
			out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");
			out.println();
		}
		// 变量
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i != b.getVariables().size(); ++i) {
			Variable v = b.getVariables().get(i);
			if (i != 0)
				sb.append(", ");
			sb.append(v.fullType() + " _" + v.getName() + "_");
		}
		out.println(prefix + CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().Public + getClassName(b) + "("
				+ sb.toString() + ")");
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "{");
		for (Variable v : b.getVariables()) {
			out.println(prefix + CSharpSign.getInstance().Tab[2] + "this."
					+ v.getName() + " = _" + v.getName() + "_;");
		}
		out.println(prefix + CSharpSign.getInstance().Tab[1] + "}");
		out.println();
	}

	private String getClassName(Bean b) {
		return b.getName().substring(b.getName().lastIndexOf(".") + 1,
				b.getName().length());
	}

	private String initializer(Variable v) {
		return CSharpTypeFactory
				.obj()
				.getAllType(v.getType(), v)
				.formatInitialExpressionP(v.getInitial(), v.getKey(),
						v.getValue());
	}

	@Override
	public void implementClassEquals(PrintStream out, Bean b) {
		out.println(CSharpSign.getInstance().Tab[1]
				+ CSharpSign.getInstance().DECLARE_EQUALS);
		out.println(CSharpSign.getInstance().Tab[1] + "{");
		out.println(CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().If + "(this == "
				+ CSharpSign.getInstance().OBJECT_Parameter + ")");
		out.println(CSharpSign.getInstance().Tab[3]
				+ CSharpSign.getInstance().Return
				+ CSharpSign.getInstance().True + ";");
		out.println(CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().If + "(!("
				+ CSharpSign.getInstance().OBJECT_Parameter + " "
				+ CSharpSign.getInstance().Instanceof + "" + b.getName() + "))");
		out.println(CSharpSign.getInstance().Tab[3]
				+ CSharpSign.getInstance().Return
				+ CSharpSign.getInstance().False + ";");
		if (!b.getVariables().isEmpty()) {
			out.println(CSharpSign.getInstance().Tab[2] + b.getName() + " "
					+ CSharpSign.getInstance().OBJECT_Parameter + "t = ("
					+ b.getName() + ")"
					+ CSharpSign.getInstance().OBJECT_Parameter + ";");
			for (Variable v : b.getVariables()) {
				String equal = CSharpTypeFactory
						.obj()
						.getAllType(v.getType(), v)
						.formatEquals(
								v.getName(),
								CSharpSign.getInstance().OBJECT_Parameter
										+ "t." + v.getName(), false);
				out.println(CSharpSign.getInstance().Tab[2]
						+ CSharpSign.getInstance().If + "(" + equal + ")");
				out.println(CSharpSign.getInstance().Tab[3]
						+ CSharpSign.getInstance().Return
						+ CSharpSign.getInstance().False + ";");
			}
		}
		out.println(CSharpSign.getInstance().Tab[2]
				+ CSharpSign.getInstance().Return
				+ CSharpSign.getInstance().True + ";");
		out.println(CSharpSign.getInstance().Tab[1] + "}");
	}
}
