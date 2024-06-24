package pl.kurs.s11dziekanat.exception;

import java.util.function.Supplier;

public class OcenaExcetion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8249994874381798261L;

	public OcenaExcetion(String message) {
		super(message);
	}
	
	public static class Supply implements Supplier<OcenaExcetion> {

		String message;
		
		public Supply() {
			message = "Excetion!";
		}
		
		public Supply(String message) {
			this.message = message;
		}
		
		@Override
		public OcenaExcetion get() {
			
			return new OcenaExcetion(message);
		}
		
		
		
	}
}
