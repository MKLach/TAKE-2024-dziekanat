package pl.kurs.s11dziekanat.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
	
	
	@ManyToMany(mappedBy = "przedmioty")
	private Set<Prowadzacy> prowadzacy;

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

	public void addProwadzacy(Prowadzacy prowadzacy) {
		
		if(this.prowadzacy == null) {
			this.prowadzacy = new HashSet<Prowadzacy>();
		}
		
		this.prowadzacy.add(prowadzacy);
		
	}
	
	public void removeProwadzacy(Prowadzacy prowadzacy) {
		
		if(this.prowadzacy == null) {
			this.prowadzacy = new HashSet<Prowadzacy>();
			return;
		}
		
		this.prowadzacy.remove(prowadzacy);
		
	}

	public void setProwadzacy(Set<Prowadzacy> prowadzacy) {
		this.prowadzacy = prowadzacy;
	}

	public Set<Prowadzacy> getProwadzacy(){
		
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
