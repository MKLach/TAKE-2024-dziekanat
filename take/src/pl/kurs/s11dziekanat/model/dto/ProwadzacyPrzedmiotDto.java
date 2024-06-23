package pl.kurs.s11dziekanat.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;

@XmlRootElement(name = "przedmiot")
public class ProwadzacyPrzedmiotDto extends PrzedmiotDto {

	Long prowadzacy_przedmiot_id;
	int rok;
	String semestr;
    
    public ProwadzacyPrzedmiotDto() {
    }

    public ProwadzacyPrzedmiotDto(ProwadzacyPrzedmiot p) {
       super(p.getPrzedmiot());
       this.prowadzacy_przedmiot_id = p.getProwadzacyPrzedmiotId();
       this.semestr = p.getSemestr().toString().toLowerCase();
       this.rok = p.mangled_name_temporal_rok_in();
    }

    @XmlElement(nillable=false)
	public int getRok() {
		return rok;
	}

	public void setRok(int rok) {
		this.rok = rok;
	}

	@XmlElement(nillable=false)
	public String getSemestr() {
		return semestr;
	}

	public void setSemestr(String semestr) {
		this.semestr = semestr;
	}

	@Override
	public String toString() {
		return "ProwadzacyPrzedmiotDto [rok=" + rok + ", semestr=" + semestr + ", getId()=" + getId() + ", getNazwa()="
				+ getNazwa() + "]";
	}
	
	@XmlElement
	public Long getProwadzacy_przedmiot_id() {
		return prowadzacy_przedmiot_id;
	}

	public void setProwadzacy_przedmiot_id(Long prowadzacy_przedmiot_id) {
		this.prowadzacy_przedmiot_id = prowadzacy_przedmiot_id;
	}
	
	
    
    
    

}