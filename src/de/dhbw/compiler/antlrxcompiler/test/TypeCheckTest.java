/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis ANTLR-Parser für X
 * - Testfall-Utility für Parser
 * 
 * **********************************************
 */


package de.dhbw.compiler.antlrxcompiler.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import de.dhbw.compiler.antlrxcompiler.XLexer;
import de.dhbw.compiler.antlrxcompiler.XParser;
import de.dhbw.compiler.antlrxcompiler.XTree;
import de.dhbw.compiler.antlrxcompiler.XTreeAdaptor;
import de.dhbw.compiler.antlrxcompiler.XTypeCheck;

public abstract class TypeCheckTest {

	protected void testTypeCheckTree(String in, String expected) throws Exception {
		XTreeAdaptor xTreeAdaptor = new XTreeAdaptor(); 		
		ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(in.getBytes())); 
		XLexer scanner = new XLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(scanner);
		XParser			parser	= new XParser(tokens);
		parser.setTreeAdaptor(xTreeAdaptor);
		XTree out = parser.program().getTree();
		XTypeCheck typecheck = new XTypeCheck(new CommonTreeNodeStream(xTreeAdaptor, out));
		typecheck.setTreeAdaptor(xTreeAdaptor);
		out = typecheck.program().getTree();
		
		if (out==null) {
			assertEquals(expected, out);
		} else {
			assertEquals(expected, out.toStringTree());
		}
	}
	
}
