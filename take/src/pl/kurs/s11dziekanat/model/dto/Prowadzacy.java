package pl.kurs.s11dziekanat.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Prowadzacy {
	private List<ProwadzacyDto> prowadzacy = new ArrayList<ProwadzacyDto>();

	public Prowadzacy(List<ProwadzacyDto> m) {
		super();
		this.prowadzacy = new ArrayList<ProwadzacyDto>(m);
		
	}

	public Prowadzacy() {	}
	
	public List<ProwadzacyDto> getValues() {
		return prowadzacy;
	}

	public void setValues(List<ProwadzacyDto> values) {
		this.prowadzacy = values;
	}
}
