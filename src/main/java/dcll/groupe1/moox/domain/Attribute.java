package dcll.groupe1.moox.domain;

/**
 * Represents an attribute of a XML node
 * 
 * @author ?
 */
public class Attribute {

	/**
	 * The name of the Attribute.
	 */
	private String name;
	
	/**
	 * THe value of the Attribute
	 */
	private String value;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attribute) {
			Attribute a = (Attribute) obj;
			return a.name.equals(this.name) && a.value.equals(this.value);
		} else
			return false;
	}

	/**
	 * @return
	 * 			the name of the Attribute
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
	 * 			the value of the Attribute
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

	@Override
	public String toString() {
		return (" Attribut : " + this.name + "=\"" + this.value + "\" ");
	}

}
