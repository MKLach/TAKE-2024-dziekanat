package pl.kurs.s11dziekanat.model.dto.ocena;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import pl.kurs.s11dziekanat.model.DateAdapter;
import pl.kurs.s11dziekanat.model.Ocena;

@XmlRootElement(name = "ocena")
public class OcenaDto {

    private Long id;
    private double ocena;
    private double waga;
    private String komentarz;
    private Date dataWystawienia;

    public OcenaDto() {}

    public OcenaDto(Ocena o) {
        this.id = o.getId();
        this.ocena = o.getOcena();
        this.waga = o.getWaga();
        this.komentarz = o.getKomentarz();
        this.dataWystawienia = o.getDataWystawienia();
    }

    public OcenaDto(double ocena, double waga, String komentarz, Date dataWystawienia) {
        this.ocena = ocena;
        this.waga = waga;
        this.komentarz = komentarz;
        this.dataWystawienia = dataWystawienia;
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    @XmlElement
    public double getWaga() {
        return waga;
    }

    public void setWaga(double waga) {
        this.waga = waga;
    }

    @XmlElement
    public String getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }

    @XmlElement
	 @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getDataWystawienia() {
        return dataWystawienia;
    }

    public void setDataWystawienia(Date dataWystawienia) {
        this.dataWystawienia = dataWystawienia;
    }
}