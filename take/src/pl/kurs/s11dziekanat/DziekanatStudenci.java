package pl.kurs.s11dziekanat;

import javax.ejb.Local;

import pl.kurs.s11dziekanat.model.Student;

@Local
public interface DziekanatStudenci {
	public abstract String create(Student student);

	public abstract String find(int studentId);

	public abstract Student get();

	public abstract String update(Student student);

	public abstract void delete(int idc);
}
