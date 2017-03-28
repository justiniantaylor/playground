package com.playground.phoneword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.playground.phoneword.dictionary.Dictionary;
import com.playground.phoneword.dictionary.MatchResult;
import com.playground.phoneword.directory.PhoneDirectory;
import com.playground.phoneword.directory.PhoneNumber;
import com.playground.phoneword.util.PathUtils;

/**
 * This generates phonewords for a all the phone numbers in a directory using words from the supplied dictionary. 
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class PhonewordGenerator {

	private Dictionary dictionary;
	
	public PhonewordGenerator(Dictionary dictionary) {
		this.dictionary = dictionary;		
	}
	
	/**
	 * This generates phonewords for all the phone numbers in a directory using words in the supplied dictionary. 
	 * 
	 * @param phoneDirectory the phone directory containing the phone numbers to generate phonewords for
	 * @return the set of phone numbers with their generate phonewords (if any)
	 * @throws IOException
	 */
	public List<PhoneNumber> generatePhonewords(PhoneDirectory phoneDirectory) throws IOException {
		List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
		
		PathUtils.extractNumbersFromDirectory(phoneDirectory.getPhoneDirectoryPath()).parallelStream()
			.forEach(phoneDirectoryNumber -> {
				try {	
					PhoneNumber phoneNumber = new PhoneNumber(phoneDirectoryNumber);
					phoneNumber.setPhonewords(generatePhoneword(null, phoneDirectoryNumber));
					phoneNumbers.add(phoneNumber);					
				} catch (IOException e) {
					System.err.println("Error generating phonewords for: " + phoneDirectoryNumber);
				}
			});
		
		return phoneNumbers;
	}
	
	/**
	 * This generates all possible phonewords for a specific phone number. 
	 * 
	 * @param phoneNumber the phone number to generate phonewords for
	 * @return the list of phonewords for the supplied phone number(if any)
	 * @throws IOException
	 */
	private Set<String> generatePhoneword(String prefix, String phoneNumber) throws IOException {

		Set<String> phoneWords = new HashSet<String>();
		
		String searchNumber = phoneNumber.substring(prefix == null ? 0 : prefix.replaceAll("-", "").length(), phoneNumber.length());
		
		List<MatchResult> matchResults = dictionary.findWordMatches(searchNumber);
		
		for(MatchResult matchResult : matchResults) {
			if(matchResult.isCompleteMatch()) {
				phoneWords.add((prefix == null ? "" : prefix + "-") + matchResult.getWord());
			} else {	
				phoneWords.addAll(generatePhoneword((prefix == null ? "" : prefix + "-") + matchResult.getWord(), phoneNumber));				
			}
		}

		return phoneWords;
    }
}
