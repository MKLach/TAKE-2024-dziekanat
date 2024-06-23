package pl.kurs.s11dziekanat.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Prowadzacy")
public class Prowadzacy {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prowadzacy_seq")
	@SequenceGenerator(name="prowadzacy_seq", sequenceName="_prowadzacy_seq", initialValue=0)
	@Column(name="prowadzacy_id")
	private Long id;
	
	@Column(name = "imie", nullable = false, length = 30)
	private String imie;
	
	@Column(name = "nazwisko", nullable = false, length = 50)
	private String nazwisko;
	
	@Column(name = "tytul", length = 50)
	private String tytul;

	@OneToMany(mappedBy = "prowadzacy", fetch=FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.DETACH})
	private Set<ProwadzacyPrzedmiot> przedmioty;

	public Prowadzacy(){
		
	}
	
	public Prowadzacy(Long id, String imie, String nazwisko, String tytul) {
		super();
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.tytul = tytul;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getTytul() {
		return tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public Set<ProwadzacyPrzedmiot> getPrzedmioty() {
		return przedmioty;
	}

	public void setPrzedmioty(Set<ProwadzacyPrzedmiot> przedmioty) {
		this.przedmioty = przedmioty;
	}
	
	public void addPrzedmioty(ProwadzacyPrzedmiot przedmiot) {
		if(this.przedmioty == null) {
			this.przedmioty = new HashSet<ProwadzacyPrzedmiot>();
		}
		
		this.przedmioty.add(przedmiot);
		przedmiot.setProwadzacy(this);
		
	}
	
	public void removePrzedmioty(Przedmiot przedmioty) {
		if(this.przedmioty == null) {
			this.przedmioty = new HashSet<ProwadzacyPrzedmiot>();
			return;
		}
		
		this.przedmioty.remove(przedmioty);
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, imie, nazwisko, tytul);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prowadzacy other = (Prowadzacy) obj;
		return Objects.equals(id, other.id) && Objects.equals(imie, other.imie) && Objects.equals(nazwisko, other.nazwisko)
				&& Objects.equals(tytul, other.tytul);
	}

	@Override
	public String toString() {
		return "Prowadzacy [id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko + ", tytul=" + tytul
				+ ", przedmioty=" + przedmioty + "]";
	}
	
	
	
}
	
	