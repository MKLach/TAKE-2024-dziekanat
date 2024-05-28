package pl.kurs.s11dziekanat.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Test {
	@XmlAttribute
	private String message;

	public Test(String message) {
		super();
		this.message = message;
	}

	public Test() {	}
	
	public String getMessage() {
		return message;
	}

	public void setCars(String message) {
		this.message = message;
	}
	
	
	
	
}
