package dcll.groupe1.moox;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.impl.JsonParser;

public class JsonParserTestCase {

	File myFile;
	
	
	@Before
	public void setUp() throws Exception {
		myFile = new File("testJson.json");
	}

	@Test
	public void testParse() throws ParserException {
		Tag tag = new JsonParser().parse(myFile.toURI());
		
		assertEquals(tag.getName(), "quiz");
		assertEquals(tag.getValue(), "toto");
		
		ArrayList<Attribute> listAttr = tag.getAttributes();
		assertEquals(listAttr.size(), 2);
		assertEquals(listAttr.get(0).getName(), "type1");
		assertEquals(listAttr.get(1).getName(), "type2");
		assertEquals(listAttr.get(0).getValue(), "category1");
		assertEquals(listAttr.get(1).getValue(), "category2");
		
		ArrayList<Tag> listSubTag = tag.getSubTags();
		assertEquals(listSubTag.size(), 1);
		
		Tag question = listSubTag.get(0);
		assertEquals(question.getName(), "question");
		assertEquals(question.getValue(), "test");
		
		listAttr = question.getAttributes();
		assertEquals(listAttr.size(), 1);
		assertEquals(listAttr.get(0).getName(), "type");
		assertEquals(listAttr.get(0).getValue(), "category");
		
		listSubTag = question.getSubTags();
		assertEquals(listSubTag.size(), 1);
		
		Tag category = listSubTag.get(0);
		assertEquals(category.getName(), "category");
		assertNull(category.getValue());
		assertEquals(category.getAttributes().size(), 0);
		
		listSubTag = category.getSubTags();
		assertEquals(listSubTag.size(), 1);
		
		Tag text = listSubTag.get(0);
		assertEquals(text.getName(), "text");
		assertEquals(text.getValue(), "$course$/Défaut pour 1SA3GL1");
		assertEquals(text.getAttributes().size(), 0);
		assertEquals(text.getSubTags().size(), 0);
		
		
	}

}
