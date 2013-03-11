package dcll.groupe1.moox.domain;

import java.util.ArrayList;

/**
 * Represents a node of a XML file.
 * 
 * @author ?
 */
public class Tag {

	/**
	 * Name of the node.
	 */
	private String name;
	
	/**
	 * Value of the node.
	 */
	private String value;
	
	/**
	 * List of attributes of the node.
	 */
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	
	/**
	 * List of the descendant nodes.
	 */
	private ArrayList<Tag> subTags = new ArrayList<Tag>();
	
	/**
	 * Adds an Attribute to the list.
	 * 
	 * @param a
	 * 			the Attribute to add
	 * 
	 * @see Tag#attributes
	 */
	public void addAttribute(Attribute a) {
		this.attributes.add(a);
	}

	/**
	 * Adds a descendant Tag to the list.
	 * 
	 * @param t
	 * 			the Tag to add
	 * 
	 * @see Tag#subTags
	 */
	public void addTag(Tag t) {
		this.subTags.add(t);
	}

	/**
	 * Removes a given Attribute from the list.
	 * 
	 * @param a
	 * 			the Attribute to seek and remove
	 * 
	 * @see Tag#attributes
	 */
	public void removeAttribute(Attribute a) {
		this.attributes.remove(a);
	}

	/**
	 * Removes a given Tag from the list.
	 * 
	 * @param t
	 * 			the Tag to seek and remove
	 * 
	 * @see Tag#subTags
	 */
	public void removeTag(Tag t) {
		this.subTags.remove(t);
	}

	/**
	 * Removes all the attributes of the Tag
	 */
	public void clearAttribute() {
		this.attributes.clear();
	}

	/**
	 * Removes all the descendant tags of the Tag
	 */
	public void clearTag() {
		this.subTags.clear();
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			Tag t = (Tag) obj;
			return t.attributes.equals(this.attributes)
					&& t.subTags.equals(this.subTags)
					&& t.name.equals(this.name);
		} else {
			return false;
		}
	}

	/**
	 * @return
	 * 			the name of the Tag
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * 			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * 			the Attribute list of the Tag
	 */
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @return
	 * 			the descendant tags of the Tag
	 */
	public ArrayList<Tag> getSubTags() {
		return subTags;
	}

	/**
	 * @return
	 * 			the value of the Tag
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 * 			the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
