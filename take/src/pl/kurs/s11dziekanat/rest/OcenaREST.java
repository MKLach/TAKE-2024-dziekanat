package pl.kurs.s11dziekanat.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import extended.javax.ws.rs.PATCH;
import pl.kurs.s11dziekanat.ejb.OcenaEjb;
import pl.kurs.s11dziekanat.exception.OcenaExcetion;
import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.exception.StudentExcetion;
import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.dto.ocena.OcenaDto;
import pl.kurs.s11dziekanat.model.dto.ocena.Oceny;

@Path("/dziekanat/oceny")
public class OcenaREST {

	@EJB
    private OcenaEjb ocenaService;

    @GET
    @Path("/{id}")
    @Consumes({ "application/xml" })
	@Produces({ "application/xml" })
    public Response findById(@PathParam("id") Long id) {
      
        return Response.ok(ocenaService.dtofindById(id)).build();
    }

    @GET
    @Consumes({ "application/xml" })
	@Produces({ "application/xml" })
    public Response findAll() {
    	
    	Oceny o = new Oceny(ocenaService.findAll());
    	
        return Response.ok(o).build();
    }

    @POST
    @Path("/{s_id}/{pp_id}")
    @Consumes({ "application/xml" })
	@Produces({ "application/xml" })
    public Response save(@PathParam(value="s_id") Long s_id, @PathParam(value="pp_id") Long pp_id, OcenaDto ocena) {
        try {
			Ocena o = ocenaService.save(s_id, pp_id, ocena);
			 return Response.status(Response.Status.CREATED).entity(new OcenaDto(o)).build();
		} catch (OcenaExcetion e) {
			
			return Response.status(400).tag(e.getMessage()).build();
		} catch (PrzedmiotExcetion e) {
			return Response.status(404).tag(e.getMessage()).build();
			
		} catch (StudentExcetion e) {
			
			return Response.status(404).tag(e.getMessage()).build();
		}
       
    }
    
    
    @GET
    @Path("/{s_id}")
    @Consumes({ "application/xml" })
	@Produces({ "application/xml" })
    public Response findAllOcenyStudenta(@PathParam(value="s_id") Long id) {
    	
    	try {
			return Response.ok(ocenaService.wszystkieOcenyStudenta(id)).build();
		} catch (StudentExcetion e) {
			
			return Response.status(404).tag(e.getMessage()).build();
		}
		
    	
    }

    

    @PATCH
    @Path("/{id}")
    @Consumes({ "application/xml" })
	@Produces({ "application/xml" })
    public Response update(@PathParam("id") Long id, Ocena ocena) {
        Ocena existingOcena = ocenaService.findById(id);
        if (existingOcena == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ocenaService.update(ocena);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({ "application/xml" })
	@Produces({ "application/xml" })
    public Response delete(@PathParam("id") Long id) {
        Ocena ocena = ocenaService.findById(id);
        if (ocena == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ocenaService.delete(id);
        return Response.noContent().build();
    }
}