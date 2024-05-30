package pl.kurs.s11dziekanat.rest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import extended.javax.ws.rs.PATCH;
import pl.kurs.komis.Car;
import pl.kurs.s11dziekanat.ejb.PrzedmiotEjb;
import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.ListDto;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotDto;
import pl.kurs.s11dziekanat.xml.Test;

@Path("/dziekanat/przedmioty")
//@Consumes({ "application/json" })
//@Produces({ "application/json" })


public class PrzedmiotREST{


	@EJB
	PrzedmiotEjb przedmiotyService;

	
	@POST
	@Path(value="/save")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response create(PrzedmiotDto p) {
		Przedmiot pNew = przedmiotyService.create(p);
		
		return Response.ok().entity(new PrzedmiotDto(pNew)).build();
	}


	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam(value="id") long id) {
		System.out.println(id);
		if(przedmiotyService.delete(Long.valueOf(id))){

			return Response.noContent().build();
		}
		
		return Response.status(404).build();
	}

	@GET
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response find(@PathParam(value="id") long id) {
		
		try {
			return Response.ok( new PrzedmiotDto(przedmiotyService.find(Long.valueOf(id)).orElseThrow(new PrzedmiotExcetion.Supply()))).build();
		
		} catch (PrzedmiotExcetion e) {
			return Response.status(404).build();
		}
	}
	
	@GET
	@Path("")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response find(@QueryParam(value="nazwa") String nazwa) {
		
		try {
			return Response.ok(new ListDto(przedmiotyService.cast(przedmiotyService.find(nazwa)))).build();
		
		} catch (PrzedmiotExcetion e) {
			return Response.status(404).build();
		}
	}
	
	
	@GET
	@Path("/single")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findSingle(@QueryParam(value="nazwa") String nazwa) {
		
		try {
			return Response.ok(przedmiotyService.cast(przedmiotyService.find(nazwa)).get(0)).build();
		
		} catch (PrzedmiotExcetion e) {
			return Response.status(404).build();
		}
	}
	
	
	@GET
	@Path("")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findAll() {
		
		
		List<Przedmiot> l = przedmiotyService.findAll();
		System.out.println(l.size());
		return Response.ok(new ListDto(przedmiotyService.cast(l))).build();
	}
	
	
	@PATCH
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response update(@PathParam(value="id") Long id, PrzedmiotDto p) {
		Przedmiot pRet;
		try {
			p.setId(id);
			pRet = przedmiotyService.update(p);
		} catch (PrzedmiotExcetion e) {
			
			return Response.status(404).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.notModified().build();
			
		}
		
		return Response.ok(new PrzedmiotDto(pRet)).build();
		
		
	}
	
	
	

}
