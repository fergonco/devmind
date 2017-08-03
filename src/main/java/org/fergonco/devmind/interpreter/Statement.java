package org.fergonco.devmind.interpreter;

public interface Statement {

	void execute(KnowledgeBase kb);

	String getOutput();

}
