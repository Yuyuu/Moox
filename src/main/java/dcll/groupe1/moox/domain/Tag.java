package dcll.groupe1.moox.domain;

import java.util.ArrayList;

public class Tag {
	
	private String name;
	private String value;
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	private ArrayList<Tag> subTags = new ArrayList<Tag>();
	
	public void addAttribute(Attribute a){
		this.attributes.add(a);
	}
	
	public void addTag(Tag t){
		this.subTags.add(t);
	}
	
	public void removeAttribute(Attribute a){
		this.attributes.remove(a);
	}
	
	public void removeTag(Tag t){
		this.subTags.remove(t);
	}
	
	public void clearAttribute(){
		this.attributes.clear();
	}
	
	public void clearTag(){
		this.subTags.clear();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Tag){
			Tag t = (Tag)obj;
			return t.attributes.equals(this.attributes) && t.subTags.equals(this.subTags) && t.name.equals(this.name);
		}
		else
			return false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}
	public ArrayList<Tag> getSubTags() {
		return subTags;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
