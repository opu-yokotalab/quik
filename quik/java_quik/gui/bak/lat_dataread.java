/* PROGRAM DATA READ CLASS  --1997-- */

package java_quik.gui;
import java_quik.*;

import  java.util.*;

public class LAT_Dataread {
    	private StringTokenizer st;

    	public LAT_Dataread(String str) {
		st = new StringTokenizer(str);
    	}

    	public LAT_Thesa load(LAT_Thesa th) {
		String front = null;
		String back = null;
		int isa = 0;

		while (true) {
	    		try { 
				String kind = st.nextToken();

				if (0 == kind.compareTo("<=")) {
		    			isa = 1;
					back = st.nextToken();
				}
				if (0 == kind.compareTo(">=")) {
		    			isa = 2;
					back = st.nextToken();
				}
				switch (isa) {
		  			case	1:
		    			if (null == front) {
					isa = 0;
					break;
		    			}
		    			th.add(front, back);
		    			isa = 0;
		    			break;

		 			case	2:
					if (null == front) {
					isa = 0;
					break;
		    			}
		    			th.add(back, front);
		    			isa = 0;
		    			break;

		  			default:
					front = kind;
		    			break;
				}
	    		} catch (NoSuchElementException ea) {
				return th;
			}
		}
    	}
}










