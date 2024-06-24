package pl.kurs.s11dziekanat.ejb;

import pl.kurs.s11dziekanat.model.Semestr;

public class Key1234 {
	String nazwa;
	Integer rok;
	Semestr sem;
	
	String prowadzacy;
	
	public Key1234(String nazwa, Integer rok, Semestr sem, String prowadzacy) {
		super();
		this.nazwa = nazwa;
		this.rok = rok;
		this.sem = sem;
		this.prowadzacy = prowadzacy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nazwa == null) ? 0 : nazwa.hashCode());
		result = prime * result + ((rok == null) ? 0 : rok.hashCode());
		result = prime * result + ((sem == null) ? 0 : sem.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key1234 other = (Key1234) obj;
		if (nazwa == null) {
			if (other.nazwa != null)
				return false;
		} else if (!nazwa.equals(other.nazwa))
			return false;
		if (rok == null) {
			if (other.rok != null)
				return false;
		} else if (!rok.equals(other.rok))
			return false;
		if (sem != other.sem)
			return false;
		return true;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public Integer getRok() {
		return rok;
	}
	public void setRok(Integer rok) {
		this.rok = rok;
	}
	public Semestr getSem() {
		return sem;
	}
	public void setSem(Semestr sem) {
		this.sem = sem;
	}
	public String getProwadzacy() {
		return prowadzacy;
	}
	public void setProwadzacy(String prowadzacy) {
		this.prowadzacy = prowadzacy;
	}
	
	
	
	
}
