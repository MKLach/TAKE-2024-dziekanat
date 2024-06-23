package pl.kurs.s11dziekanat.ejb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import pl.kurs.s11dziekanat.exception.ProwadzacyException;
import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.Semestr;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyPrzedmiotDto;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotDto;

@Stateless
public class ProwadzacyEJB {
	
	@PersistenceContext(name="dziekanat", type=PersistenceContextType.TRANSACTION)
	EntityManager entityManager;

	@EJB
	PrzedmiotEjb przedmiotEJB;
	
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Prowadzacy create(ProwadzacyDto p) throws Exception {
		
		Prowadzacy prowadzacy = new Prowadzacy(p.getId(), p.getImie(), p.getNazwisko(), p.getTytul());
		
		entityManager.persist(prowadzacy);
		
		if(p.getPrzedmioty() == null || p.getPrzedmioty().isEmpty()){
			
			return prowadzacy;
		}
		
		Set<ProwadzacyPrzedmiot> przedmioty = new HashSet<ProwadzacyPrzedmiot>();
		for(ProwadzacyPrzedmiotDto przedmiotDto : p.getPrzedmioty()){
		
			ProwadzacyPrzedmiot pp = new ProwadzacyPrzedmiot();
			pp.setProwadzacy(prowadzacy);
			
			Optional<Przedmiot> przedmiotIn;
			
			if(przedmiotDto.getId() == null){
				przedmiotIn = przedmiotEJB.findOpt(przedmiotDto.getNazwa());
			} else {
				przedmiotIn = przedmiotEJB.find(przedmiotDto.getId());
			}
			
			Przedmiot przedmiot;
			
			if(!przedmiotIn.isPresent()){
				if(przedmiotDto.getNazwa() == null){
					
					entityManager.remove(prowadzacy);					
					throw new Exception("cannot add prowadzacy with not exisiting przedmiot!!!");
				}
				
				PrzedmiotDto dto = new PrzedmiotDto();
				dto.setNazwa(przedmiotDto.getNazwa());
			
				przedmiot = new Przedmiot();
				przedmiot.setNazwa(przedmiotDto.getNazwa());
				entityManager.persist(przedmiot);
				
				
			} else {
				przedmiot = przedmiotIn.get();
			}
					
			pp.setPrzedmiot(przedmiot);
			pp.setRok(przedmiotDto.getRok());
			pp.setSemestr(Semestr.get(przedmiotDto.getSemestr()));
			
			//entityManager.persist(pp);
			
			przedmioty.add(pp);
		}
		
		for(ProwadzacyPrzedmiot pp : przedmioty){
			entityManager.persist(pp);
			
		}
		
		prowadzacy.setPrzedmioty(przedmioty);
		
		prowadzacy = entityManager.merge(prowadzacy);
		
		
		return prowadzacy;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Prowadzacy updateOnlyProwadzacy(Long id, ProwadzacyDto p) throws Exception {
		
		Optional<Prowadzacy> prowadzacyopt = find(id);
	
		if(!prowadzacyopt.isPresent()){
			
			throw new Exception("cannot update not exiting prowadzacy!!!");
		}
	
		Prowadzacy prowadzacy = prowadzacyopt.get();
		prowadzacy.setImie(p.getImie());
		prowadzacy.setNazwisko(p.getNazwisko());
		prowadzacy.setTytul(p.getTytul());
		
		prowadzacy = entityManager.merge(prowadzacy);
		
		return prowadzacy;
	}
	
	public Prowadzacy updateAddNewPrzedmiot(Long id, ProwadzacyPrzedmiotDto ppd) throws Exception, PrzedmiotExcetion {
		
		Optional<Prowadzacy> prowadzacyopt = find(id);
		
		if(!prowadzacyopt.isPresent()){
			
			throw new Exception("cannot update not exiting prowadzacy!!!");
		}
	
		Prowadzacy p = prowadzacyopt.get();
		
		Set<ProwadzacyPrzedmiot> ppl = p.getPrzedmioty();
		boolean iterate = true;
		if(p.getPrzedmioty() == null || p.getPrzedmioty().isEmpty()){
			ppl = new HashSet<ProwadzacyPrzedmiot>();
			iterate = false;
			
		}
		
		if(iterate){
			
			for(ProwadzacyPrzedmiot ppIn : ppl){
				
				if(ppIn.getPrzedmiot().getNazwa().equals(ppd.getNazwa())){
					if(ppIn.mangled_name_temporal_rok_in() == ppd.getRok()){
						if(ppIn.getSemestr().equals(Semestr.get(ppd.getSemestr()))){
							throw new PrzedmiotExcetion("Prowadzacy ju¿ uczy tego przedmiotu w tym semestrze w tym roku!");
						}
					}
				}
			}	
		}
		
		ProwadzacyPrzedmiot pp = new ProwadzacyPrzedmiot();
		
		pp.setRok(ppd.getRok());
		pp.setSemestr(Semestr.get(ppd.getSemestr()));
		pp.setProwadzacy(p);
		
		
		Przedmiot przed = przedmiotEJB.findOrCreate(ppd.getNazwa());
		
		pp.setPrzedmiot(przed);
		
		entityManager.persist(pp);
		
		ppl.add(pp);
		
		p.setPrzedmioty(ppl);
		
		p = entityManager.merge(p);
		
		return p;
	}
	
	
	public ProwadzacyPrzedmiot findByDto(final ProwadzacyPrzedmiotDto in) throws PrzedmiotExcetion{
		
		
		try{
			
		
		if(in.getProwadzacy_przedmiot_id() != null){
			return entityManager.find(ProwadzacyPrzedmiot.class, in.getProwadzacy_przedmiot_id());
		}
		
		} catch(Exception e){
			
			
		}
		
		if(((in.getNazwa() != null) || (in.getId() != null)) && in.getRok() != 0 && in.getSemestr() != null){
			
			return findAllPP().stream().filter(new Predicate<ProwadzacyPrzedmiot>() {

				@Override
				public boolean test(ProwadzacyPrzedmiot t) {
					
					if(in.getNazwa() != null){

						return t.mangled_name_temporal_rok_in()==in.getRok() && t.getPrzedmiot().getNazwa().equals(in.getNazwa()) && t.getSemestr().equals(Semestr.get(in.getSemestr()));
					} else if (in.getId() != null) {
						
						return t.mangled_name_temporal_rok_in()==in.getRok() && t.getPrzedmiot().getId().equals(in.getId()) && t.getSemestr().equals(Semestr.get(in.getSemestr()));
					} else {
						return false;
					}
					
				}
			}).findFirst().orElseThrow(new PrzedmiotExcetion.Supply("Podany przedmiot nie istneje!"));
			
			
			
		}
		
		
		throw new PrzedmiotExcetion("Podany przedmiot nie istneje!");
		
		
	}
	
	public Prowadzacy updateRemovePrzedmiot(Long id, final ProwadzacyPrzedmiotDto pp) throws Exception, PrzedmiotExcetion {
		

		Optional<Prowadzacy> prowadzacyopt = find(id);
		
		if(!prowadzacyopt.isPresent()){
			
			throw new Exception("prowadzacy nie istnieje!");
		}
	
		Prowadzacy p = prowadzacyopt.get();
		
		Set<ProwadzacyPrzedmiot> ppl = p.getPrzedmioty();
		
		if(p.getPrzedmioty() == null || p.getPrzedmioty().isEmpty()){
			
			throw new PrzedmiotExcetion("prowadzacy nie uczy zadnych przedmiotow!");
			
		}
		
		int s_bef = ppl.size();
		
		ProwadzacyPrzedmiot ppNEW = this.findByDto(pp);
		
		ppl.remove(ppNEW);
		
		if(s_bef == ppl.size()){
			throw new PrzedmiotExcetion("prowadzacy nie uczy tego przedmiotu!!!");
		}
		
		p.setPrzedmioty(ppl);
		
		p = entityManager.merge(p);
		
		return p;
		
		
	}
		
	
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	/**
	 * if update has no list of przedmioty, then prowadzacy will lose all przedmioty
	 * WARING replacing old przedmioty will opnly have effect when there is no oceny pointing to the przedmioty
	 * by przedmioty i mean prowadzacy_przedmiot
	 * 
	 * @param id
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public Prowadzacy updateReplaceOldPrzedmiotyWithNew(Long id, ProwadzacyDto p) throws Exception {
		
		Prowadzacy prowadzacy = this.updateOnlyProwadzacy(id, p);
		
		for(ProwadzacyPrzedmiot pp : prowadzacy.getPrzedmioty()){
			entityManager.remove(pp);
		}
		
		if(p.getPrzedmioty() == null || p.getPrzedmioty().isEmpty()){
			
			
			return prowadzacy;
		}
		
		Set<ProwadzacyPrzedmiot> przedmioty = new HashSet<ProwadzacyPrzedmiot>();
		for(ProwadzacyPrzedmiotDto przedmiotDto : p.getPrzedmioty()){
		
			ProwadzacyPrzedmiot pp = new ProwadzacyPrzedmiot();
			pp.setProwadzacy(prowadzacy);
			
			Optional<Przedmiot> przedmiotIn;
			
			if(przedmiotDto.getId() == null){
				przedmiotIn = przedmiotEJB.findOpt(przedmiotDto.getNazwa());
			} else {
				przedmiotIn = przedmiotEJB.find(przedmiotDto.getId());
			}
			
			Przedmiot przedmiot;
			
			if(!przedmiotIn.isPresent()){
				if(przedmiotDto.getNazwa() == null){
					
					entityManager.remove(prowadzacy);					
					throw new Exception("cannot add prowadzacy with not exisiting przedmiot!!!");
				}
				
				PrzedmiotDto dto = new PrzedmiotDto();
				dto.setNazwa(przedmiotDto.getNazwa());
			
				przedmiot = new Przedmiot();
				przedmiot.setNazwa(przedmiotDto.getNazwa());
				entityManager.persist(przedmiot);
				
				
			} else {
				przedmiot = przedmiotIn.get();
			}
					
			pp.setPrzedmiot(przedmiot);
			pp.setRok(przedmiotDto.getRok());
			pp.setSemestr(Semestr.get(przedmiotDto.getSemestr()));
			
			//entityManager.persist(pp);
			
			przedmioty.add(pp);
		}
		
		for(ProwadzacyPrzedmiot pp : przedmioty){
			entityManager.persist(pp);
			
		}
		
		prowadzacy.setPrzedmioty(przedmioty);
		
		prowadzacy = entityManager.merge(prowadzacy);
		
		
		return prowadzacy;
	}
	/**
	 * there is no sense for this to exist xay
	 * 
	 * ProwadzacyPrzedmiiot has complex priamry key (prow_id, przed_id, rok, semestr), if we try to add new entry with the same
	 * przedmiot, rok and semester, there will be no effect
	 * 
	 * guide for updating existing prowadzacyprzedmiot:
	 * <code><br>
	 * 	<<przedmiot><br>
	 * 		<<id>NEW ONE<</id> // if we want to edit, it must be present, if it is not present, we will try to add new connection<br>
	 *  	<<year>year-int<</year>   // may be ommitted<br>
	 *  	<<semestr>sem<</semestr> // may be ommitted<br>
	 * 	<</przedmiot><br>
	 * </code>
	 * 
	 * @param id
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Prowadzacy updateKeepOldPrzedmiotyMayUpdateExistingMayAddNewOnes(Long id, ProwadzacyDto p) throws Exception {
		
		Prowadzacy prowadzacy = this.updateOnlyProwadzacy(id, p);
		
		if(p.getPrzedmioty() == null || p.getPrzedmioty().isEmpty()){
			
			return prowadzacy;
		}

		Set<ProwadzacyPrzedmiot> przedmioty = new HashSet<ProwadzacyPrzedmiot>();
		if(prowadzacy.getPrzedmioty() == null){
			
			prowadzacy.setPrzedmioty(przedmioty);
		}
		
		for(ProwadzacyPrzedmiotDto przedmiotDto : p.getPrzedmioty()){
		
			if(przedmiotDto.getId() == null){
				// here be add new one
				
				
				
				
			} else {				
				// here be updates
				for(ProwadzacyPrzedmiot inside : prowadzacy.getPrzedmioty()){
					
					if(inside.getPrzedmiot().equals(przedmiotDto.getId())){
						
						
					}
				}
				
				
			}
			
			
			Optional<Przedmiot> przedmiotIn;
			
			if(przedmiotDto.getId() == null){
				przedmiotIn = przedmiotEJB.findOpt(przedmiotDto.getNazwa());
			} else {
				przedmiotIn = przedmiotEJB.find(przedmiotDto.getId());
			}
			
			Przedmiot przedmiot;
			
			if(!przedmiotIn.isPresent()){
				if(przedmiotDto.getNazwa() == null){
					
					entityManager.remove(prowadzacy);					
					throw new Exception("cannot add prowadzacy with not exisiting przedmiot!!!");
				}
				
				PrzedmiotDto dto = new PrzedmiotDto();
				dto.setNazwa(przedmiotDto.getNazwa());
			
				przedmiot = new Przedmiot();
				przedmiot.setNazwa(przedmiotDto.getNazwa());
				entityManager.persist(przedmiot);
				
				
			} else {
				przedmiot = przedmiotIn.get();
			}
					
			//pp.setPrzedmiot(przedmiot);
			//pp.setRok(przedmiotDto.getRok());
			//pp.setSemestr(Semestr.get(przedmiotDto.getSemestr()));
			
			//entityManager.persist(pp);
			
			//przedmioty.add(pp);
		}
		
		for(ProwadzacyPrzedmiot pp : przedmioty){
			entityManager.persist(pp);
			
		}
		
		prowadzacy.setPrzedmioty(przedmioty);
		
		prowadzacy = entityManager.merge(prowadzacy);
		
		
		return prowadzacy;
	}

	
	public boolean delete(Long id) throws Exception{
		System.out.println(id);
		entityManager.remove(find(id).get());
		try{
			return delete(find(id).orElseThrow(new ProwadzacyException.Supply("Could not find przedmiot with id " + id )));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(Prowadzacy p) throws Exception {
		
		try{
			entityManager.remove(p);
			
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}

	public Optional<Prowadzacy> find(Long id) {
		
		Optional<Prowadzacy> opt = Optional.ofNullable(entityManager.find(Prowadzacy.class, id));
		
		return opt;
	}
	
	public List<Prowadzacy> findAll() {
		
		TypedQuery<Prowadzacy> query = entityManager.createQuery("SELECT p FROM Prowadzacy p", Prowadzacy.class);
		
		return query.getResultList();
		
	}
	
	public List<Prowadzacy> findAll(
			String imie,
            String nazwisko,
            String tytul,
            Integer rok, 
            String semestr,
            String przedmiot) {

        StringBuilder queryString = new StringBuilder("SELECT e FROM Prowadzacy e");
        
        if (rok != null || przedmiot != null && !przedmiot.trim().isEmpty() || (semestr != null && !semestr.trim().isEmpty())) {
            queryString.append(" JOIN e.przedmioty pp");
        }
        
        if ((przedmiot != null && !przedmiot.trim().isEmpty())) {
            queryString.append(" JOIN pp.przedmiot p");
        }
       
        
        queryString.append(" WHERE 1=1");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (imie != null && !imie.trim().isEmpty()) {
            queryString.append(" AND e.imie = :imie");
            parameters.put("imie", imie);
        }
        if (nazwisko != null && !nazwisko.trim().isEmpty()) {
            queryString.append(" AND e.nazwisko = :nazwisko");
            parameters.put("nazwisko", nazwisko);
        }
        if (tytul != null && !tytul.trim().isEmpty()) {
            queryString.append(" AND e.tytul = :tytul");
            parameters.put("tytul", tytul);
        }
        if (rok != null) {
            queryString.append(" AND pp.rok = :rok");
            parameters.put("rok", rok);
        }
        if (semestr != null) {
            queryString.append(" AND pp.semestr = :semestr");
            parameters.put("semestr", Semestr.get(semestr));
        }
        if (przedmiot != null && !przedmiot.trim().isEmpty()) {
            queryString.append(" AND p.nazwa = :przedmiot");
            parameters.put("przedmiot", przedmiot);
        }

        TypedQuery<Prowadzacy> query = entityManager.createQuery(queryString.toString(), Prowadzacy.class);
        
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

       return query.getResultList();
	}
	
	public List<Prowadzacy> findAllWildCardSupport(
			String imie,
            String nazwisko,
            String tytul,
            Integer rok, 
            String semestr,
            String przedmiot) {

        StringBuilder queryString = new StringBuilder("SELECT e FROM Prowadzacy e");
        
        if (rok != null || przedmiot != null && !przedmiot.trim().isEmpty() || (semestr != null && !semestr.trim().isEmpty())) {
            queryString.append(" JOIN e.przedmioty pp");
        }
        
        if ((przedmiot != null && !przedmiot.trim().isEmpty())) {
            queryString.append(" JOIN pp.przedmiot p");
        }
       
        
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
        if (tytul != null && !tytul.trim().isEmpty()) {
            queryString.append(" AND e.tytul LIKE :tytul");
            parameters.put("tytul", "%"+tytul+"%");
        }
        if (rok != null) {
            queryString.append(" AND pp.rok = :rok");
            parameters.put("rok", rok);
        }
        if (semestr != null) {
            queryString.append(" AND pp.semestr = :semestr");
            parameters.put("semestr", Semestr.get(semestr));
        }
        if (przedmiot != null && !przedmiot.trim().isEmpty()) {
            queryString.append(" AND p.nazwa LIKE :przedmiot"); // Assuming 'name' is a field in Przedmiot
            parameters.put("przedmiot", "%"+przedmiot+"%");
        }

        TypedQuery<Prowadzacy> query = entityManager.createQuery(queryString.toString(), Prowadzacy.class);
        
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        System.out.println("Generated Query: " + queryString.toString());
        System.out.println("Parameters: " + parameters);
        
       return query.getResultList();
	}
	
	public List<ProwadzacyPrzedmiot> findAllPP() {
		TypedQuery<ProwadzacyPrzedmiot> query = entityManager.createQuery("SELECT p FROM Prowadzacy_Przedmioty p", ProwadzacyPrzedmiot.class);
		
		return query.getResultList();
	}
	
	
	
	
	
	
	
}
