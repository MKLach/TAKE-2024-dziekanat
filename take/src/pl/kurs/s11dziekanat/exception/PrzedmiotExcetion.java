package pl.kurs.s11dziekanat.exception;

import java.util.function.Supplier;

public class PrzedmiotExcetion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8249994874381798262L;

	public PrzedmiotExcetion(String message) {
		super(message);
	}
	
	public static class Supply implements Supplier<PrzedmiotExcetion> {

		String message;
		
		public Supply() {
			message = "Excetion!";
		}
		
		public Supply(String message) {
			this.message = message;
		}
		
		@Override
		public PrzedmiotExcetion get() {
			
			return new PrzedmiotExcetion(message);
		}
		
		
		
	}
}
