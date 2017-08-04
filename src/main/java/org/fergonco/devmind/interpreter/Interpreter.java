package org.fergonco.devmind.interpreter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.fergonco.devmind.lexer.Lexer;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.lexer.Token;
import org.fergonco.devmind.parser.Parser;
import org.fergonco.devmind.parser.SyntaxException;

public class Interpreter {

	public static final File HISTORY = new File("kb.hist");
	private KnowledgeBase kb = new KnowledgeBase();
	private ArrayList<String> statements = new ArrayList<>();

	public Statement compile(String query) throws SyntaxException, LexerException {
		Lexer lexer = new Lexer(query);
		Token token = lexer.process();
		Parser parser = new Parser(token);
		return parser.parse();
	}

	public String run(String script) throws SyntaxException, LexerException, KBRuntimeException {
		Statement statement = compile(script);
		statement.execute(kb);
		if (statement.tells()) {
			this.statements.add(statement.toString());
		}
		return statement.getOutput();
	}

	public String[] getConstants() {
		return kb.getConstants();
	}

	public Statement[] getReferences(String constantName) {
		return kb.getReferences(constantName);
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Version: " + Interpreter.class.getPackage().getImplementationVersion());
			System.out.println("Usage: devmind <statement>");
		} else if (args.length == 1 && args[0].equals("--symbols")) {
			Interpreter i = new Interpreter();
			try {
				i.load();
				String[] symbols = i.getSymbols();
				System.out.println(StringUtils.join(symbols, " "));
			} catch (IOException | SyntaxException | LexerException | KBRuntimeException e) {
			}
		} else {
			String query = StringUtils.join(args, " ");
			Interpreter i = new Interpreter();
			try {
				String ret = i.commmandLineRun(query);
				if (ret != null) {
					System.out.println(ret);
				}
			} catch (SyntaxException | LexerException | KBRuntimeException | IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public String[] getSymbols() {
		return kb.getSymbols();
	}

	public String commmandLineRun(String query)
			throws SyntaxException, LexerException, KBRuntimeException, IOException {
		load();
		String ret = run(query);
		save();
		return ret;
	}

	private void save() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY));
		try {
			for (String statement : statements) {
				writer.write(statement);
				writer.newLine();
			}
		} finally {
			writer.close();
		}
	}

	private void load() throws IOException, SyntaxException, LexerException, KBRuntimeException {
		if (HISTORY.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(HISTORY));
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					run(line);
				}
			} finally {
				reader.close();
			}
		}
	}

}
