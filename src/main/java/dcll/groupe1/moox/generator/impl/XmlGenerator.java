package dcll.groupe1.moox.generator.impl;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.generator.GeneratorException;
import dcll.groupe1.moox.generator.GeneratorInterface;

/**
 * The class XmlGenerator provides the method to generate a XML formatted string
 * of a given tag.
 * @author Vincent
 */
public class XmlGenerator implements GeneratorInterface {

	public String generate(Tag root) throws GeneratorException {
		if (root == null)
			throw new GeneratorException("Pas d'élément racine, impossible de générer le fichier.");
		
		// XML document
		Document doc = new Document();
		
		// Root
		Element xmlRoot = new Element(root.getName()).setText(root.getValue());
		
		// Attributes of the root
		for (Attribute att : root.getAttributes())
			xmlRoot.setAttribute(att.getName(), att.getValue());
		
		// Generation of children nodes
		addDescendants(xmlRoot, root);
		
		// Add the root to the document
		doc.setRootElement(xmlRoot);
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());		
		return outputter.outputString(doc);
	}
	
	/**
	 * Recursively add the descendants of the given node
	 * @param node Current node
	 * @param content The content of the current node
	 */
	private void addDescendants(Element node, Tag content) {
		for (Tag tag : content.getSubTags()) {
			Element child = new Element(tag.getName()).setText(tag.getValue());
			
			for (Attribute att : tag.getAttributes())
				child.setAttribute(att.getName(), att.getValue());
			
			addDescendants(child, tag);
			node.addContent(child);
		}
	}
	
	/*public static void main(String args[]) {
		String fic = "file:////home/vincent/Dropbox/univ/master/s8/dcll/projet/Moox/MoodleXML.xml";
		String out = "file:////home/vincent/Bureau/moodGen.xml";
		
		try {
			XmlParser p = new XmlParser();
			Tag root = p.parse(new URI(fic));
			String result = new XmlGenerator().generate(root);
			FileWriter w = new FileWriter(new File(new URI(out)));
			w.write(result);
			w.close();
		} catch (GeneratorException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}*/

}
