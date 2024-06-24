package pl.kurs.s11dziekanat.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.Semestr;
import pl.kurs.s11dziekanat.utils.QuickCast;

@XmlRootElement(name = "Przedmiot")
public class PrzedmiotExtendedDto extends PrzedmiotDto {


	
	private Integer rok;
	private List<ProwadzacySimpleDto> prowadzacy;
	private Semestr semestr;
	
	
	public PrzedmiotExtendedDto() {
		super();
	}
	
	public PrzedmiotExtendedDto(Przedmiot przedmiot, final Integer year, final Semestr semestr, List<ProwadzacySimpleDto> prowadzacy) {
		super(przedmiot);
		this.rok = year;
		this.semestr = semestr;
		this.prowadzacy = prowadzacy;
		
				/*
		przedmiot.getProwadzacy().stream().filter(new Predicate<ProwadzacyPrzedmiot>() {
			@Override
			public boolean test(ProwadzacyPrzedmiot pp) {
				
				return ProwadzacyPrzedmiot.getYearFromDate(pp.getRok()).equals(year);
			}
		}).collect(Collectors.toList());
		*/
		
		
	}
	
    @XmlElementWrapper(name = "prowadzacy")
    @XmlElement(name = "prowadzacy")
    public List<ProwadzacySimpleDto> getProwadzacy() {
        return prowadzacy;
    }
    @XmlElement(name="rok")
	public Integer getYear() {
		return rok;
	}
    
	public void setYear(Integer year) {
		this.rok = year;
	}

	public void setProwadzacy(List<ProwadzacySimpleDto> prowadzacy) {
		this.prowadzacy = prowadzacy;
	}

	@XmlElement
	public Semestr getSemestr() {
		return semestr;
	}

	public void setSemestr(Semestr semestr) {
		this.semestr = semestr;
	}
	
	@XmlElement
	public Integer getLcizbaProwadzacych() {
		return prowadzacy.size();
	}

	
}
