package pl.kurs.s11dziekanat.model.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.Przedmiot;

@XmlRootElement(name = "Prowadzacy")
public class ProwadzacyDto {

    private Long id;
    private String imie;
    private String nazwisko;
    private String tytul;
    private List<PrzedmiotDto> przedmioty;

    public ProwadzacyDto() {
		
	}
    
    public ProwadzacyDto(Prowadzacy p) {
    	this.id = p.getId();
    	this.imie = p.getImie();
    	this.nazwisko = p.getNazwisko();
    	this.tytul = p.getTytul();
    	
    	Stream<PrzedmiotDto> stream = p.getPrzedmioty().stream().map(new Function<Przedmiot, PrzedmiotDto>() {

			@Override
			public PrzedmiotDto apply(Przedmiot t) {
				
				return new PrzedmiotDto(t);
			}
		});
    	
    	przedmioty = new ArrayList<PrzedmiotDto>();
    	
    	stream.forEach(new Consumer<Object>() {

			@Override
			public void accept(Object t) {
				
				PrzedmiotDto dto = (PrzedmiotDto) t;
				
				przedmioty.add(dto);
				
			}
		});
    	
    	
    	
    }
    
    

    public ProwadzacyDto(Long id, String imie, String nazwisko, String tytul, List<PrzedmiotDto> przedmioty) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.tytul = tytul;
        this.przedmioty = przedmioty;
    }

    public ProwadzacyDto(Long id, String imie, String nazwisko, String tytul) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.tytul = tytul;
       
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

    @XmlElementWrapper(name = "przedmioty")
    @XmlElement(name = "przedmiot")
    public List<PrzedmiotDto> getPrzedmioty() {
        return przedmioty;
    }

    public void setPrzedmioty(List<PrzedmiotDto> przedmioty) {
        this.przedmioty = przedmioty;
    }

    @Override
    public String toString() {
        return "ProwadzacyDTO [id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko + ", tytul=" + tytul
                + ", przedmioty=" + przedmioty + "]";
    }
}
