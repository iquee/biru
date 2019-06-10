package com.luiztaira.utils;

/**
 * Auxiliary class to normalize CNPJ
 * 
 * @author taira
 *
 */
public class NormalizeDocument {
	
	public static String normnalizeToStore(String document) {
		if (document != null && !document.equals(""))
			document = document.replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\-", "");
		return document;
	}
	
	public static String normnalizeToResponse(String document) {
		if (document != null && document.length() == 14)
			document = document.substring(0, 2) + "." + document.substring(2, 5) + "." + document.substring(5, 8) + "/"
					+ document.substring(8, 12) + "-" + document.substring(12, 14);
		return document;
	}

}
