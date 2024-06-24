package pl.kurs.s11dziekanat.model.dto.ocena;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlAttribute;

import pl.kurs.s11dziekanat.ejb.Key1234;
import pl.kurs.s11dziekanat.model.Ocena;

@XmlRootElement(name="przedmiot")
public class PrzedmiotOcentDto {

	String nazwa;
	Integer rok;
	String semestr;
	String prowadzacy;
	
	
	List<OcenaDto> oceny = new ArrayList<OcenaDto>();
	
	public PrzedmiotOcentDto() {
	
	}
	
	public PrzedmiotOcentDto(Key1234 pp, ArrayList<Ocena> arrayList) {
		this.nazwa = pp.getNazwa();
		this.rok = pp.getRok();
		this.semestr = pp.getSem().toString().toLowerCase();
		this.prowadzacy = pp.getProwadzacy();
		
		for(Ocena o : arrayList){
			oceny.add(new OcenaDto(o));
		}
	}

	@XmlAttribute
	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
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
	@XmlAttribute
	public String getProwadzacy() {
		return prowadzacy;
	}

	public void setProwadzacy(String prowadzacy) {
		this.prowadzacy = prowadzacy;
	}

	@XmlElementWrapper(name = "oceny")
    @XmlElement(name = "ocena")
	public List<OcenaDto> getOceny() {
		return oceny;
	}

	public void setOceny(List<OcenaDto> oceny) {
		this.oceny = oceny;
	}
	
	
	
}
