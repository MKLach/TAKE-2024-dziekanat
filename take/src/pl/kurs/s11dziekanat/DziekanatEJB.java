package pl.kurs.s11dziekanat;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.Przedmiot;

@Stateless
public class DziekanatEJB {
	
	@PersistenceContext(name="komis")
	EntityManager entityManager;
	

	public void create(Prowadzacy p) {
		System.out.println("Creating prowadzacy!");
		System.out.println(entityManager);
		entityManager.persist(p);
	}
	
	
	public void createPrzedmiot(Przedmiot p) {
		System.out.println("Creating przedmiot!");
		System.out.println(entityManager);
		entityManager.persist(p);
	}


	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
}
