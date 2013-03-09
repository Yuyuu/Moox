package dcll.groupe1.moox.parser.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.ParserInterface;

public class JsonParser implements ParserInterface {

	@Override
	public Tag parse(URI uri) throws ParserException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode tree;
		Tag tag = new Tag();
		try {
			tree = mapper.readTree(new File(uri));
			tag = parseTag(tree);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tag;
	}

	private Tag parseTag(JsonNode nodeToParse) {
		Tag t = new Tag();
		Iterator<Entry<String, JsonNode>> p = nodeToParse.getFields();
		while (p.hasNext()) {
			Entry<String, JsonNode> node = p.next();
			JsonNode value = node.getValue();

			// si non { ou [
			if (!value.isContainerNode()) {
				t.setValue(value.asText());
			} else if (value.isArray()) {
				// tableau d'attributs ou de subtags
				switch (node.getKey()) {
				case "attributes":
					Iterator<JsonNode> att = value.getElements();
					while (att.hasNext()) {
						JsonNode attribut = att.next();
						Entry<String, JsonNode> toto = attribut.getFields()
								.next();
						Attribute attribute = new Attribute();
						attribute.setName(toto.getKey());
						attribute.setValue(toto.getValue().asText());
						t.addAttribute(attribute);
					}
					break;
				case "subTags":
					Iterator<JsonNode> sub = value.getElements();
					while (sub.hasNext()) {
						JsonNode subTag = sub.next();
						t.addTag(parseTag(subTag));
					}
					break;
				}
			} else if (value.isObject()) {
				t.setName(node.getKey());
			}

			if (!p.hasNext() && value.getFields().hasNext()) {
				p = value.getFields();
			}

		}

		System.out.println("**Name : " + t.getName());
		System.out.println("**Value : " + t.getValue());
		System.out.println(t.getAttributes());
		// System.out.println(tag.getSubTags().get(0).getName());
		// System.out.println(tag.getSubTags().get(0).getValue());
		return t;
	}

	public static void main(String args[]) {
		File f = new File("testJson.json");
		JsonParser jsp = new JsonParser();
		try {
			System.out.println(jsp.parse(f.toURI()));
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
