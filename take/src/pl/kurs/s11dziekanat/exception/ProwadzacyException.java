package pl.kurs.s11dziekanat.exception;

import java.util.function.Supplier;

public class ProwadzacyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8249994874381798261L;

	public ProwadzacyException(String message) {
		super(message);
	}
	
	public static class Supply implements Supplier<ProwadzacyException> {

		String message;
		
		public Supply() {
			message = "Excetion!";
		}
		
		public Supply(String message) {
			this.message = message;
		}
		
		@Override
		public ProwadzacyException get() {
			
			return new ProwadzacyException(message);
		}
		
		
		
	}
}
