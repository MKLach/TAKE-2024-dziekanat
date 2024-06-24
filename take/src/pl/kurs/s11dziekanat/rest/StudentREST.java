package pl.kurs.s11dziekanat.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

import extended.javax.ws.rs.PATCH;
import pl.kurs.s11dziekanat.ejb.StudentEjb;
import pl.kurs.s11dziekanat.exception.PrzedmiotExcetion;
import pl.kurs.s11dziekanat.exception.StudentExcetion;
import pl.kurs.s11dziekanat.model.Semestr;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.model.dto.ProwadzacyDto;
import pl.kurs.s11dziekanat.model.dto.student.Studenci;
import pl.kurs.s11dziekanat.model.dto.student.StudentDto;
import pl.kurs.s11dziekanat.model.dto.student.StudentExtendedDto;
import pl.kurs.s11dziekanat.model.dto.student.ZapisNaPrzedmiot;
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
		
		List<StudentDto> m = qc.castUsingAdv(studentService.findAllWildCardSupport(imie, nazwisko), new ArrayList<StudentDto>());
        
		return Response.ok(new Studenci(m)).build();
		
	
	}
	
	@GET
	@Path("/zapisaniNa")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findAllByPrzedmiot(
			@QueryParam("nazwa") String nazwa,
            @QueryParam("rok") Integer rok,
            @QueryParam("semestr") String semestr) {

		if(nazwa == null){
			return Response.status(400).build();
			
		}

		if(rok == null){
			return Response.status(400).build();
			
		}
		
		if(semestr == null){
			return Response.status(400).build();
			
		}
		
		if(Semestr.get(semestr) == null){
			return Response.status(400).build();
			
		}
      
		QuickCast<List, List, Student, StudentDto> qc = new QuickCast<List, List, Student, StudentDto>(__F__);
		
		List<StudentDto> m = qc.castUsingAdv(studentService.findAllByPrzedmiot(nazwa, rok, Semestr.get(semestr)), new ArrayList<StudentDto>());
        
		return Response.ok(new Studenci(m)).build();
		
	
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
	 
	
	@GET
	@Path("/{id}/przedmioty")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response findByIdExt(@PathParam(value="id") long id) {
		
		StudentExtendedDto opt;
		try {
			opt = studentService.findExt(Long.valueOf(id));
		} catch (StudentExcetion e) {
			
			return Response.status(404).tag(e.getMessage()).build();
		}
		
		return Response.ok(opt).build();
		
	}
	 
	
	@PATCH
	@Path("/{id}")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response update(@PathParam("id") Long id, StudentDto p)  {
		try{
			Student s = studentService.update(id, p);
			
			return Response.ok(new StudentDto(s)).build();
			
		}catch (PrzedmiotExcetion e) {
			return Response.notModified().tag(e.getMessage()).build();
		}
	}
	
	
	
	@PATCH
	@Path("/{id}/zapisz")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response zapiszNaPrzedmiot(@PathParam("id") Long id, ZapisNaPrzedmiot p)  {
		System.out.println(id);
		try{
			Student s = studentService.zapiszNaPrzedmiot(id, p);
			
			return Response.ok(new StudentDto(s)).build();
			
		}catch (StudentExcetion e) {
			
			return Response.notModified().tag(e.getMessage()).build();
		} catch (PrzedmiotExcetion e) {
			return Response.notModified().tag(e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/{id}/wypisz")
	@Consumes({ "application/xml" })
	@Produces({ "application/xml" })
	public Response wypiszZPrzedmiotu(@PathParam("id") Long id, ZapisNaPrzedmiot p)  {
		System.out.println(id);
		try{
			Student s = studentService.wypiszZPrzedmiotu(id, p);
			
			return Response.ok(new StudentDto(s)).build();
			
		}catch (StudentExcetion e) {
			
			return Response.notModified().tag(e.getMessage()).build();
		}
	}
	
	
	
	
	

}
