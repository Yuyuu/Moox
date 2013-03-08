package dcll.groupe1.moox.parser.impl;

import java.io.File;

import java.io.IOException;

import java.net.URI;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.ParserInterface;

/**
 * Parse un fichier .json
 * 
 * @author Laurent
 * 
 */
public class JsonParser implements ParserInterface {
	Tag tag;
	Tag root;
	org.codehaus.jackson.JsonParser jParser;

	public JsonParser() {
		tag = new Tag();
		root = new Tag();
		try {

			JsonFactory jfactory = new JsonFactory();
			// récupération du fichier Json
			jParser = jfactory
					.createJsonParser(new File("MoodleXMLEnJson.json"));

			// Début du traitement Parser
			// parseTag();

			// Fin du traitement Parser
			jParser.close();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * todo
	 */
	public Tag parse(URI uri) throws ParserException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Se positionne sur le token suivant le goal
	 * 
	 * @param goal
	 *            token à atteindre
	 */
	public void goAfter(String goal) {
		try {
			while (jParser.getCurrentName() != goal) {
				jParser.nextToken();
			}
			jParser.nextToken();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * méthode de test sur nextTextValue()
	 */
	public void test() {
		try {
			System.out.println(jParser.nextTextValue());

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Parse un Tag json
	 * 
	 * @return le tag parsé
	 */
	public Tag parseTag() {
		try {
			String name = null;
			while (jParser.getCurrentToken() != JsonToken.VALUE_STRING) {
				System.out.println("token " + jParser.getText());
				jParser.nextToken();
			}
			name = jParser.getText();
			System.out.println("name " + name);
			if (jParser.getCurrentToken() == JsonToken.START_OBJECT) {
				tag.setName(name);
				System.out.println("Name TAG : " + tag.getName());
				goAfter("text");
				tag.setValue(jParser.getText());
				parseAttributes();
				// parseSubTags();
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * Parse un ensemble de sous-tags
	 */
	public void parseSubTags() {
		goAfter("subTags");
		Tag tmp;
		tmp = parseTag();
		while (!tmp.getName().equals("null")) {
			tag.addTag(tmp);
			tmp = parseTag();
		}
	}

	/**
	 * Parse un ensemble d'attributs
	 */
	public void parseAttributes() {
		try {
			goAfter("attributes");
			Attribute attribute = new Attribute();
			while (jParser.getCurrentToken() != JsonToken.END_ARRAY) {
				goAfter("{");
				attribute.setName(jParser.getCurrentName());
				jParser.nextToken();
				attribute.setValue(jParser.getCurrentName());
				tag.addAttribute(attribute);
				goAfter("}");
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JsonParser jp = new JsonParser();
		jp.test();
	}
}
