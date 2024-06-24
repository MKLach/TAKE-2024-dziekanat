package pl.kurs.s11dziekanat.model.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Semestr;

@XmlRootElement(name="rokisemestr")
public class RokSemestrDto {

	Integer rok;
	String semestr;
	
	public RokSemestrDto() {
		
	}

	public RokSemestrDto(ProwadzacyPrzedmiot pp) {
		this(ProwadzacyPrzedmiot.getYearFromDate(pp.getRok()), pp.getSemestr());
	}
	
	public RokSemestrDto(Integer rok, Semestr semestr) {
		super();
		this.rok = rok;
		this.semestr = semestr.name().toLowerCase();
		
	}
	
	@XmlAttribute
	public Integer getRok() {
		return rok;
	}
	public void setRok(Integer rok) {
		this.rok = rok;
	}
	
	@XmlAttribute
	public String getSemestr() {
		return semestr;
	}
	public void setSemestr(String semestr) {
		this.semestr = semestr;
	}
	
}
