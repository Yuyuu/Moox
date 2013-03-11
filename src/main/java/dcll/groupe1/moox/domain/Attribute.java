package dcll.groupe1.moox.domain;

public class Attribute {

	private String name;
	private String value;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attribute) {
			Attribute a = (Attribute) obj;
			return a.name.equals(this.name) && a.value.equals(this.value);
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return (" Attribut : " + this.name + "=\"" + this.value + "\" ");
	}

}
