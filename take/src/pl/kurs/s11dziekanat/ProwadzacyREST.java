package pl.kurs.s11dziekanat;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import pl.kurs.komis.Car;
import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.xml.Test;

@Path("/dziekanat/prowadzacy")
//@Consumes({ "application/json" })
//@Produces({ "application/json" })

@Consumes({ "application/xml" })
@Produces({ "application/xml" })
public class ProwadzacyREST{


	@EJB
	ProwadzacyEJB prowadaczyService;

	
	@GET
	@Path(value="/save")
	public Response create(ProwadzacyDto p) {
		Prowadzacy pNew = prowadaczyService.create(p);
		
		
		
		return Response.ok().entity(new ProwadzacyDto(pNew)).build();
	}


	public Response delete(Prowadzacy p) {
		
		return Response.noContent().build();
	}


	public Response delete(Long id) {
		
		return Response.noContent().build();
	}
	
	public Response delete(ProwadzacyDto p) {
		
		return Response.noContent().build();
	}

	public Response update(Prowadzacy p) {
		
		return Response.ok().build();
	}


	public Response find(Long id) {
		
		return null;
	}
	

	public Response update(ProwadzacyDto p) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
