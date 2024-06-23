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

import pl.kurs.s11dziekanat.ejb.ProwadzacyEJB;
import pl.kurs.s11dziekanat.ejb.StudentEjb;
import pl.kurs.s11dziekanat.model.Prowadzacy;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.student.StudentDto;
import pl.kurs.s11dziekanat.utils.QuickCast;

@Path("/dziekanat/studenci")
//@Consumes({ "application/json" })
//@Produces({ "application/json" })


public class StudentREST{

	public static final Function<Student, StudentDto> __F__ = new Function<Student, StudentDto>() {
		
		@Override
		public StudentDto apply(Student t) {
			
			return new StudentDto(t);
		}
	};
	

	@EJB
	StudentEjb studentService;

	@GET
	@Path("")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findAll(
			@QueryParam("imie") String imie,
            @QueryParam("nazwisko") String nazwisko) {

      
		QuickCast<List, List, Student, StudentDto> qc = new QuickCast<List, List, Student, StudentDto>(__F__);
		
		List<ProwadzacyDto> m = qc.castUsingAdv(studentService.findAllWildCardSupport(imie, nazwisko), new ArrayList<ProwadzacyDto>());
        return Response.ok(new pl.kurs.s11dziekanat.model.dto.Prowadzacy(m)).build();
		
	
	}
	
	
	@POST
	@Path(value="/save")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response create(StudentDto p) {
		
		Student pNew;
		try {
			pNew = studentService.create(p);

			return Response.ok().entity(new StudentDto(pNew)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.notModified(e.getMessage()).build();
			
		}
		
	}


	public Response delete(Prowadzacy p) {
		
		return Response.noContent().build();
	}


	public Response delete(Long id) {
		
		return Response.noContent().build();
	}
	
	
	@DELETE
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response delete(@PathParam(value="id") long id) {
		
		try {
			studentService.delete(Long.valueOf(id));
			return Response.noContent().entity("deleted!").build();
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.notModified("unable to remove! " + e.getMessage()).build();
		}
		
		
	}

	
	@GET
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findById(@PathParam(value="id") long id) {
		
		Optional<Student> opt = studentService.find(Long.valueOf(id));
		
		if(opt.isPresent()){

			return Response.ok(new StudentDto(opt.get())).build();
		} else {
			return Response.status(404).build();
		}
		
	}
	 
	

	public Response update(StudentDto p) {
		
		return null;
	}
	
	
	

}
