package pl.kurs.s11dziekanat.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

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

	
	@ManyToMany
	@JoinTable(
			name = "Prowadz¹cy_Przedmioty", 
			joinColumns = @JoinColumn(name="prowadzacy_id", referencedColumnName = "prowadzacy_id"), 
			inverseJoinColumns = @JoinColumn(name="przedmiot_id", referencedColumnName = "przedmiot_id"))
	private Set<Przedmiot> przedmioty;
	
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

	public Set<Przedmiot> getPrzedmioty() {
		return przedmioty;
	}

	public void setPrzedmioty(Set<Przedmiot> przedmioty) {
		this.przedmioty = przedmioty;
	}
	
	public void addPrzedmioty(Przedmiot przedmiot) {
		if(this.przedmioty == null) {
			this.przedmioty = new HashSet<Przedmiot>();
		}
		
		this.przedmioty.add(przedmiot);
		przedmiot.addProwadzacy(this);
		
	}
	
	public void removePrzedmioty(Przedmiot przedmioty) {
		if(this.przedmioty == null) {
			this.przedmioty = new HashSet<Przedmiot>();
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
	
	