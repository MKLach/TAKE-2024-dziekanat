package pl.kurs.s11dziekanat.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pl.kurs.s11dziekanat.model.Przedmiot;

@XmlRootElement(name = "Przedmiot")
public class PrzedmiotDto {

    private Long id;
    private String nazwa;

    public PrzedmiotDto() {
    }

    public PrzedmiotDto(Przedmiot p) {
        this.id = p.getId();
        this.nazwa = p.getNazwa();
    }
    
    public PrzedmiotDto(Long id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return "PrzedmiotDTO [id=" + id + ", nazwa=" + nazwa + "]";
    }
}