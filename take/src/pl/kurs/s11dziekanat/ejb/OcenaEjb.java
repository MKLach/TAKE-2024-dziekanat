package pl.kurs.s11dziekanat.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.kurs.s11dziekanat.exception.OcenaExcetion;
import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.exception.StudentExcetion;
import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Semestr;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.Prowadzacy;
import pl.kurs.s11dziekanat.model.dto.ocena.OcenaDto;
import pl.kurs.s11dziekanat.model.dto.ocena.OcenaExDto;
import pl.kurs.s11dziekanat.model.dto.ocena.PrzedmiotOcentDto;
import pl.kurs.s11dziekanat.model.dto.student.StudentDto;
import pl.kurs.s11dziekanat.model.dto.student.StudentOcenyDto;

@Stateless
public class OcenaEjb {

	
	@PersistenceContext
    EntityManager entityManager;
	
	@EJB
	StudentEjb studentService;
	
	@EJB
	ProwadzacyEJB prowadzacyService;
	
	@EJB
	PrzedmiotEjb przedmiotService;

    public Ocena findById(Long id) {
        return entityManager.find(Ocena.class, id);
    }
    
    public OcenaDto dtofindById(Long id) {
        return new OcenaExDto(entityManager.find(Ocena.class, id));
    }

    public List<OcenaDto> findAll() {
    	StringBuilder queryString = new StringBuilder("SELECT o FROM Oceny o WHERE 1=1");
        Map<String, Object> parameters = new HashMap<String, Object>();

        queryString.append(" AND o.waga = :waga");
        parameters.put("waga", -1.0);
      
        queryString.append(" AND o.ocena = :ocena");
        parameters.put("ocena", 0.0);
	        
        TypedQuery<Ocena> query = entityManager.createQuery(queryString.toString(), Ocena.class);

        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        List<OcenaDto> res = new ArrayList<OcenaDto>();
        
        for(Ocena o : query.getResultList()){
        	
        	
        }
        
        return res;
    }

    public Ocena save(Long s_id, Long pp_id, OcenaDto ocena) throws OcenaExcetion, PrzedmiotExcetion, StudentExcetion {
    	Student s = studentService.find(s_id).orElseThrow(new StudentExcetion.Supply("student not found"));
    	ProwadzacyPrzedmiot pp = przedmiotService.findPPById(pp_id);
    	
    	if(!studentService.isStudentEnlisted(s, ProwadzacyPrzedmiot.getYearFromDate(
    			pp.getRok()),
    			pp.getSemestr(),
    			pp.getPrzedmiot().getNazwa())
    	){
    		throw new OcenaExcetion("student nie jest zpisany na ten przedmiot!");
    		
    	}
    	
    	Ocena n = new Ocena();
    	n.setKomentarz(ocena.getKomentarz());
    	n.setOcena(ocena.getOcena());
    	n.setWaga(ocena.getWaga());
    	n.setStudent(s);
    	n.setDataWystawienia(ocena.getDataWystawienia());
    	n.setPrzedmiot(pp);
    	
        entityManager.persist(n);
        
        s.addOcena(n);
        
        entityManager.merge(s);
        return n;
    }

   
    
    public StudentOcenyDto wszystkieOcenyStudenta(Long id) throws StudentExcetion{
		
    	Student s = studentService.find(id).orElseThrow(new StudentExcetion.Supply("student not found"));
    	
    	HashMap<Key1234, ArrayList<Ocena>> hm = new HashMap<Key1234, ArrayList<Ocena>>();
    	
    	for(Ocena o : s.getOceny()){
    		if(o.getWaga() == -1 && o.getOcena() == 0){
    			continue;
    		}
    		pl.kurs.s11dziekanat.model.Prowadzacy p = o.getPrzedmiot().getProwadzacy();
    		
    		Key1234 key = new Key1234(o.getPrzedmiot().getPrzedmiot().getNazwa(),
    				ProwadzacyPrzedmiot.getYearFromDate( o.getPrzedmiot().getRok() ), 
    				o.getPrzedmiot().getSemestr(),
    				p.getTytul() + " " + p.getImie() + " " + p.getNazwisko()
    				);
    		
    		if(hm.containsKey(key)){
    			ArrayList<Ocena> oc = hm.get(key);
    			oc.add(o);
    			hm.put(key, oc);
    		} else {
    			ArrayList<Ocena> oc = new ArrayList<Ocena>();
    			oc.add(o);
    			hm.put(key, oc);
    		}
    	}
    	
    	StudentOcenyDto dtp = new StudentOcenyDto();
    	
    	for(Key1234 pp : hm.keySet()){
    		PrzedmiotOcentDto o = new PrzedmiotOcentDto(pp, hm.get(pp));
    		dtp.addPrzedmiotAndOceny(o);
    		
    	}
    	
    	
    	return dtp;
    	
    }
    
    public void update(Ocena ocena) {
        entityManager.merge(ocena);
    }

    public void delete(Long id) {
        Ocena ocena = findById(id);
        if (ocena != null) {
            entityManager.remove(ocena);
        }
    }
}