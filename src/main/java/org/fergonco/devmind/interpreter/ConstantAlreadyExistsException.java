package org.fergonco.devmind.interpreter;

public class ConstantAlreadyExistsException extends KBRuntimeException {
	private static final long serialVersionUID = 1L;

	public ConstantAlreadyExistsException(String constant) {
		super(constant + " already exists");
	}

}
