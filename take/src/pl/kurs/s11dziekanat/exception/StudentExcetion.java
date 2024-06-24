package pl.kurs.s11dziekanat.exception;

import java.util.function.Supplier;

public class StudentExcetion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8249994874381798261L;

	public StudentExcetion(String message) {
		super(message);
	}
	
	public static class Supply implements Supplier<StudentExcetion> {

		String message;
		
		public Supply() {
			message = "Excetion!";
		}
		
		public Supply(String message) {
			this.message = message;
		}
		
		@Override
		public StudentExcetion get() {
			
			return new StudentExcetion(message);
		}
		
		
		
	}
}
