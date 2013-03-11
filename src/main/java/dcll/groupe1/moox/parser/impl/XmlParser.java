package dcll.groupe1.moox.parser.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.ParserInterface;

/**
 * Provides methods to parse a XML file into a Tag.
 * 
 * @see Tag
 * 
 * @author ?
 */
public class XmlParser implements ParserInterface {

	/**
	 * The root of the XML file.
	 */
	private Tag rootTag;

	public XmlParser() {
		this.rootTag = new Tag();
	}

	/**
	 * Parses the nodes of a XML file.
	 * 
	 * @param l
	 * 			list of the children Elements
	 * @return 
	 * 			the Tag list parsed from the XML file
	 */
	private ArrayList<Tag> parseSinceRoot(List<?> l) {

		Tag t;
		Attribute notreAttribut;
		ArrayList<Tag> tagList = new ArrayList<Tag>();

		for (Iterator<?> i = l.iterator(); i.hasNext();) {
			t = new Tag();
			Element courant = (Element) i.next();
			t.setName(courant.getName());
			if (!courant.getTextNormalize().equals("")) {
				t.setValue(courant.getTextNormalize());
			}
			for (Iterator<?> att = courant.getAttributes().iterator(); att
					.hasNext();) {
				notreAttribut = new Attribute();
				org.jdom2.Attribute attributJDOM = (org.jdom2.Attribute) att
						.next();

				notreAttribut.setName(attributJDOM.getName());
				notreAttribut.setValue(attributJDOM.getValue());
				t.addAttribute(notreAttribut);
			}
			addListTagInTag(t, parseSinceRoot(courant.getChildren()));
			tagList.add(t);
		}
		return tagList;
	}

	/**
	 * Adds a list of Tags in a Tag.
	 * 
	 * @param tag
	 * 			the Tag to add the list in
	 * @param l
	 * 			the list of Tags to add
	 */
	private void addListTagInTag(Tag tag, ArrayList<Tag> l) {
		for (Iterator<Tag> i = l.iterator(); i.hasNext();) {
			Tag courant = (Tag) i.next();
			tag.addTag(courant);
		}
	}

	/**
	 * Displays the Tag content of the XML parsed file.
	 */
	public void affiche() {
		ArrayList<Tag> alt = new ArrayList<Tag>();
		alt.add(rootTag);
		afficheListTags(alt, 0);
	}

	/**
	 * Displays of list of Tags.
	 * 
	 * @param l
	 * 			the list to display
	 * @param tabul
	 * 			number of tabulations to insert
	 */
	private void afficheListTags(ArrayList<Tag> l, int tabul) {
		for (Iterator<Tag> i = l.iterator(); i.hasNext();) {
			for (int j = 0; j < tabul; j++) {
				System.out.print("      ");
			}
			Tag tag = (Tag) i.next();
			System.out.print("Name: " + tag.getName() + " ");
			for (Iterator<Attribute> att = tag.getAttributes().iterator(); att
					.hasNext();) {
				Attribute attribut = (Attribute) att.next();
				System.out.print(attribut.toString());
			}
			System.out.println("Value : " + tag.getValue());
			afficheListTags(tag.getSubTags(), tabul + 1);
		}
	}

	/**
	 * @see ParserInterface#parse(URI)
	 */
	public Tag parse(URI uri) throws ParserException {
		Element root;
		Document doc = new Document();
		URL test = null;
		try {
			test = uri.toURL();
			SAXBuilder sxb = new SAXBuilder();
			doc = sxb.build(test);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		root = doc.getRootElement();

		this.rootTag.setName(root.getName());
		addListTagInTag(rootTag, parseSinceRoot(root.getChildren()));
		return rootTag;
	}

}
