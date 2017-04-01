# 1-800-CODING-CHALLENGE

## Requirements

Many companies like to list their phone numbers using the letters printed on most
telephones. This makes the number easier to remember for customers. An example may
be 1-800-FLOWERS

This coding challenge is to write a program that will show a user possible matches for a list
of provided phone numbers.

Your program should be a command line application that reads from files specified as
command-line arguments or STDIN when no files are given. Each line of these files will
contain a single phone number.

For each phone number read, your program should output all possible word replacements
from a dictionary. Your program should try to replace every digit of the provided phone
number with a letter from a dictionary word; however, if no match can be made, a single
digit can be left as is at that point. No two consecutive digits can remain unchanged and
the program should skip over a number (producing no output) if a match cannot be made.

Your program should allow the user to set a dictionary with the -d command-line option,
but it's fine to use a reasonable default for your system. The dictionary is expected to have
one word per line.

All punctuation and whitespace should be ignored in both phone numbers and the
dictionary file. The program should not be case sensitive, letting "a" == "A". Output should
be capital letters and digits separated at word boundaries with a single dash (-), one
possible word encoding per line. For example, if your program is fed the number:

2255.63

According to my dictionary, one possible line of output is

CALL-ME

The number encoding on the phone the program will use is:

Digit | Characters
------------ | -------------
2 | A B C
3 | D E F
4 | G H I
5 | J K L
6 | M N O
7 | P Q R S
8 | T U V
9 | W X Y Z

## Solution

### Stack
There was the requirement to not used any 3rd party libraries for this (besides testing libs) and be able to run from command line & STDIN, so I only used the following:
* Java 8
* JUnit & Maven (For testing and building the jar in the root named aconex.zip, please see instructions)

### Approach
1. I started by creating a basic conceptual model, in which the concepts were the Dictionary, Phone Directory, Phone Number and Phoneword.
2. I then stubbed out the methods for these objects, and then wired these methods into a CLI class for the command line, as well as the JUnit so I could test as I progressed.
3. I then added specific test cases to my test data and unit tests that would form the basis for testing my logic through the development process.
4. I then added all the method logic into the methods and tested and bugfixed until my tests passed.
5. I then logged in and started interacting with the command line and did more human / user acceptance testing  and confirmed I was happy with the final result.

### Design
* The code design is fairly basic, there is a CLI class which is used for inputting and printing responses for phoneword generation, either by command lines or stdin console.
* There is a dictionary class which holds the location to a validated phone directory file and with finding matching words in the dictionary files for requested numbers.
* There is a phone directory class which holds the location to a validated phone directory file.
* There is a phone number class which holds the number as well as any phonewords that may have been generated for that number.
* Then finally there is then a phoneword generator class which is the main class that deals with extracting numbers from a phone directory and generating phonewords for these number and returning the results.

### Instructions

As this is a commaned line application, in order to execute the phoneword-1.0.0.jar packaged with this zip file, please download, unzip the file and make sure you have a valid JRE on your path and execute this command in the jar folder:

java -jar phoneword.jar [-d "dictionaryFile"] "phoneDirectoryFile1" "phoneDirectoryFile2" 

* Should you not specify the dictionary file, the application will use the default dictionary file in the current folder that would have been unzipped with the jar file.
* Should you not specify any phone directory files the command line will prompt for them, should you wish to exit from these prompts, please type exit.
