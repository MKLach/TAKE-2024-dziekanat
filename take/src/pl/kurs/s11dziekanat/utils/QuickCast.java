package pl.kurs.s11dziekanat.utils;

import java.util.Collection;
import java.util.function.Function;

public class QuickCast<COLORG extends Collection,COLNEW extends Collection,ORG,NEW> {
	
	Function<ORG, NEW> f;
	
	public QuickCast() {
		f = null;
	}
	
	public QuickCast(Function<ORG, NEW> f) {
		this.f=f;
	}
	
	@SuppressWarnings("unchecked")
	public COLNEW cast(COLORG original, COLNEW newOne){
		
		for(Object o : original){
			
			newOne.add((NEW)o);
		}
		
		
		return newOne;
		
	}
	
	public COLNEW castUsingAdv(COLORG original, COLNEW newOne){
		
		for(Object o : original){
			
			newOne.add(f.apply((ORG)o) );
		}
		
		
		return newOne;
		
	}
	
	
	
	
}