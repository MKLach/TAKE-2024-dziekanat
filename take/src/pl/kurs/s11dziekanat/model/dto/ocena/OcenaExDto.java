package pl.kurs.s11dziekanat.model.dto.ocena;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyBasicDto;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyPrzedmiotDto;
import pl.kurs.s11dziekanat.model.dto.student.StudentDto;

@XmlRootElement(name="ocena")
public class OcenaExDto extends OcenaDto {

	ProwadzacyPrzedmiotDto przedmiot;
	ProwadzacyBasicDto prowadzacy;
	StudentDto student;
	
	public OcenaExDto() {
		
	}
	
	public OcenaExDto(Ocena o) {
		super(o);
		this.przedmiot = new ProwadzacyPrzedmiotDto(o.getPrzedmiot());
		this.student = new StudentDto(o.getStudent());
		this.prowadzacy = new ProwadzacyBasicDto(o.getPrzedmiot().getProwadzacy());
	}
	
	
	@XmlElement
	public ProwadzacyPrzedmiotDto getPrzedmiot() {
		return przedmiot;
	}
	public void setPrzedmiot(ProwadzacyPrzedmiotDto przedmiot) {
		this.przedmiot = przedmiot;
	}
	@XmlElement
	public ProwadzacyBasicDto getProwadzacy() {
		return prowadzacy;
	}
	public void setProwadzacy(ProwadzacyBasicDto prowadzacy) {
		this.prowadzacy = prowadzacy;
	}
	@XmlElement
	public StudentDto getStudent() {
		return student;
	}
	public void setStudent(StudentDto student) {
		this.student = student;
	}
	
	
	
	
}
