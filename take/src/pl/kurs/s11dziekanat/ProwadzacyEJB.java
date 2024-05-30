package pl.kurs.s11dziekanat;

import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotDto;

@Stateless
public class ProwadzacyEJB {
	
	@PersistenceContext(name="dziekanat")
	EntityManager entityManager;

	public Prowadzacy create(ProwadzacyDto p) {
		System.out.println(p);
		
		Prowadzacy prowadzacy = new Prowadzacy(p.getId(), p.getImie(), p.getNazwisko(), p.getTytul());
		
		if(p.getPrzedmioty() == null){
			
			return prowadzacy;
		}
		
		List<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
		for(PrzedmiotDto przedmiotDto : p.getPrzedmioty()){
			
			
			
		}
		
		
		
		
		return null;
	}

	public boolean delete(ProwadzacyDto p) {
		
		return false;
	}

	public boolean delete(Prowadzacy p) {
		
		return false;
	}

	public boolean delete(Long id) {
		
		return false;
	}

	public Prowadzacy update(Prowadzacy p) {
		
		return null;
	}

	public Prowadzacy update(ProwadzacyDto p) {
		
		return null;
	}

	public Prowadzacy find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
