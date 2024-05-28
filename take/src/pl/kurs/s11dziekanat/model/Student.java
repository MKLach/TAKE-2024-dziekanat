package pl.kurs.s11dziekanat.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Studenci")
public class Student {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="studenci_seq")
	@SequenceGenerator(name="studenci_seq", sequenceName="_studenci_seq", initialValue=300000)
	@Column(name="student_id")
	private Long id;
	
	@Column(name = "imie", nullable = false, length = 30)
	private String imie;
	 
	@Column(name = "nazwisko", nullable = false, length = 50)
	private String nazwisko;
	
	@Column(name = "data_urodzenia")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUrodzenia;

	@OneToMany(mappedBy="student")
	private Set<Ocena> oceny;
	
	public Student() {
		
	}
	
	public Student(Long id, String imie, String nazwisko, Date date) {
		super();
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.dataUrodzenia = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Date getDataUrodzenia() {
		return dataUrodzenia;
	}

	
	public void setDataUrodzenia(Date dataUrodzenia) {
		this.dataUrodzenia = dataUrodzenia;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(dataUrodzenia, id, imie, nazwisko);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(dataUrodzenia, other.dataUrodzenia) && Objects.equals(id, other.id) && Objects.equals(imie, other.imie)
				&& Objects.equals(nazwisko, other.nazwisko);
	}

}
