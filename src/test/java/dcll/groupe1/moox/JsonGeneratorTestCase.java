package dcll.groupe1.moox;

import java.io.File;
import java.io.FileWriter;

import org.junit.Before;
import org.junit.Test;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.generator.GeneratorException;
import dcll.groupe1.moox.generator.impl.JsonGenerator;
import dcll.groupe1.moox.parser.impl.JsonParser;

import junit.framework.TestCase;

public class JsonGeneratorTestCase extends TestCase {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testEmptyTag() {
		Tag root = new Tag();
		
		JsonGenerator generator = new JsonGenerator(false);
		try {
			generator.generate(root);	//should generate an exception
			fail();
		} catch (GeneratorException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testOnlyRoot() throws Exception {
		Tag root = new Tag();
		root.setName("root");
		String expectedRes = "{\"root\":{\"text\":null,\"attributes\":[],\"subTags\":[]}}";
		
		JsonGenerator generator = new JsonGenerator(false);
		String res = generator.generate(root);
		assertEquals(expectedRes, res);
	}
	
	@Test
	public void testGenerateAndParse() throws Exception {
		Attribute a1 = new Attribute();
		Attribute a2 = new Attribute();
		a1.setName("att1");
		a1.setValue("v1");
		a2.setName("att2");
		a2.setValue("v2");
		
		Tag text = new Tag();
		text.setName("text");
		text.setValue("value");
		text.addAttribute(a1);
		text.addAttribute(a2);
		
		Tag subTag = new Tag();
		subTag.setName("sub");
		subTag.addTag(text);
		
		Tag root = new Tag();
		root.setName("root");
		Attribute a = new Attribute();
		a.setName("attRoot");
		a.setValue("val");
		root.addAttribute(a);
		root.addTag(subTag);
		
		JsonGenerator generator = new JsonGenerator(false);
		String res = generator.generate(root);
		File f = File.createTempFile("testMoox", "tmp");
		FileWriter w = new FileWriter(f);
		w.write(res);
		w.close();
		
		JsonParser parser = new JsonParser();
		Tag result = parser.parse(f.toURI());
		f.delete();
		
		assertEquals(root, result);
	}

}
