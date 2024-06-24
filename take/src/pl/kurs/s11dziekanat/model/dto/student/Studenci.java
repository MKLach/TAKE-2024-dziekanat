package pl.kurs.s11dziekanat.model.dto.student;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Studenci {
	private List<StudentDto> prowadzacy = new ArrayList<StudentDto>();

	public Studenci(List<StudentDto> m) {
		super();
		this.prowadzacy = new ArrayList<StudentDto>(m);
		
	}

	public Studenci() {	}
	
	public List<StudentDto> getValues() {
		return prowadzacy;
	}

	public void setValues(List<StudentDto> values) {
		this.prowadzacy = values;
	}
}
