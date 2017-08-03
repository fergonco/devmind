package org.fergonco.devmind.interpreter;

import org.apache.commons.lang3.StringUtils;

public class ShowStatement extends AbstractAskStatement implements Statement {

	private String constantName;

	public ShowStatement(String constantName) {
		this.constantName = constantName;
	}

	@Override
	public void execute(KnowledgeBase kb) {
		setOutput(StringUtils.join(kb.getReferences(constantName), "\n"));
	}

}
