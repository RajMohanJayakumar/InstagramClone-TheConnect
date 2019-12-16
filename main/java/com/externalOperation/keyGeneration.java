package com.externalOperation;

public class keyGeneration {
	
	public static String createKey() {
		
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<20;i++) {
			int index = (int)(alphaNumeric.length()*Math.random());
			sb.append(alphaNumeric.charAt(index));
		}
		
		String str = sb.toString();
		
		return str;
	}
}
