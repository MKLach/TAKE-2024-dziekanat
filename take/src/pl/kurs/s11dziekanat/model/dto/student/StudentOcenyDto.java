package pl.kurs.s11dziekanat.model.dto.student;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.ocena.PrzedmiotOcentDto;

@XmlRootElement(name="student")
public class StudentOcenyDto extends StudentDto {

	
	List<PrzedmiotOcentDto> przedmioty = new ArrayList<PrzedmiotOcentDto>();
	
	public StudentOcenyDto() {
		
	}
	
	public StudentOcenyDto(Student s) {
		super(s);
	}
	
	
	public void addPrzedmiotAndOceny(PrzedmiotOcentDto o){
		this.przedmioty.add(o);
	}
	
	@XmlElementWrapper(name = "przedmioty")
    @XmlElement(name = "przedmiot")
	public List<PrzedmiotOcentDto> getPrzedmioty() {
		return przedmioty;
	}

	public void setPrzedmioty(List<PrzedmiotOcentDto> przedmioty) {
		this.przedmioty = przedmioty;
	}

	
	
	
}
