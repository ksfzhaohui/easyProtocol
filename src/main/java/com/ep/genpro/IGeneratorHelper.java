package com.ep.genpro;

import java.io.PrintStream;

import com.ep.config.Bean;

public interface IGeneratorHelper {

	public void implementClassHashCode(String prefix, PrintStream out, Bean b);

	public void implementClassToString(String prefix, PrintStream out, Bean b);

	public void implementClassToString0(String prefix, PrintStream out, Bean b);

	public void implementCalssMarshal(String prefix, PrintStream out, Bean b);

	public void implementCalssUnmarshal(String prefix, PrintStream out, Bean b);

	public void declareImport(PrintStream out, Bean b);

	public void implementClassMember(String prefix, PrintStream out, Variable v);

	public void implementClassConstructor(String prefix, PrintStream out, Bean b);

	public void implementClassEquals(PrintStream out, Bean b);
}
