package dcll.groupe1.moox.parser.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;

public class JsonParser {

	public static void main(String args[]) {
		InputStream f;
		try {
			f = new FileInputStream("testJson.json");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode tree = mapper.readTree(f);

			Iterator<Entry<String, JsonNode>> p = tree.getFields();

			Tag tag = new Tag();
			while (p.hasNext()) {
				Entry<String, JsonNode> node = p.next();
				JsonNode value = node.getValue();
				// si non { ou [
				if (!value.isContainerNode()) {
					tag.setValue(value.asText());
				} else if (value.isArray()) {
					System.out.println("*ARRAY : " + value);
					// tableau d'attributs ou de subtags
					// node = value.getFields().next();
					switch (node.getKey()) {
					case "attributes":
						System.out.println("go att");
						Iterator<JsonNode> att = value.getElements();
						while (att.hasNext()) {
							System.out.println("attribuuuuuuuuts");
							JsonNode attribut = att.next();
							Entry<String, JsonNode> toto = attribut.getFields().next();
							Attribute attribute = new Attribute();
							attribute.setName(toto.getKey());
							attribute.setValue(toto.getValue().asText());
							System.out.println(attribute);
							tag.addAttribute(attribute);
						}

						// System.out.println("attributes " +
						// node.getValue().getFieldNames());
						break;
					case "subTags":
						System.out.println("go sub");
						break;
					}
				} else if (value.isObject()) {
					// c'est un Tag
					tag.setName(node.getKey());
				}

				// System.out.println("***" + value.getFields().hasNext());
				if (!p.hasNext() && value.getFields().hasNext()) {
					p = value.getFields();
				}

			}

			// System.out.println(tag.getName());
			// System.out.println(tag.getValue());
			// Iterator<String> it = tree.getFieldNames();
			// while(it.hasNext()) {
			// System.out.println(it.next());
			// }

			/*
			 * Tag tag = mapper.convertValue(tree.get("quiz"), ref);
			 * System.out.println(tag.getName());
			 * System.out.println(tag.getValue());
			 * System.out.println(tag.getAttributes());
			 * System.out.println(tag.getSubTags().get(0).getName());
			 * System.out.println(tag.getSubTags().get(0).getValue());
			 */
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
