package pl.kurs.s11dziekanat.model.dto.student;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Student;

@XmlRootElement(name = "student")
public class StudentDto {

	
	private Long nr_albumu;
	
	private String imie;
	
	private String nazwisko;
	
	private Date dataUrodzenia;

	
	public StudentDto() {
	
	}
	
	public StudentDto(Student s) {
		nr_albumu = s.getId();
		imie = s.getImie();
		nazwisko = s.getNazwisko();
		dataUrodzenia = s.getDataUrodzenia();
	}
	
	@XmlElement
	public Long getNr_albumu() {
		return nr_albumu;
	}

	public void setNr_albumu(Long nr_albumu) {
		this.nr_albumu = nr_albumu;
	}
	
	@XmlElement
	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}
	
	@XmlElement
	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	
	@XmlElement
	public Date getDataUrodzenia() {
		return dataUrodzenia;
	}

	public void setDataUrodzenia(Date dataUrodzenia) {
		this.dataUrodzenia = dataUrodzenia;
	}

}