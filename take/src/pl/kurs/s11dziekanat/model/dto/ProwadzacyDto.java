package pl.kurs.s11dziekanat.model.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;

@XmlRootElement(name = "prowadzacy")
public class ProwadzacyDto {

    private Long id;
    private String imie;
    private String nazwisko;
    private String tytul;
    private List<ProwadzacyPrzedmiotDto> przedmioty;

    public ProwadzacyDto() {
		
	}
    
    public ProwadzacyDto(Prowadzacy p) {
    	this.id = p.getId();
    	this.imie = p.getImie();
    	this.nazwisko = p.getNazwisko();
    	this.tytul = p.getTytul();
    	
    	Stream<ProwadzacyPrzedmiotDto> stream = p.getPrzedmioty().stream().map(new Function<ProwadzacyPrzedmiot, ProwadzacyPrzedmiotDto>() {

			@Override
			public ProwadzacyPrzedmiotDto apply(ProwadzacyPrzedmiot t) {
				
				return new ProwadzacyPrzedmiotDto(t);
			}
		});
    	
    	przedmioty = new ArrayList<ProwadzacyPrzedmiotDto>();
    	
    	stream.forEach(new Consumer<Object>() {

			@Override
			public void accept(Object t) {
				
				ProwadzacyPrzedmiotDto dto = (ProwadzacyPrzedmiotDto) t;
				
				przedmioty.add(dto);
				
			}
		});
    	
    }
    
    @XmlElementWrapper(name = "przedmioty")
    @XmlElement(name = "przedmiot")
    public List<ProwadzacyPrzedmiotDto> getPrzedmioty() {
        return przedmioty;
    }

    public void setPrzedmioty(List<ProwadzacyPrzedmiotDto> przedmioty) {
        this.przedmioty = przedmioty;
    }
    
    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    @XmlElement
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @XmlElement
    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

  
}
