package pl.kurs.s11dziekanat.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.ejb.EJB;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import extended.javax.ws.rs.PATCH;
import pl.kurs.s11dziekanat.ejb.ProwadzacyEJB;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyPrzedmiotDto;
import pl.kurs.s11dziekanat.utils.QuickCast;

@Path("/dziekanat/prowadzacy")
//@Consumes({ "application/json" })
//@Produces({ "application/json" })


public class ProwadzacyREST{

	public static final Function<Prowadzacy, ProwadzacyDto> __F__ = new Function<Prowadzacy, ProwadzacyDto>() {
		
		@Override
		public ProwadzacyDto apply(Prowadzacy t) {
			
			return new ProwadzacyDto(t);
		}
	};
	

	@EJB
	ProwadzacyEJB prowadaczyService;

	@GET
	@Path("")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findAll(
			@QueryParam("imie") String imie,
            @QueryParam("nazwisko") String nazwisko,
            @QueryParam("tytul") String tytul,
            @QueryParam("rok") Integer rok, 
            @QueryParam("semestr") String semestr,
            @QueryParam("przedmiot") String przedmiot) {

      
		QuickCast<List, List, Prowadzacy, ProwadzacyDto> qc = new QuickCast<List, List, Prowadzacy, ProwadzacyDto>(__F__);
		
		List<ProwadzacyDto> m = qc.castUsingAdv(prowadaczyService.findAll(imie, nazwisko, tytul, rok, semestr, przedmiot), new ArrayList<ProwadzacyDto>());
        return Response.ok(new pl.kurs.s11dziekanat.model.dto.Prowadzacy(m)).build();
		
	
	}
	
	@GET
	@Path("/wild")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findAllWildCard(
			@QueryParam("imie") String imie,
            @QueryParam("nazwisko") String nazwisko,
            @QueryParam("tytul") String tytul,
            @QueryParam("rok") Integer rok, 
            @QueryParam("semestr") String semestr,
            @QueryParam("przedmiot") String przedmiot) {

		QuickCast<List, List, Prowadzacy, ProwadzacyDto> qc = new QuickCast<List, List, Prowadzacy, ProwadzacyDto>(__F__);
		
		List<ProwadzacyDto> m = qc.castUsingAdv(prowadaczyService.findAllWildCardSupport(imie, nazwisko, tytul, rok, semestr, przedmiot), new ArrayList<ProwadzacyDto>());
        return Response.ok(new pl.kurs.s11dziekanat.model.dto.Prowadzacy(m)).build();
		
	
	}
	
	
	
	@POST
	@Path(value="/save")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response create(ProwadzacyDto p) {
		
		Prowadzacy pNew;
		try {
			pNew = prowadaczyService.create(p);

			return Response.ok().entity(new ProwadzacyDto(pNew)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.notModified(e.getMessage()).build();
			
		}
		
	}


	public Response delete(Prowadzacy p) {
	
		return Response.noContent().build();
	}


	public Response delete(Long id) {
		
		try {
			if(prowadaczyService.delete(id)){
				return Response.noContent().build();
			}
		} catch (Exception e) {
		
			return Response.status(404).build();
		}
		
		
		return Response.noContent().build();
	}
	
	
	@DELETE
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response delete(@PathParam(value="id") long id) {
		
		try {
			prowadaczyService.delete(Long.valueOf(id));
			return Response.noContent().entity("deleted!").build();
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.notModified("unable to remove! " + e.getMessage()).build();
		}
		
		
	}
	
	@PATCH
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response update(@PathParam(value="id") long id, ProwadzacyDto p) {
		
		try {
			Prowadzacy p1 = prowadaczyService.updateOnlyProwadzacy(id, p);
			return Response.ok(new ProwadzacyDto(p1)).build();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.notModified(e.getMessage()).build();
		}
		
		
		
		
	}
	
	
	@PATCH
	@Path("/{id}/addPrzedmiot")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response addPrzedmiot(@PathParam(value="id") long id, ProwadzacyPrzedmiotDto pp) {
		
		try {
			Prowadzacy p1 = prowadaczyService.updateAddNewPrzedmiot(id, pp);
			return Response.ok(new ProwadzacyDto(p1)).build();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.notModified(e.getMessage()).build();
		}
		
	}
	
	@DELETE
	@Path("/{id}/removePrzedmiot")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response removePrzedmiot(@PathParam(value="id") long id, ProwadzacyPrzedmiotDto przedmiot ) {
		
		try {
			Prowadzacy p1 = prowadaczyService.removeProwadzacyPrzedmiot(id, przedmiot);
			
			return Response.noContent().build();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.notModified(e.getMessage()).build();
		}
		
	}
	

	@GET
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response find(@PathParam(value="id") long id) {
		
		Optional<Prowadzacy> opt = prowadaczyService.find(Long.valueOf(id));
		
		if(opt.isPresent()){

			return Response.ok(new ProwadzacyDto(opt.get())).build();
		} else {
			return Response.status(404).build();
		}
		
	}
	


}
