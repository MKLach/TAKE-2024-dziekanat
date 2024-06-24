package pl.kurs.s11dziekanat.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Przedmiot;

@XmlRootElement(name="Przedmiot")
public class PrzedmiotRSDto extends PrzedmiotDto {

	List<RokSemestrDto> rokisemestr = new ArrayList<RokSemestrDto>();
	
	public PrzedmiotRSDto() {
		
	}
	
	public PrzedmiotRSDto(Przedmiot przedmiot) {
		super(przedmiot);
		
		for(ProwadzacyPrzedmiot pp : przedmiot.getProwadzacy()){
			rokisemestr.add(new RokSemestrDto(pp));
		}
		
	}
	
	@XmlElementWrapper(name = "prowadzony")
	@XmlElement(name = "rokisemestr")
	public List<RokSemestrDto> getRokISemestr() {
		return rokisemestr;
	}

}
