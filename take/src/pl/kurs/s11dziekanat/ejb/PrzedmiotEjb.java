package pl.kurs.s11dziekanat.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotDto;

@Stateless
public class PrzedmiotEjb {

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
			throw new PrzedmiotExcetion.Supply("no przedmiots with given name! " + nazwa).get();
		}
		
		return l;
	}
	
	
	public List<PrzedmiotDto> cast(List<Przedmiot> przedmiots){
		
		List<PrzedmiotDto> l = new ArrayList<PrzedmiotDto>();
		for(Przedmiot o : przedmiots){
			
			l.add(new PrzedmiotDto((Przedmiot)o));
		}
		return l;
		
	}
	
}
