package pl.kurs.s11dziekanat.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Oceny")

public class Ocena {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="oceny_seq")
	@SequenceGenerator(name="oceny_seq", sequenceName="_oceny_seq", initialValue=0)
	@Column(name = "ocena_id")
	private Long id;
	
	@Column(name="ocena")
	private double ocena;
	
	@Column(name="komentarz", length = 350)
	private String komentarz;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="prowadzacy_id")
	private Prowadzacy prowadzacy;
	
	@ManyToOne
	@JoinColumn(name="przedmiot_id")
	private Przedmiot przedmiot;
	
	
	@Column(name = "data_wystawienia")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataWystawienia;
	
	public Ocena() {
		
	}

	public Ocena(Long id, double ocena, String komentarz, Date dataWystawienia) {
		super();
		this.id = id;
		this.ocena = ocena;
		this.komentarz = komentarz;
		this.dataWystawienia = dataWystawienia;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public String getKomentarz() {
		return komentarz;
	}

	public void setKomentarz(String komentarz) {
		this.komentarz = komentarz;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Prowadzacy getProwadzacy() {
		return prowadzacy;
	}

	public void setProwadzacy(Prowadzacy prowadzacy) {
		this.prowadzacy = prowadzacy;
	}

	public Przedmiot getPrzedmiot() {
		return przedmiot;
	}

	public void setPrzedmiot(Przedmiot przedmiot) {
		this.przedmiot = przedmiot;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataWystawienia() {
		return dataWystawienia;
	}

	public void setDataWystawienia(Date dataWystawienia) {
		this.dataWystawienia = dataWystawienia;
	}
	
	@PrePersist	
	public void prePersist(){
		if(this.dataWystawienia == null){
			this.dataWystawienia = new Date();
		}
		
	}
	
	@PreUpdate
	public void preUpdate(){
		this.komentarz = "" + dataWystawienia.toString() + "\n"+ this.komentarz;
		this.dataWystawienia = new Date();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, komentarz, ocena, prowadzacy, przedmiot, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ocena other = (Ocena) obj;
		return Objects.equals(id, other.id) && Objects.equals(komentarz, other.komentarz)
				&& Double.doubleToLongBits(ocena) == Double.doubleToLongBits(other.ocena)
				&& Objects.equals(prowadzacy, other.prowadzacy) && Objects.equals(przedmiot, other.przedmiot)
				&& Objects.equals(student, other.student);
	}

	@Override
	public String toString() {
		return "Ocena [id=" + id + ", ocena=" + ocena + ", komentarz=" + komentarz + ", student=" + student.getNazwisko()
				+ ", prowadzacy=" + prowadzacy + ", przedmiot=" + przedmiot + "]";
	}
	
	
	
}
