package pl.kurs.s11dziekanat.model.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProwadzacyPrzedmiotyList {
	private Set<ProwadzacyPrzedmiotDto> przedmioty = new HashSet<ProwadzacyPrzedmiotDto>();

	public ProwadzacyPrzedmiotyList(Set<ProwadzacyPrzedmiotDto> values) {
		super();
		this.przedmioty = new HashSet<ProwadzacyPrzedmiotDto>(values);
		
	}

	public ProwadzacyPrzedmiotyList() {	}
	
	public Set<ProwadzacyPrzedmiotDto> getValues() {
		return przedmioty;
	}

	public void setValues(Set<ProwadzacyPrzedmiotDto> values) {
		this.przedmioty = values;
	}
}
