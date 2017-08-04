package org.fergonco.devmind.interpreter;

import org.apache.commons.lang3.StringUtils;

public class WhatStatement extends AbstractAskStatement implements Statement {

	@Override
	public void execute(KnowledgeBase kb) {
		setOutput(StringUtils.join(kb.getConstants(), "\n"));
	}

}
