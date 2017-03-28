package com.playground.phoneword.dictionary;

import java.util.List;

/**
 * A result of a word match request for a combination of letter options in a dictionary.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class MatchResult {
	
	/**
	 * the letter options used for the match request
	 */
	private List<String> letterOptions;
	
	/**
	 * the word used for the match request
	 */
	private String word;
	
	/**
	 * the indicator of whether a match was found
	 */
	private boolean match = false;
	
	public MatchResult(List<String> letterOptions, String word) {
		super();
		this.letterOptions = letterOptions;
		this.word = word;
	}
	
	public List<String> getLetterOptions() {
		return letterOptions;
	}
	
	public void setLetterOptions(List<String> letterOptions) {
		this.letterOptions = letterOptions;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public boolean isMatch() {
		return match;
	}
	
	public void setMatch(boolean match) {
		this.match = match;
	}
	
	/**
	 * This indicates that the word was an exact letter count match with letter options and not futher words are needed.
	 * 
	 * @return whether this was a complete match
	 */
	public boolean isCompleteMatch() {
		boolean completeMatch = false;
		if(isMatch() && getNumbersRemaining() == 0) {
			completeMatch = true;
		}
		return completeMatch;
	}
	
	/**
	 * This calculates and returns the numberof letter options still remaining after the match.
	 * 
	 * @return the number of letter options remaining after the match
	 */
	public int getNumbersRemaining() {
		if(letterOptions == null && word != null) {
			return word.length();
		} else if(letterOptions != null && word != null && word.length() <= letterOptions.size()) {
			return letterOptions.size() - word.length();
		} else {
			return 0;
		}
	}
}
