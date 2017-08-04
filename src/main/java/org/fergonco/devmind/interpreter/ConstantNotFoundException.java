package org.fergonco.devmind.interpreter;

public class ConstantNotFoundException extends KBRuntimeException {
	private static final long serialVersionUID = 1L;

	public ConstantNotFoundException(String constant) {
		super(constant + " does not exist");
	}

}
