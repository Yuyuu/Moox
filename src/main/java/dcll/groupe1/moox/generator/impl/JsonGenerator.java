package dcll.groupe1.moox.generator.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.generator.GeneratorException;
import dcll.groupe1.moox.generator.GeneratorInterface;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.impl.XmlParser;

/**
 * Class to generate a JSON text from a Tag element.
 * @author SERIN Kévin
 *
 */
public class JsonGenerator implements GeneratorInterface {
	private org.codehaus.jackson.JsonGenerator generator;	//jackson generator
	private StringWriter out;								//out buffer
	
	/**
	 * Constructs a new JSON generator.
	 * @param indent Indicates if the generator must indent the parsing result or not (false by default).
	 */
	public JsonGenerator(boolean indent) {
		JsonFactory factory = new JsonFactory();
		this.out = new StringWriter();
		try {
			this.generator = factory.createJsonGenerator(out);
			
			if(indent)
				this.generator.setPrettyPrinter(new DefaultPrettyPrinter());
		} catch (IOException e) {
			this.generator = null;
		}
	}
	
	/**
	 * Constructs a new JSON generator.
	 */
	public JsonGenerator(){
		this(false);
	}

	/**
	 * Generates a JSON text from a Tag tree.
	 * @param root The tree to parse.
	 * @return A string representing the generated JSON.
	 * @throws GeneratorException If an error occurred during the generation.
	 */
	public String generate(Tag root) throws GeneratorException {
		if(this.generator == null)
			throw new GeneratorException("Impossibile to initialize the jackson generator");
		
		/* start the document */
		try {
			ArrayList<Pair<Tag, Integer>> stack = new ArrayList<Pair<Tag,Integer>>(); //stack to course the tag tree: (node, level)
			stack.add(new Pair<Tag, Integer>(root, 0)); //root is level 0 in the tree
			
			int lastLevel = -1;
			while(!stack.isEmpty()){
				/* pop */
				Pair<Tag, Integer> element = stack.get(0);
				Tag node = element.getLeft();
				int level = element.getRight();
				stack.remove(0);
				
				/* Add sons to the top of the stack */
				ArrayList<Tag> subNodes = node.getSubTags();
				for(int i = subNodes.size()-1; i >= 0; i--){
					stack.add(0, new Pair<Tag, Integer>(subNodes.get(i), level+1));
				}
				
				/* We have to close opened array in the document if the level decrease */
				for(int i = 0; i <= lastLevel - level; i++){
					this.closeSubTagArray();
				}
				lastLevel = level;
				
				/* start a new object */
				this.startNode(node.getName());
				
				/* Write the text field */
				if(node.getValue() == null || node.getValue().isEmpty())
					this.generator.writeNullField("text");
				else
					this.generator.writeStringField("text", node.getValue());
//				System.out.println("\"text\":\""+node.getValue()+"\"");
				
				/* Write attributes */
				this.writeAttributes(node);
				
				/* Start the array for subtags */
				this.startSubTag();
			}
			
			/* Close the unclosed node */
			for(int i=0; i <= lastLevel; i++){
				this.closeSubTagArray();
			}
			
			this.generator.close();
		} catch (JsonGenerationException e) {
			throw new GeneratorException(e);
		} catch (IOException e) {
			throw new GeneratorException(e);
		}
		
		return this.out.toString();
	}
	
	/*
	 * Closes a subTags array in the JSON document.
	 */
	private void closeSubTagArray() throws JsonGenerationException, IOException{
		/* {
			"example":
				{
					"text":null,
					"attributes":[],
					"subTags":
					[
					] --> array to close
				} --> tag to close
			} --> node to close
		]
		 */
		
		this.generator.writeEndArray();	//close the array
		this.generator.writeEndObject(); //close the tag
		this.generator.writeEndObject(); //close the node
		
//		System.out.println("]");
//		System.out.println("}");
//		System.out.println("}");
	}
	
	/*
	 * Starts a new node in the JSON document.
	 */
	private void startNode(String nodeName) throws JsonGenerationException, IOException{
		/*
		 * { --> start node
		 * 		"tag": --> node name
		 * 		{ --> start tag
		 * 			...
		 */
		
		this.generator.writeStartObject(); //start node
		this.generator.writeFieldName(nodeName); //node name
		this.generator.writeStartObject(); //start tag
		
//		System.out.println("{");
//		System.out.println("\""+nodeName+"\":");
//		System.out.println("{");
	}
	
	/*
	 * Starts a new subTags element in the JSON document.
	 */
	private void startSubTag() throws JsonGenerationException, IOException{
		/*
		 * "subtags":
		 * [
		 * 		...
		 */
		
		this.generator.writeFieldName("subTags");
		this.generator.writeStartArray();
		
//		System.out.println("\"subTags\":");
//		System.out.println("[");
	}
	
	/*
	 * Writes attributes in the JSON document.
	 */
	private void writeAttributes(Tag node) throws JsonGenerationException, IOException{
		/*
		 * "attributes":
		 * [
		 * 		"atttribute" : "value",
		 * 		...
		 * ]
		 */
		
		this.generator.writeFieldName("attributes");
		this.generator.writeStartArray();
		
//		System.out.println("\"attributes:\"");
//		System.out.println("[");
		
		ArrayList<Attribute> attributes = node.getAttributes();
		for(Attribute attribute : attributes){
			this.generator.writeStartObject();
			this.generator.writeStringField(attribute.getName(), attribute.getValue());
			this.generator.writeEndObject();
			
//			System.out.println("{\""+attribute.getName()+"\":\""+attribute.getValue()+"\"}");
		}
		this.generator.writeEndArray();
		
//		System.out.println("]");
	}
	
	
	/*public static void main(String[] args){
		String uriIn = "file:///C:/Users/K%C3%A9vin/Desktop/quiz-moodle-exemple.xml";
		String uriOut = "file:///C:/Users/K%C3%A9vin/Desktop/result.json";
		
		
		try {
			XmlParser p = new XmlParser();
			Tag root = p.parse(new URI(uriIn));
			
			String test = new JsonGenerator(true).generate(root);
			FileWriter writer = new FileWriter(new File(new URI(uriOut)));
			writer.write(test);
			writer.close();
			System.out.println(test);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneratorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
