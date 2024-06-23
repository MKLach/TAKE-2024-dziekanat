package pl.kurs.s11dziekanat.exception;

import java.util.function.Supplier;

public class InvalidArgExcetion extends Exception {



	/**
	 * 
	 */
	private static final long serialVersionUID = 3934172628852366365L;

	public InvalidArgExcetion(String message) {
		super(message);
	}
	
	public static class Supply implements Supplier<InvalidArgExcetion> {

		String message;
		
		public Supply() {
			message = "Excetion!";
		}
		
		public Supply(String message) {
			this.message = message;
		}
		
		@Override
		public InvalidArgExcetion get() {
			
			return new InvalidArgExcetion(message);
		}
		
		
		
	}
}
