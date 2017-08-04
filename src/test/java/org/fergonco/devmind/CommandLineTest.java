package org.fergonco.devmind;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.fergonco.devmind.interpreter.ConstantNotFoundException;
import org.fergonco.devmind.interpreter.Interpreter;
import org.fergonco.devmind.interpreter.KBRuntimeException;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.parser.SyntaxException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandLineTest {

	@Before
	@After
	public void clean() {
		Interpreter.HISTORY = new File("target/kb.hist");
		assertTrue(!Interpreter.HISTORY.exists() || Interpreter.HISTORY.delete());
	}

	@Test
	public void testSaveAndLoad() throws SyntaxException, LexerException, KBRuntimeException, IOException {
		commmandLineRun("A");
		commmandLineRun("B");
		commmandLineRun("nice(A)");
		try {
			commmandLineRun("nice(C)");
			fail();
		} catch (ConstantNotFoundException e) {
		}
	}

	@Test
	public void testAskStatementsDoNotBreakAnything()
			throws SyntaxException, LexerException, KBRuntimeException, IOException {
		commmandLineRun("A");
		commmandLineRun("what");
		commmandLineRun("B");
		commmandLineRun("nice(B)");
	}

	private String commmandLineRun(String query)
			throws SyntaxException, LexerException, KBRuntimeException, IOException {
		Interpreter i = new Interpreter();
		return i.commmandLineRun(query);
	}
}
