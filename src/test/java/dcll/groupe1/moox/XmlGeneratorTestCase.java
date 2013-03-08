package dcll.groupe1.moox;

import java.io.BufferedWriter;
import java.io.StringWriter;

import java.io.File;

import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

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

	/**
	 * Test of XmlGenerator in parsing a Tag and then generating it
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGenerator1() throws Exception {
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

	/**
	 * Test of XmlGenerator with String representation of Xml files
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGenerator2() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(f);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\\s", "");

		XmlGenerator xml = new XmlGenerator();
		String tmp = xml.generate(tag).replaceAll("\\s", "");

		// suppression de l'en-tête du fichier XML
		String test = tmp.substring(tmp.indexOf("<quiz>"));
		assertEquals(output, test);
	}
}
