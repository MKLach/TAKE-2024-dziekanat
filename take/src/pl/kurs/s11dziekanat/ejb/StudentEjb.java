package pl.kurs.s11dziekanat.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.exception.StudentExcetion;
import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Semestr;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.student.StudentDto;
import pl.kurs.s11dziekanat.model.dto.student.StudentExtendedDto;
import pl.kurs.s11dziekanat.model.dto.student.ZapisNaPrzedmiot;

@Stateless
public class StudentEjb {
	
	@PersistenceContext(name="dziekanat")
	EntityManager entityManager;

	public StudentEjb() {
		
	}
	
	@EJB
	PrzedmiotEjb przedmiotyService;

	
	
	public List<Student> findAll() {
		
		TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Studenci s", Student.class);
		
		return query.getResultList();
		
	}
	
	public Student create(StudentDto student) throws StudentExcetion {
		
		if(student.getDataUrodzenia() == null){
			throw new StudentExcetion("null dataUrodzenia");
		}
		
		if(student.getImie() == null){
			throw new StudentExcetion("null imie");
		}
		
		if(student.getNazwisko() == null){
			throw new StudentExcetion("null nazwisko");
		}
		
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

        StringBuilder queryString = new StringBuilder("SELECT e FROM Studenci e");
        
      
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


	public Student zapiszNaPrzedmiot(Long id, ZapisNaPrzedmiot p) throws StudentExcetion, PrzedmiotExcetion {
		
		Student s = find(id).orElseThrow(new StudentExcetion.Supply("not found"));
		
		for(Ocena o : s.getOceny()){
			
			if(o.getPrzedmiot().getProwadzacyPrzedmiotId().equals(p.getProwadzacy_przedmiot_id())){
				throw new PrzedmiotExcetion("student jest juz zapisany na ten przedmiot!");
			}
			
		}
		
		if(p.getProwadzacy_przedmiot_id() != null){
			Ocena zapis = new Ocena(0d, -1d, "Jest to zapisz na przedmiot", null);
			zapis.setStudent(s);
			zapis.setPrzedmiot(przedmiotyService.findPPById(p.getProwadzacy_przedmiot_id()));
			entityManager.persist(zapis);
			s.addOcena(zapis);
			entityManager.merge(s);
		}
		
		return s;
	}

	public Student wypiszZPrzedmiotu(Long id, final ZapisNaPrzedmiot p) throws StudentExcetion{
		Student s = find(id).orElseThrow(new StudentExcetion.Supply("not found"));
		
		
		
		boolean result = s.getOceny().removeIf(new Predicate<Ocena>() {

			@Override
			public boolean test(Ocena o) {
				if(o.getPrzedmiot().getProwadzacyPrzedmiotId().equals(p.getProwadzacy_przedmiot_id())){
					entityManager.remove(o);
					return true;
					
				}
				return false;
			}
		});
		
		if(!result){	
			throw new StudentExcetion("student nie jest zapisany na ten przedmiot!");
		}
		
		entityManager.merge(s);
		
		return s;
	}

	public StudentExtendedDto findExt(Long id) throws StudentExcetion {
		Student s = find(id).orElseThrow(new StudentExcetion.Supply("not found"));
		
		List<ZapisNaPrzedmiot> l = new ArrayList<ZapisNaPrzedmiot>();
		
		for(Ocena o : s.getOceny()){
			if(o.getWaga() == -1 && o.getOcena() == 0){
				
				l.add(new ZapisNaPrzedmiot(o.getPrzedmiot()));
			}
			
		}
		
		return new StudentExtendedDto(s,l);
	}

	public List<Student> findAllByPrzedmiot(String nazwa, Integer rok, Semestr semestr) {
		
		
		 StringBuilder queryString = new StringBuilder("SELECT e FROM Studenci e JOIN e.oceny o JOIN o.przedmiot pp JOIN pp.przedmiot p");
	      
	        queryString.append(" WHERE 1=1");

	        Map<String, Object> parameters = new HashMap<String, Object>();

	       
            queryString.append(" AND pp.rok = :rok");
            parameters.put("rok", ProwadzacyPrzedmiot.getDateFromYear(rok));
	        
            queryString.append(" AND pp.semestr = :semestr");
            parameters.put("semestr", semestr);
	        
            queryString.append(" AND p.nazwa = :nazwa");
            parameters.put("nazwa", nazwa);
	        
            queryString.append(" AND o.waga = :waga");
            parameters.put("waga", -1.0);
            
            queryString.append(" AND o.ocena = :ocena");
            parameters.put("ocena", 0.0);
            
	        TypedQuery<Student> query = entityManager.createQuery(queryString.toString(), Student.class);
	        
	        for (Map.Entry<String, Object> param : parameters.entrySet()) {
	            query.setParameter(param.getKey(), param.getValue());
	        }

	       
		return query.getResultList();
	}
	
	public boolean isStudentEnlisted(Student s, Integer rok, Semestr semestr, String nazwa){
		
		for(Ocena o : s.getOceny()){
			
			if( 
				ProwadzacyPrzedmiot.getYearFromDate(o.getPrzedmiot().getRok()).equals(rok) &&
				o.getPrzedmiot().getPrzedmiot().getNazwa().equals(nazwa) &&
				o.getPrzedmiot().getSemestr().equals(semestr)
			){
				return true;
			}
		}
		return false;
	}
}

