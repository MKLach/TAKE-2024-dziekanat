package pl.kurs.s11dziekanat.model.dto.student;

import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyPrzedmiotDto;

@XmlRootElement(name="sprzedmiot")
public class ZapisNaPrzedmiot extends ProwadzacyPrzedmiotDto{

	public ZapisNaPrzedmiot() {
		
	}
	
	public ZapisNaPrzedmiot(ProwadzacyPrzedmiot pp) {
		super(pp);
	}
	
}
