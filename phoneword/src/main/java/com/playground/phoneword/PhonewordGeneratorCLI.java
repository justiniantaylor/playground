package com.playground.phoneword;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.playground.phoneword.dictionary.Dictionary;
import com.playground.phoneword.directory.PhoneDirectory;
import com.playground.phoneword.directory.PhoneNumber;

/**
 * Command Line Interface for the Aconex phoneword generator.
 * <p>
 * Usage: java -jar phoneword.jar [-d "dictionaryFile"] "numberFile1" "numberFile2"
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class PhonewordGeneratorCLI {
	
	private static final String HELP = "Usage: java -jar phoneword.jar [-d \"dictionaryFile\"] \"numberFile1\" \"numberFile2\"";
	private static final String EXIT = "exit";
	
	/**
	 * This will parse any directory files that were passed as command line arguments and generate phonewords for those directories. 
	 * <p>
	 * If no directories were supplied as command line arguments the interface will start a console and prompt for directories
	 * to generate phonewords for.
	 * <p>
	 * If the optional directory file was not supplied using the -d option, the system will default to looking for a file name 
	 * "dictionary.txt" in the current folder.
	 * 
	 * @param args	the command line arguments
	 */
	public void parseInput(String[] args) {
		System.out.println();
		System.out.println("Welcome to the Aconex phoneword generator.");
		
		Path dictionaryPath = null;
		
		try {
			List<PhoneDirectory> phoneDirectories = new ArrayList<PhoneDirectory>();
			if (args != null && args.length != 0) {
				Iterator<String> i = Arrays.asList(args).iterator();	
				while(i.hasNext()) {
					String arg = i.next();				
					if(arg.equalsIgnoreCase("-d")) {
						dictionaryPath = Paths.get(i.next());
					} else {
						phoneDirectories.add(new PhoneDirectory(Paths.get(arg)));
					}
				}		
			} 
			
			PhonewordGenerator phonewordGenerator = new PhonewordGenerator(new Dictionary(dictionaryPath));
			
			phoneDirectories.forEach( directory-> {
				try {
					printResults(phonewordGenerator.generatePhonewords(directory));
				} catch(IOException ioe) {
					System.err.println(ioe.getMessage());	
				}
			});
			
			if(phoneDirectories.size() == 0) {
				startConsole(phonewordGenerator);
			}			
		} catch(IOException ioe) {
			System.err.println(ioe.getMessage());	
			System.err.println(HELP);
			System.exit(1);
		}
	}
	
	/**
	 * This will start up a console and start prompting directories to generate phonewords for
	 * until the user types in exit and presses enter.
	 * 
	 * @param phonewordGenerator the initialised phoneword generator that we should use when processing directories
	 */
	private void startConsole(PhonewordGenerator phonewordGenerator) {
		
		Console console = System.console();
		if (console == null) {
		    System.err.println("No console: please revert to using command line options!");
		    System.err.println(HELP);
		    System.exit(1);
		}
		
		boolean exitCLI = false;
		System.out.println();
		System.out.println("Lets begin generating some phonewords, if you would like to exit at any point, type 'EXIT' and enter.");
        while (!exitCLI) {        	
        	System.out.println();
        	System.out.println("Which directory file would you like to generate a phonewords for? ");
			String phoneDirectoryPath = console.readLine();
			
            if (EXIT.equalsIgnoreCase(phoneDirectoryPath)) {
            	exitCLI = true;
            } else {
            	try {
            		printResults(phonewordGenerator.generatePhonewords(new PhoneDirectory(Paths.get(phoneDirectoryPath))));
	            } catch(IOException ioe) {
	    			System.err.println(ioe.getMessage());			
	    		}
            }
        }
	}
	
	/**
	 * This will print the results from the phoneword generation..
	 * 
	 * @param phoneNumbers the numbers we requested phoneword generation for
	 */
	private void printResults(List<PhoneNumber> phoneNumbers) {
		if(phoneNumbers != null) {
			for(PhoneNumber phoneNumber : phoneNumbers) {
				System.out.println();
				System.out.println("Phoneword options for " + phoneNumber.getNumber());
				if(phoneNumber.hasPhonewords()) {										
					for(String phoneword : phoneNumber.getPhonewords()) {
						System.out.println(phoneword);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		PhonewordGeneratorCLI phonewordGeneratorCLI = new PhonewordGeneratorCLI();
		phonewordGeneratorCLI.parseInput(args);
	}
}
