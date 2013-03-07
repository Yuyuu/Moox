package dcll.groupe1.moox;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;


import org.junit.Before;
import org.junit.Test;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.parser.impl.XmlParser;

/**
 * Unit test for XmlParser class
 * @author Laurent Warin
 *
 */
public class XmlParserTestCase {
	File f;
	URI uri;
	
	@Before
	public void setUp() {
		f = new File("testXml.xml");
		uri = f.toURI();
	}

	@Test
	public void testParser() throws Exception {
		Tag tag = new XmlParser().parse(uri);
		
		//Test on quiz node
		assertEquals("quiz", tag.getName());
		assertNull(tag.getValue());
		assertTrue(tag.getAttributes().size() == 0);
		assertTrue(tag.getSubTags().size() == 2);
	
		//Test on question 0
		Tag subTag1 = tag.getSubTags().get(0);
		assertEquals("question", subTag1.getName());
		Attribute att1 = subTag1.getAttributes().get(0);
		assertEquals("type", att1.getName());
		assertEquals("category", att1.getValue());
		assertEquals("category", subTag1.getSubTags().get(0).getName());

		//Test on question 1
		Tag subTag2 = tag.getSubTags().get(1);
		assertEquals("question", subTag2.getName());
		assertTrue(subTag2.getSubTags().size() == 12);
	}

}
