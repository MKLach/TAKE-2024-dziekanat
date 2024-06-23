package pl.kurs.s11dziekanat.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Przedmioty {
	private List<PrzedmiotDto> przedmioty = new ArrayList<PrzedmiotDto>();

	public Przedmioty(List<PrzedmiotDto> values) {
		super();
		this.przedmioty = new ArrayList<PrzedmiotDto>(values);
		
	}

	public Przedmioty() {	}
	
	public List<PrzedmiotDto> getValues() {
		return przedmioty;
	}

	public void setValues(List<PrzedmiotDto> values) {
		this.przedmioty = values;
	}
}
