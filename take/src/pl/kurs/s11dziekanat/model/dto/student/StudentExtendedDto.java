package pl.kurs.s11dziekanat.model.dto.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyPrzedmiotDto;

@XmlRootElement(name="student")
public class StudentExtendedDto extends StudentDto {

	List<ZapisNaPrzedmiot> przedmioty = new ArrayList<ZapisNaPrzedmiot>();
	

	public StudentExtendedDto() {
		
	}
	
	
	public StudentExtendedDto(Student s, List<ZapisNaPrzedmiot> oceny) {
		super(s);
		przedmioty = new ArrayList<ZapisNaPrzedmiot>(oceny);
		
	}
	
	

	@XmlElementWrapper(name = "przedmiot")
    @XmlElement(name = "przedmioty")
	public List<ZapisNaPrzedmiot> getPrzedmioty() {
		return przedmioty;
	}


	public void setPrzedmioty(List<ZapisNaPrzedmiot> przedmioty) {
		this.przedmioty = przedmioty;
	}
	
	
	
}
