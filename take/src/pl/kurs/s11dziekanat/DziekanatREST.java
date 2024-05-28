package pl.kurs.s11dziekanat;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import pl.kurs.komis.Car;
import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.xml.Test;

@Path("/dziekanat")
//@Consumes({ "application/json" })
//@Produces({ "application/json" })

@Consumes({ "application/xml" })
@Produces({ "application/xml" })
public class DziekanatREST implements DziekanatOceny{


	@EJB
	DziekanatEJB dziekanatBean;
	
	
	@Override
	@GET
	@Path("/test")
	public String create(Ocena ocena) {
		
		//Student student = new Student();
		
		//dziekanatBean.entityManager.persist(student);
		
		
		return "hello";
	}
	
	
	@GET	
	@Path("{idc}")
	@Override
	public String find(int idc) {
		
		System.out.println("test");
		
		return "test";
	}
	
	@GET	
	@Path("/test4")
	@Override
	public Student get() {
		
		
		
		return null;
	}

	@GET
	@Path("/losuj-ocena")
	@Override
	public String update(Ocena ocena) {
		
		return "";
	}

	@Override
	public void delete(int idc) {
		// TODO Auto-generated method stub
		
	}


	@Override
	@GET
	@Path("/test")
	@Produces({ "application/xml" })
	public Test test() {
		
		Prowadzacy p = new Prowadzacy();
		
		p.setImie("Krzysztof");
		p.setNazwisko("Lach");
		p.setTytul("mgr in¿.");

		System.out.println(dziekanatBean);
		System.out.println(dziekanatBean.entityManager);
		dziekanatBean.create(p);
		return new Test(p.toString());
	}


	@Override
	@GET
	@Path("/test2")
	@Produces({ "application/xml" })
	public Test test2() {
		;
		System.out.println(dziekanatBean.getEntityManager().find(Przedmiot.class, Long.valueOf(0)));
		return new Test(""+dziekanatBean.getEntityManager().find(Przedmiot.class, Long.valueOf(0)));
	}


	@Override
	@GET
	@Path("/test3")
	public Test test3() {
		
		Przedmiot p1 = new Przedmiot(null, "KAPPA");
		
		dziekanatBean.createPrzedmiot(p1);
		
		return  new Test(p1 == null ? "null" : p1.toString());
	}

}
