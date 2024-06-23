package pl.kurs.s11dziekanat.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.student.StudentDto;

public class StudentEjb {
	@PersistenceContext(name="dziekanat")
	EntityManager entityManager;

	public StudentEjb() {
		
	}
	
	
	public List<Student> findAll() {
		
		TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s", Student.class);
		
		return query.getResultList();
		
	}
	
	public Student create(StudentDto student) {
		
		Student s = new Student();
		
		s.setImie(student.getImie());
		s.setNazwisko(student.getNazwisko());
		s.setDataUrodzenia(student.getDataUrodzenia());
		
		entityManager.persist(s);
		
		return s;
	}
	
	public boolean delete(StudentDto p) {
		
		return delete(p.getNr_albumu());
	}
	
	public boolean delete(Long id) {
		try{
			
			delete(this.find(id).get());
			
		} catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	public boolean delete(Student p) {
		try{
			
			entityManager.remove(p);
			
		} catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	
	public Optional<Student> find(Long id){
		return Optional.ofNullable(entityManager.find(Student.class, id));
	}
	
	
	public Student update(Long id, StudentDto p) throws PrzedmiotExcetion {
		
		Student org = this.find(id).orElseThrow(new PrzedmiotExcetion.Supply("Could not find przedmiot with id " + p.getNr_albumu()));
		
		org.setImie(p.getImie());
		
		
		
		org.setNazwisko(p.getNazwisko());
		
		
		
		org.setDataUrodzenia(p.getDataUrodzenia());
		
		
		org = entityManager.merge(org);
		
		return org;

	}
	
	public List<Student> findAllWildCardSupport(
			String imie,
            String nazwisko 
            ) {

        StringBuilder queryString = new StringBuilder("SELECT e FROM Prowadzacy e");
        
      
        queryString.append(" WHERE 1=1");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (imie != null && !imie.trim().isEmpty()) {
            queryString.append(" AND e.imie LIKE :imie");
            parameters.put("imie", "%"+imie+"%");
        }
        if (nazwisko != null && !nazwisko.trim().isEmpty()) {
            queryString.append(" AND e.nazwiskoand is  LIKE :nazwisko");
            parameters.put("nazwisko", "%"+nazwisko+"%");
        }
       
        TypedQuery<Student> query = entityManager.createQuery(queryString.toString(), Student.class);
        
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        System.out.println("Generated Query: " + queryString.toString());
        System.out.println("Parameters: " + parameters);
        
       return query.getResultList();
	}
	
	
}

