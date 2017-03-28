package com.playground.phoneword.dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.playground.phoneword.util.NumberUtils;
import com.playground.phoneword.util.PathUtils;

/**
 * A dictionary containing words stored in a valid file.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class Dictionary {

	private static final String DEFAULT = "./dictionary.txt";
	
	/**
	 * the validated path to the dictionary
	 */
	private Path dictionaryPath;
	
	/**
	 * This will construct a new dictionary and validate the dictionary path.
	 * <p> 
	 * If the dictionary is null the default dictionary file named "dictionary.txt" in the current folder will be used.
	 * <p>
	 * On initialisation the dictionary file path will be validated to see if its exists and can be read from.
	 * 
	 * @param dictionaryPath the path to the dictionary file
	 * @throws FileNotFoundException, IOException
	 */
	public Dictionary(Path dictionaryPath) throws FileNotFoundException, IOException {
		
		if(dictionaryPath != null) {
			System.out.println("Using provided dictionary: " + dictionaryPath.toString());
			this.dictionaryPath = dictionaryPath;
		} else {
			System.out.println("Using default dictionary 'dictionary.txt' located in this folder.");
			this.dictionaryPath = Paths.get(DEFAULT);
		}
		PathUtils.validateFilePath(this.dictionaryPath);		
		
	}
	
	/**
	 * This will find all possible word matches for letter options provided.
	 * ie. ["MNO","DEF"] will match the word "ME" 
	 * 
	 * @param numbers the combination of numbers used to find word matches.
	 * @throws IOException
	 * @return the words found in the dictionary file matching the numbers supplied
	 */
	public List<MatchResult> findWordMatches(String numbers) throws IOException {
		List<MatchResult> matchResults = new ArrayList<MatchResult>();
		
		Set<String> possibleWords = filterWordsStartingWith(NumberUtils.getNumberLetterOption(numbers.charAt(0)), numbers.length());
		
		for(String possibleWord : possibleWords) {	
			MatchResult matchResult = matchWord(possibleWord, numbers);
			if(matchResult.isMatch()) {	
				matchResults.add(matchResult);
			}
		}
		return matchResults;
	}
	
	/**
	 * This will retrieve words from the dictionary that begin with any of the letters supplied and that are within the maximum length provided.
	 * 
	 * @param startsWithLetters the string (possibly multiple characters)
	 * @param maxWordLength the maximum length of the word in the dictionary to return
	 * @throws IOException
	 * @return the words found in the dictionary file matching the input criteria
	 */
	private Set<String> filterWordsStartingWith(String startsWithLetters, int maxWordLength) throws IOException {
		return Files.newBufferedReader(dictionaryPath)
				.lines()
				.map(c -> { 
					StringBuilder sb = new StringBuilder();

					c.toUpperCase().chars()
						.filter(x -> Character.isLetter((char)x))
						.forEach(x -> sb.append((char) x));
							
					return sb.toString();
				})
				.filter(c -> c.length() <= maxWordLength && startsWithLetters.contains(""+c.charAt(0)))
				.collect(Collectors.toSet());
	}	
	
	/**
	 * This will match a possible word with numbers provided.
	 * ie. 63 will match the possible word "ME" with options "MNO" for 6 and "DEF" for 3
	 * <p>
	 * No two consecutive digits can remain unchanged and the program should skip over a number (producing no output) if a match cannot be made.
	 * <p>
	 * It replaces every digit of the provided phone number with a letter from a dictionary word; however, if no match can be made, a single
     * digit is left at that point.
	 * 
	 * @param possibleWord the word to attempt matching against the letter options provided.
	 * @param numbers the combination of numbers to match to the word.
	 * @throws IOException
	 * @return the result of the match request
	 * @see MatchResult
	 */
	private MatchResult matchWord(String possibleWord, String numbers) throws IOException {
		
		List<String> numberLetterOptions = NumberUtils.getNumberLetterOptions(numbers);
		MatchResult matchResult = new MatchResult(numberLetterOptions, possibleWord);
		
		if(possibleWord.length() <= numberLetterOptions.size()) {
			int notMatched = 0;
			for(int wordIndex = 0; wordIndex < possibleWord.length(); wordIndex++) {				
				char character = possibleWord.charAt(wordIndex);
				
				if(numberLetterOptions.get(wordIndex).contains(""+ (char) character)) {
					matchResult.setMatch(true);	
					if(wordIndex == possibleWord.length()) {										
						break;
					}
				} else {
					StringBuilder partialWord = new StringBuilder(matchResult.getWord());
					partialWord.setCharAt(wordIndex, (char) numbers.charAt(wordIndex));
					matchResult.setWord(partialWord.toString());
					++notMatched;
				}
				if(notMatched == 2) {
					matchResult.setMatch(false);
					break;
				}
			}		
		}
		return matchResult;
	}	
}
