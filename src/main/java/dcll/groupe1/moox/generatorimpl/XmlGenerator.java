package dcll.groupe1.moox.generatorimpl;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.generator.GeneratorException;
import dcll.groupe1.moox.generator.GeneratorInterface;

public class XmlGenerator implements GeneratorInterface {

	public String generate(Tag root) throws GeneratorException {
		if (root == null)
			throw new GeneratorException("Pas d'élément racine, impossible de générer le fichier.");
		
		// Le document XML
		Document doc = new Document();
		
		// La racine
		Element xmlRoot = new Element(root.getName()).setText(root.getValue());
		
		// Ajout des attributs de la racine attributs de la racine
		for (Attribute att : root.getAttributes())
			xmlRoot.setAttribute(att.getName(), att.getValue());
		
		// Génération récursive des fils
		addAllChilds(xmlRoot, root);
		
		// Ajout de la racine au document
		doc.setRootElement(xmlRoot);
		
		// Transformation en String
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());		
		return outputter.outputString(doc);
	}
	
	private void addAllChilds(Element node, Tag content) {
		for (Tag tag : content.getSubTags()) {
			Element child = new Element(tag.getName()).setText(tag.getValue());
			for (Attribute att : tag.getAttributes())
				child.setAttribute(att.getName(), att.getValue());
			addAllChilds(child, tag);
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
