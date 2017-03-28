package com.playground.phoneword.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for numbers.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class NumberUtils {
	
	/**
	 * Gets the letter options for as a specific digit.
	 * 
	 * @param digit the character digit to get the letter options for
	 * @return the letter options for each digit in the number
	 */
	public static String getNumberLetterOption(char digit) {
		String numberLetterOptions = "";

		if(Character.isDigit(digit)) {
			numberLetterOptions = NumberUtils.getLetters(Character.getNumericValue(digit));			
		}
		return numberLetterOptions;
	}
	
	/**
	 * Gets the letter options for each digit in the number.
	 * 
	 * @param numbers the string of numbers used to get the letter options for
	 * @return the list of letter options for each digit in the number
	 */
	public static List<String> getNumberLetterOptions(String numbers) {
		List<String> numberLetterOptions = new ArrayList<String>();

		if(numbers != null && !numbers.equals("")) {
			numbers.chars().forEach(c -> numberLetterOptions.add(getNumberLetterOption((char) c)));				
		}
		return numberLetterOptions;
	}
	
	/**
	 * This will return the letter options for an integer.
	 * 
	 * @param the integer to get the letter options for
	 * @return the letter options for the integer
	 */
	public static String getLetters(int n) {
		switch (n) {
            case 2:
            	return "ABC";
            case 3:
            	return "DEF";
            case 4:
            	return "GHI";
            case 5:
            	return "JKL";
            case 6:
            	return "MNO";
            case 7:
            	return "PQRS";
            case 8:
            	return "TUV";
            case 9:
            	return "WXYZ";            
            default:
                return "";        	
		}
	}
}