package dcll.groupe1.moox.parser.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import dcll.groupe1.moox.domain.Attribute;
import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.ParserInterface;

public class XmlParser implements ParserInterface {

	private Tag rootTag;

	public XmlParser() {
		this.rootTag = new Tag();
	}

	public Tag getTag() {
		return this.rootTag;
	}

	private ArrayList<Tag> parseSinceRoot(List<?> l) {

		Tag t;
		Attribute notreAttribut;
		ArrayList<Tag> tagList = new ArrayList<Tag>();

		for (Iterator<?> i = l.iterator(); i.hasNext();) {
			t = new Tag();
			Element courant = (Element) i.next();
			t.setName(courant.getName());
			if (!courant.getTextNormalize().equals(""))
				t.setValue(courant.getTextNormalize());
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

	private void addListTagInTag(Tag tag, ArrayList<Tag> l) {
		for (Iterator<Tag> i = l.iterator(); i.hasNext();) {
			Tag courant = (Tag) i.next();
			tag.addTag(courant);
		}
	}

	public void affiche() {
		ArrayList<Tag> alt = new ArrayList<Tag>();
		alt.add(rootTag);
		afficheListTags(alt, 0);
	}

	private void afficheListTags(ArrayList<Tag> l, int tabul) {
		for (Iterator<Tag> i = l.iterator(); i.hasNext();) {
			for (int j = 0; j < tabul; j++)
				System.out.print("      ");
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

	public Tag parse(URI uri) throws ParserException {
		Element root;
		Document doc = new Document();
		URL test = null;
		try {
			test = uri.toURL();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		SAXBuilder sxb = new SAXBuilder();
		try {

			doc = sxb.build(test);
		} catch (Exception e) {
		}
		root = doc.getRootElement();

		this.rootTag.setName(root.getName());
		addListTagInTag(rootTag, parseSinceRoot(root.getChildren()));
		return rootTag;
	}

}
