package pl.kurs.s11dziekanat.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
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
import pl.kurs.s11dziekanat.exception.InvalidArgExcetion;
import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.ProwadzacyPrzedmiot;
import pl.kurs.s11dziekanat.model.Przedmiot;
import pl.kurs.s11dziekanat.model.Semestr;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.Przedmioty;
import pl.kurs.s11dziekanat.utils.QuickCast;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotDto;
import pl.kurs.s11dziekanat.model.dto.PrzedmiotExtendedDto;
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
		try{
			System.out.println(id);
			if(przedmiotyService.delete(Long.valueOf(id))){
	
				return Response.noContent().build();
			
			}
			
			return Response.status(404).build();
		
		} catch(EJBTransactionRolledbackException e){
			return Response.status(400).tag(e.getMessage() + "Nie mozna usunac przedmiotu ktory ma zaleznosci!").build();
			
		}
		
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
		
		if(nazwa==null){
			return this.findAll();
		}
		
		try {
			return Response.ok(new Przedmioty(przedmiotyService.cast(przedmiotyService.find(nazwa)))).build();
		
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Path("/extended")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findSingleExt(@QueryParam(value="nazwa") String nazwa, @QueryParam(value="rok") final Integer rok, @QueryParam(value="semestr") String semestr) {
		
		
		final Semestr s;
		try {
			if(semestr == null){
				throw new InvalidArgExcetion("null semestr");
			}
			if((s=Semestr.get(semestr)) == null){
				throw new InvalidArgExcetion("zla wartosc dla semestru: " + semestr);
			}
			if(rok == null){
				throw new InvalidArgExcetion("null rok");
			}
			if(nazwa == null){
				throw new InvalidArgExcetion("null nazwa");
			}
			
			
			return Response.ok(przedmiotyService.findExtended(nazwa, rok, s)).build();
			
		
		} catch (PrzedmiotExcetion e) {
			return Response.status(404).tag(e.getMessage()).build();
		} catch (InvalidArgExcetion e) {
			return Response.status(400).tag(e.getMessage()).build();
		}
	}
	
	
	
	
	@GET
	@Path("")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findAll() {
		
		
		List<Przedmiot> l = przedmiotyService.findAll();
		System.out.println(l.size());
		return Response.ok(new Przedmioty(przedmiotyService.cast(l))).build();
	}
	
	@GET
	@Path("/years")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findAllYearsForPrzedmiot(@QueryParam("id")Long id, @QueryParam("nazwa") String name) {
		try {
			
			if(id != null && name != null){
				return Response.status(400).tag("use only one, not both!").build();

			}
			
			if(id != null){
				
				return Response.ok(przedmiotyService.getAllYearsForPrzedmiot(id)).build();
				
			}
			
			if(name != null){
				return Response.ok(przedmiotyService.getAllYearsForPrzedmiot(name)).build();
				
			}

			return Response.status(400).tag("could not find nazwa and id in url!").build();

		} catch (PrzedmiotExcetion e) {
			return Response.status(404).tag(e.getMessage()).build();
		}
		
	
		
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
