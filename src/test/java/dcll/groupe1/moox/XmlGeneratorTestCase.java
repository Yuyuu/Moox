package dcll.groupe1.moox;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.generator.impl.XmlGenerator;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.impl.XmlParser;

public class XmlGeneratorTestCase extends TestCase {
	File f;
	Tag tag;
	String compareTo;

	@Before
	public void setUp() {
		// On parse testXml pour récupérer un Tag
		f = new File("testXml.xml");
		try {
			tag = new XmlParser().parse(f.toURI());
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testParser() throws Exception {
		// On genere un fichier Xml à partir du Tag
		XmlGenerator xml = new XmlGenerator();
		FileWriter fw = new FileWriter("toto.xml", false);
		BufferedWriter output = new BufferedWriter(fw);
		output.write(xml.generate(tag));
		output.flush();
		output.close();

		// On récupère un Tag à partir du fichier xml
		Tag test = null;
		f = new File("toto.xml");
		try {
			test = new XmlParser().parse(f.toURI());
		} catch (ParserException e) {
			e.printStackTrace();
		}
		f.delete();

		// Comparaison entre le Tag initial et le Tag issu de la génération
		assertEquals(tag, test);
	}
}
