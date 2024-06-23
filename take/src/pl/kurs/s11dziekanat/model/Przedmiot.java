package pl.kurs.s11dziekanat.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Przedmioty")
@Table(name = "Przedmioty")
public class Przedmiot {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="przedmioty_seq")
	@SequenceGenerator(name="przedmioty_seq", sequenceName="_przedmioty_seq", initialValue=0)
	@Column(name="przedmiot_id")
	private Long id;
	
	
	@Column(nullable = false, length = 50)
	private String nazwa;
	
	
	@OneToMany(mappedBy = "przedmiot")
	private Set<ProwadzacyPrzedmiot> prowadzacy;

	@OneToMany(mappedBy="przedmiot")
	private Set<Ocena> oceny;
	
	public Przedmiot() {
		
	}
	
	public Przedmiot(Long id, String nazwa) {
		super();
		this.id = id;
		this.nazwa = nazwa;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNazwa() {
		return nazwa;
	}


	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	
	
	public Set<Ocena> getOceny() {
		return oceny;
	}

	public void setOceny(Set<Ocena> oceny) {
		this.oceny = oceny;
	}
	
	public void addOcena(Ocena ocena){
		
		if(this.oceny == null){
			this.oceny = new HashSet<Ocena>();
		}
		
		oceny.add(ocena);
		
	}

	public void addProwadzacy(ProwadzacyPrzedmiot prowadzacy) {
		
		if(this.prowadzacy == null) {
			this.prowadzacy = new HashSet<ProwadzacyPrzedmiot>();
		}
		
		this.prowadzacy.add(prowadzacy);
		
	}
	
	public void removeProwadzacy(ProwadzacyPrzedmiot prowadzacy) {
		
		if(this.prowadzacy == null) {
			this.prowadzacy = new HashSet<ProwadzacyPrzedmiot>();
			return;
		}
		
		this.prowadzacy.remove(prowadzacy);
		
	}

	public void setProwadzacy(Set<ProwadzacyPrzedmiot> prowadzacy) {
		this.prowadzacy = prowadzacy;
	}

	public Set<ProwadzacyPrzedmiot> getProwadzacy(){
		
		return prowadzacy;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id, nazwa);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Przedmiot other = (Przedmiot) obj;
		return Objects.equals(id, other.id) && Objects.equals(nazwa, other.nazwa);
	}


	@Override
	public String toString() {
		
		
		return "Przedmiot [id=" + id + ", nazwa=" + nazwa + "]";
	}
	
	
	
}
