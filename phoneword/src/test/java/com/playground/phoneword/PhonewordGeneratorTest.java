package com.playground.phoneword;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.playground.phoneword.dictionary.Dictionary;
import com.playground.phoneword.directory.PhoneDirectory;
import com.playground.phoneword.directory.PhoneNumber;

public class PhonewordGeneratorTest {

	private Dictionary dictionary;
	private PhonewordGenerator phonewordGenerator;
	private List<PhoneDirectory> phoneDirectories;
	
	@Before
	public void setup() throws Exception {		
		ClassLoader classLoader = getClass().getClassLoader();
		File dictionaryFile = new File(classLoader.getResource("resource/dictionary.txt").getFile());
		File numbersFile = new File(classLoader.getResource("resource/directory.txt").getFile());
		
		dictionary = new Dictionary(Paths.get(dictionaryFile.getAbsolutePath()));		
		phonewordGenerator = new PhonewordGenerator(dictionary);
		
		phoneDirectories = new ArrayList<PhoneDirectory>();
		phoneDirectories.add(new PhoneDirectory(Paths.get(numbersFile.getAbsolutePath())));
	}
	
	@Test
	public void testSuccess() throws Exception {
		List<PhoneNumber> numbers = phonewordGenerator.generatePhonewords(phoneDirectories.get(0));
		assertEquals(3, numbers.size());
		
		for(PhoneNumber phoneNumber : numbers) {
			if(phoneNumber.getNumber().equals("225563")) {
				assertEquals(4, phoneNumber.getPhonewords().size());
				
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("CA-LL-ME")));
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("CALL-ME")));
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("CALLME")));
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("BALL-ME")));
			} else if (phoneNumber.getNumber().equals("215563")) {
				assertEquals(4, phoneNumber.getPhonewords().size());
				
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("C1-LL-ME")));
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("C1LL-ME")));
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("C1LLME")));
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("B1LL-ME")));
			} else if (phoneNumber.getNumber().equals("517846")) {
				assertEquals(1, phoneNumber.getPhonewords().size());
				assertTrue(phoneNumber.getPhonewords().stream().anyMatch(str -> str.equals("J1STIN")));
			} else {
				throw new Exception("No expected numbers returned!");
			}
		}
	}
}
