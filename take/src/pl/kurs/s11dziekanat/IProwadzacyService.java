package pl.kurs.s11dziekanat;

import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;

public interface IProwadzacyService {

	Prowadzacy create(ProwadzacyDto p);
	
	boolean delete(ProwadzacyDto p);
	
	boolean delete(Prowadzacy p);
	
	boolean delete(Long id);
	
	Prowadzacy update(Prowadzacy p);
	
	Prowadzacy update(ProwadzacyDto p);
	
	Prowadzacy find(Long id);
	
	
}
