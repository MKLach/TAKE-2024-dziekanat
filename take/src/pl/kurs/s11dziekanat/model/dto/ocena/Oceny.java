package pl.kurs.s11dziekanat.model.dto.ocena;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;

@XmlRootElement
public class Oceny {
	private List<OcenaDto> prowadzacy = new ArrayList<OcenaDto>();

	public Oceny(List<OcenaDto> m) {
		super();
		this.prowadzacy = new ArrayList<OcenaDto>(m);
		
	}

	public Oceny() {	}
	
	public List<OcenaDto> getValues() {
		return prowadzacy;
	}

	public void setValues(List<OcenaDto> values) {
		this.prowadzacy = values;
	}
}
