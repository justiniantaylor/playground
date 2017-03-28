package com.playground.phoneword.directory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The phone number and its available phonewords.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class PhoneNumber {
	
	/**
	 * The phone number
	 */
	private String number;
	
	/**
	 * The set of available phonewords
	 */
	private Set<String> phonewords;
	
	public PhoneNumber(String number) {		
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public Set<String> getPhonewords() {
		return phonewords;
	}
	
	public boolean hasPhonewords() {
		return phonewords == null ? false : phonewords.size() > 0;
	}

	public void setPhonewords(Set<String> phonewords) {
		this.phonewords = phonewords;
	}
	
	/**
	 * Gets phone number as a list of integers.
	 * 
	 * @return the phone number as a list of integers
	 */
	public List<Integer> getNumberList() {
		List<Integer> numbers = new ArrayList<Integer>();
		number.chars()
			.filter(c -> Character.isDigit((char)c))
			.forEach(c -> numbers.add(new Integer(Character.getNumericValue(c))));	
		return numbers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((phonewords == null) ? 0 : phonewords.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneNumber other = (PhoneNumber) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (phonewords == null) {
			if (other.phonewords != null)
				return false;
		} else if (!phonewords.equals(other.phonewords))
			return false;
		return true;
	}
}
