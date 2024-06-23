package pl.kurs.s11dziekanat.utils;

public final class StringUtils {

	
	public static boolean e(String str){
		
		return !((str != null) && !str.trim().isEmpty() && str.length() > 0);
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(e("aaaaa"));
		System.out.println(e("a"));
		System.out.println(e("  a "));
		
	}
}
