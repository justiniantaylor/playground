package com.playground.phoneword.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utilities for file system paths
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class PathUtils {
	
	/**
	 * This will extract only the numbers characters for each line in a file.
	 * 
	 * @param path the file system path to extract the lines from
	 * @return the set of lines from the file containing only the numbers from those lines
	 * @throws IOException
	 */
	public static Set<String> extractNumbersFromDirectory(Path path) throws IOException {
		return Files.newBufferedReader(path).lines()
				.map(c -> { 
					StringBuilder sb = new StringBuilder();
	
					c.chars()
						.filter(x -> Character.isDigit((char)x))
						.forEach(x -> sb.append((char) x));
							
					return sb.toString();
				})
				.collect(Collectors.toSet());
	}
	
	/**
	 * This will validate that a path is valid, that it is a file (not a directory) and it is readable.
	 * 
	 * @param path the file system path to validate
	 * @throws IOException
	 */
	public static void validateFilePath(Path path) throws IOException {
		if(path == null) {
			throw new FileNotFoundException("No file specified!");
		}
		if(!Files.isRegularFile(path) || !Files.isReadable(path)) {
			throw new FileNotFoundException("File not found: " + path.toString());
		}
		if(!Files.isReadable(path)) {
			throw new IOException("File not readable: " + path.toString());
		}
	}
}