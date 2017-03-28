package com.playground.phoneword;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.playground.phoneword.util.ExitDeniedSecurityManager;
import com.playground.phoneword.util.ExitDeniedSecurityManager.ExitSecurityException;

public class PhonewordGeneratorCLITest {
	
	@Before
    public void setUp() {
        System.setSecurityManager(new ExitDeniedSecurityManager());
    }
	
	@Test
	public void testSuccess() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File dictionaryFile = new File(classLoader.getResource("resource/dictionary.txt").getFile());
		File numbersFile = new File(classLoader.getResource("resource/directory.txt").getFile());
		
		String[] args = {"-d",dictionaryFile.getAbsolutePath(), numbersFile.getAbsolutePath()};		
		PhonewordGeneratorCLI phonewordGeneratorCLI = new PhonewordGeneratorCLI();
		phonewordGeneratorCLI.parseInput(args);
	}
	
	@Test
	public void testInvalidDictionary() {		
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File dictionaryFile = new File(classLoader.getResource("resource/dictionary.txt").getFile());
			File numbersFile = new File(classLoader.getResource("resource/directory.txt").getFile());
			
			String[] args = {"-d",dictionaryFile.getAbsolutePath()+"-invalid", numbersFile.getAbsolutePath()};		
			PhonewordGeneratorCLI phonewordGeneratorCLI = new PhonewordGeneratorCLI();
			phonewordGeneratorCLI.parseInput(args);
        } catch (ExitSecurityException e) {
            int status = e.getStatus();
            Assert.assertEquals(1, status);
        }
	}
	
	@Test
	public void testInvalidPhoneDirectory() {		
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File dictionaryFile = new File(classLoader.getResource("resource/dictionary.txt").getFile());
			File numbersFile = new File(classLoader.getResource("resource/directory.txt").getFile());
			
			String[] args = {"-d",dictionaryFile.getAbsolutePath(), numbersFile.getAbsolutePath()+"-invalid"};		
			PhonewordGeneratorCLI phonewordGeneratorCLI = new PhonewordGeneratorCLI();
			phonewordGeneratorCLI.parseInput(args);
        } catch (ExitSecurityException e) {
            int status = e.getStatus();
            Assert.assertEquals(1, status);
        }
	}
}
