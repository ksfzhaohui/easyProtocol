package com.ep.config;

import java.util.ArrayList;
import java.util.List;

import com.ep.genpro.Variable;

public class Bean {

	private String comment = "";//注释
	private String name;//名称
	private List<Variable> variables = new ArrayList<Variable>();//变量列表

	public Bean(String name, String comment) {
		this.name = name;
		this.comment = comment;
	}

	public void putVariable(Variable v) {
		variables.add(v);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariable(List<Variable> variableList) {
		this.variables = variableList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
