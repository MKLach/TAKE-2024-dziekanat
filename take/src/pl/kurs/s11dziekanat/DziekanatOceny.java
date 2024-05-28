package pl.kurs.s11dziekanat;

import javax.ejb.Local;

import pl.kurs.s11dziekanat.model.Ocena;
import pl.kurs.s11dziekanat.model.Student;
import pl.kurs.s11dziekanat.xml.Test;

@Local
public interface DziekanatOceny {
	public abstract String create(Ocena ocena);

	public abstract String find(int studentId);

	public abstract Student get();
	
	public abstract Test test();
	public abstract Test test2();
	public abstract Test test3();

	public abstract String update(Ocena ocena);

	public abstract void delete(int idc);
}
