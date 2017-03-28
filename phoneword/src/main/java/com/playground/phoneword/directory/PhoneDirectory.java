package com.playground.phoneword.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import com.playground.phoneword.util.PathUtils;

/**
 * The directory of phone numbers in a valid file.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class PhoneDirectory {

	private Path phoneDirectoryPath;
		
	public PhoneDirectory(Path phoneDirectoryPath) throws FileNotFoundException, IOException {				
		this.phoneDirectoryPath = phoneDirectoryPath;
		PathUtils.validateFilePath(this.phoneDirectoryPath);		
	}

	public Path getPhoneDirectoryPath() {
		return phoneDirectoryPath;
	}

	public void setPhoneDirectoryPath(Path phoneDirectoryPath) {
		this.phoneDirectoryPath = phoneDirectoryPath;
	}
}