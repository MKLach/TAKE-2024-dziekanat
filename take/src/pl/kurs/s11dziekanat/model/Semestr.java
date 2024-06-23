package pl.kurs.s11dziekanat.model;

public enum Semestr {
	ZIMOWY,
	LETNI,
	OBA;
	
	
	public static Semestr get(String nazwa){
		
		for(Semestr s : values()){
			if(s.name().toLowerCase().equals(nazwa.toLowerCase())){
				return s;
			}
		}
		
		return null;
	}
	
}
