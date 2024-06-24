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
public class ProwadzacyBasicDto {

    private Long id;
    private String imie;
    private String nazwisko;
    private String tytul;
    
    public ProwadzacyBasicDto() {
		
	}
    
    public ProwadzacyBasicDto(Prowadzacy p) {
    	this.id = p.getId();
    	this.imie = p.getImie();
    	this.nazwisko = p.getNazwisko();
    	this.tytul = p.getTytul();
    }
    
    

    public ProwadzacyBasicDto(Long id, String imie, String nazwisko, String tytul, List<ProwadzacyPrzedmiotDto> przedmioty) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.tytul = tytul;
        
    }

    public ProwadzacyBasicDto(Long id, String imie, String nazwisko, String tytul) {
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

    
    @Override
    public String toString() {
        return "ProwadzacyDTO [id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko + ", tytul=" + tytul
                + "]";
    }
}
