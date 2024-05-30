package pl.kurs.s11dziekanat.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListDto {
	private List<PrzedmiotDto> przedmioty = new ArrayList<PrzedmiotDto>();

	public ListDto(List<PrzedmiotDto> values) {
		super();
		this.przedmioty = new ArrayList<PrzedmiotDto>(values);
		System.out.println(values.size());
		
	}

	public ListDto() {	}
	
	public List<PrzedmiotDto> getValues() {
		return przedmioty;
	}

	public void setValues(List<PrzedmiotDto> values) {
		this.przedmioty = values;
	}
}
