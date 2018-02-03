package com.skipper.zl.utils;

public class StringUtils {
	
	
	/**
	 * Check whether the given String is empty. 
	 * @param str the candidate String
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str));
	}
}
