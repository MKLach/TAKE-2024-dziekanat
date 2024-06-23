package pl.kurs.s11dziekanat.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.Semestr;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.ProwadzacySimpleDto;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotDto;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotExtendedDto;
import pl.kurs.s11dziekanat.utils.QuickCast;

@Stateless
public class PrzedmiotEjb {


	public static final Function<ProwadzacyPrzedmiot, ProwadzacySimpleDto> __F__ = new Function<ProwadzacyPrzedmiot, ProwadzacySimpleDto>() {
		
		@Override
		public ProwadzacySimpleDto apply(ProwadzacyPrzedmiot t) {
			
			return new ProwadzacySimpleDto(t.getProwadzacy());
		}
	};

	
	@PersistenceContext(name="dziekanat")
	EntityManager entityManager;

	
	
	public PrzedmiotEjb() {
		
	}
	
	public List<Przedmiot> findAll() {
		
		TypedQuery<Przedmiot> query = entityManager.createQuery("SELECT p FROM Przedmioty p", Przedmiot.class);
		
		return query.getResultList();
		
	}
	

	public Przedmiot create(PrzedmiotDto przedmiot) {
	
		Przedmiot p = new Przedmiot();
		
		p.setNazwa(przedmiot.getNazwa());
		
		entityManager.persist(p);
		
		return p;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public boolean delete(PrzedmiotDto p) {
		
		return delete(p.getId());
	}

	public boolean delete(Przedmiot p) {
		
		try{
		
			entityManager.remove(p);
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean delete(Long id) {	
		try{
			return delete(find(id).orElseThrow(new PrzedmiotExcetion.Supply("Could not find przedmiot with id " + id )));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Przedmiot update(Long id, Przedmiot p) throws PrzedmiotExcetion {
		
		Przedmiot org = this.find(id).orElseThrow(new PrzedmiotExcetion.Supply("Could not find przedmiot with id " + p.getId()));
		
		org.setNazwa(p.getNazwa());
		
		org = entityManager.merge(org);
		
		return org;

	}

	public Przedmiot update(PrzedmiotDto p) throws Exception {
		System.out.println(p);
		
		return update(p.getId(), new Przedmiot(p.getId(), p.getNazwa()));
		
	}

	public Optional<Przedmiot> find(Long id) {
		try {
			return Optional.of(entityManager.find(Przedmiot.class, id));
		} catch (Exception e) {
			return Optional.empty();
		}
		
	}
	
	public Optional<Przedmiot> find(PrzedmiotDto dto) {
		
		return find(dto.getId());
	}
	
	
	public List<Przedmiot> find(String nazwa) throws PrzedmiotExcetion {
		
		TypedQuery<Przedmiot> query = entityManager.createQuery("SELECT p FROM Przedmioty p where nazwa=:nazwa", Przedmiot.class);
		query.setParameter("nazwa", nazwa);
		
		List<Przedmiot> l = query.getResultList();

		if(l.size() == 0){
			throw new PrzedmiotExcetion("no przedmiots with given name");
		}
		
		return l;
	}
	
	public PrzedmiotExtendedDto findExtended(String nazwa, final Integer rok, final Semestr s) throws PrzedmiotExcetion {
		
		Optional<Przedmiot> przedmiotO = this.findOpt(nazwa);
		
		Przedmiot przedmiot = przedmiotO.orElseThrow(new PrzedmiotExcetion.Supply());
		
		QuickCast<List, List, ProwadzacyPrzedmiot, ProwadzacySimpleDto> qc = new QuickCast<List, List, ProwadzacyPrzedmiot, ProwadzacySimpleDto>(__F__);
		
		List<ProwadzacySimpleDto> prowadzacy = qc.castUsingAdv(przedmiot.getProwadzacy().stream().filter(new Predicate<ProwadzacyPrzedmiot>() {
			@Override
			public boolean test(ProwadzacyPrzedmiot pp) {
				
				return ProwadzacyPrzedmiot.getYearFromDate(pp.getRok()).equals(rok) && pp.getSemestr().equals(s);
			}
		}).collect(Collectors.toList()), new ArrayList<ProwadzacySimpleDto>());
		
		
		
		if(prowadzacy.size() == 0){
			throw new PrzedmiotExcetion("przedmiot nie byl prowadzony w danym semstrze i/lub roku!");
		}
		
		
		return new PrzedmiotExtendedDto(przedmiot, rok, s, prowadzacy);
	}
	
	
	public Przedmiot findOrCreate(String nazwa) throws PrzedmiotExcetion {
		
		TypedQuery<Przedmiot> query = entityManager.createQuery("SELECT p FROM Przedmioty p where nazwa=:nazwa", Przedmiot.class);
		query.setParameter("nazwa", nazwa);
		
		List<Przedmiot> l = query.getResultList();

		if(l.size() == 0){
			Przedmiot p = new Przedmiot();
			p.setNazwa(nazwa);
			
			entityManager.persist(p);
			
			return p;
			
		}
		
		return l.get(0);
	}
	
	public Optional<Przedmiot> findOpt(String nazwa) {
		
		
		try {
			
			List<Przedmiot> l = this.find(nazwa);
			return Optional.of(l.get(0));
			
		} catch (PrzedmiotExcetion e) {
			return Optional.empty();
		}
		
		
	}
	
	
	public List<PrzedmiotDto> cast(List<Przedmiot> przedmiots){
		
		List<PrzedmiotDto> l = new ArrayList<PrzedmiotDto>();
		for(Przedmiot o : przedmiots){
			
			l.add(new PrzedmiotDto((Przedmiot)o));
		}
		return l;
		
	}
	
}
