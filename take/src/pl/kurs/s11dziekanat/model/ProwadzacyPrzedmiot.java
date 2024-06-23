package pl.kurs.s11dziekanat.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Prowadzacy_Przedmiot")
//@IdClass(ProwadzacyPrzedmiotId.class)
public class ProwadzacyPrzedmiot {

	/*public static class ProwadzacyPrzedmiotId implements Serializable {
		 
		/**
		 * 
		 *
		private static final long serialVersionUID = 3244120864300214997L;

		private Prowadzacy prowadzacy;
		
		private Przedmiot przedmiot;
		
		private Semestr semestr;
		
		private Date rok;

		public ProwadzacyPrzedmiotId() {
			
		}
		
		public ProwadzacyPrzedmiotId(Prowadzacy prowadzacy, Przedmiot przedmiot, Semestr semestr, Date rok) {
			super();
			this.prowadzacy = prowadzacy;
			this.przedmiot = przedmiot;
			this.semestr = semestr;
			this.rok = rok;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((prowadzacy == null) ? 0 : prowadzacy.hashCode());
			result = prime * result + ((przedmiot == null) ? 0 : przedmiot.hashCode());
			result = prime * result + ((rok == null) ? 0 : rok.hashCode());
			result = prime * result + ((semestr == null) ? 0 : semestr.hashCode());
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
			ProwadzacyPrzedmiotId other = (ProwadzacyPrzedmiotId) obj;
			if (prowadzacy == null) {
				if (other.prowadzacy != null)
					return false;
			} else if (!prowadzacy.equals(other.prowadzacy))
				return false;
			if (przedmiot == null) {
				if (other.przedmiot != null)
					return false;
			} else if (!przedmiot.equals(other.przedmiot))
				return false;
			if (rok == null) {
				if (other.rok != null)
					return false;
			} else if (!rok.equals(other.rok))
				return false;
			if (semestr != other.semestr)
				return false;
			return true;
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

		public Semestr getSemestr() {
			return semestr;
		}

		public void setSemestr(Semestr semestr) {
			this.semestr = semestr;
		}

		public Date getRok() {
			return rok;
		}

		public void setRok(Integer rok) {
			Calendar cal = Calendar.getInstance();
			cal.set(rok, 5, 6);
			this.rok = cal.getTime();
		}
	
		
		
	}
	*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prowadzacy_przedmiot_seq")
	@SequenceGenerator(name="prowadzacy_przedmiot_seq", sequenceName="_prowadzacy_przedmiot_seq", initialValue=0)
	@Column(name="prowadzacy_przedmiot_id")
	private Long prowadzacyPrzedmiotId;
	
    //@Id	
    @ManyToOne
    @JoinColumn(name = "prowadzacy_id")
    private Prowadzacy prowadzacy;
    
   // @Id
    @ManyToOne
    @JoinColumn(name = "przedmiot_id") 
    private Przedmiot przedmiot;
    
   // @Id
    private Semestr semestr;
    
    //@Id
    @Temporal(TemporalType.DATE)
    private Date rok;


	public ProwadzacyPrzedmiot() {
		
	}
	
	public ProwadzacyPrzedmiot(Prowadzacy prowadzacy, Przedmiot przedmiot, Semestr semestr, Integer rok) {
		this(prowadzacy, przedmiot, semestr, (Date)null);	
		setRok(rok);
		
	}
	
	
	private ProwadzacyPrzedmiot(Prowadzacy prowadzacy, Przedmiot przedmiot, Semestr semestr, Date rok) {
		super();
		this.prowadzacy = prowadzacy;
		this.przedmiot = przedmiot;
		this.semestr = semestr;
		this.rok = rok;
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

	public Semestr getSemestr() {
		return semestr;
	}

	public void setSemestr(Semestr semestr) {
		this.semestr = semestr;
	}

	public Date getRok() {
		return rok;
	}

	public void setRok(Integer rok) {
		Calendar cal = Calendar.getInstance();
		cal.set(rok, 5, 6);
		this.rok = cal.getTime();
	}
	
	public Long getProwadzacyPrzedmiotId() {
		return prowadzacyPrzedmiotId;
	}

	public void setProwadzacyPrzedmiotId(Long prowadzacyPrzedmiotId) {
		this.prowadzacyPrzedmiotId = prowadzacyPrzedmiotId;
	}

	@Override
	public String toString() {
		return "ProwadzacyPrzedmiot [przedmiot=" + przedmiot + ", semestr=" + semestr + ", rok=" + rok + "]";
	}
	
	
	public Integer mangled_name_temporal_rok_in(){
		return getYearFromDate(rok);
	}

	
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prowadzacy == null) ? 0 : prowadzacy.hashCode());
		result = prime * result + ((prowadzacyPrzedmiotId == null) ? 0 : prowadzacyPrzedmiotId.hashCode());
		result = prime * result + ((przedmiot == null) ? 0 : przedmiot.hashCode());
		result = prime * result + ((rok == null) ? 0 : rok.hashCode());
		result = prime * result + ((semestr == null) ? 0 : semestr.hashCode());
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
		ProwadzacyPrzedmiot other = (ProwadzacyPrzedmiot) obj;
		if (prowadzacy == null) {
			if (other.prowadzacy != null)
				return false;
		} else if (!prowadzacy.equals(other.prowadzacy))
			return false;
		if (prowadzacyPrzedmiotId == null) {
			if (other.prowadzacyPrzedmiotId != null)
				return false;
		} else if (!prowadzacyPrzedmiotId.equals(other.prowadzacyPrzedmiotId))
			return false;
		if (przedmiot == null) {
			if (other.przedmiot != null)
				return false;
		} else if (!przedmiot.equals(other.przedmiot))
			return false;
		if (rok == null) {
			if (other.rok != null)
				return false;
		} else if (!rok.equals(other.rok))
			return false;
		if (semestr != other.semestr)
			return false;
		return true;
	}

	public static Date getDateFromYear(int rok){
    	Calendar cal = Calendar.getInstance();
		cal.set(rok, 5, 6);
		return cal.getTime();
    }
	
	public static Integer getYearFromDate(Date rok){
    	Calendar cal = Calendar.getInstance();
		cal.setTime(rok);
		return cal.get(Calendar.YEAR);
    }
    
}