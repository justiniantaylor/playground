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

### Instructions
Download the [aconex.zip](https://github.com/justiniantaylor/playground/blob/master/phoneword/aconex.zip) and unzip into a location of your choice, in this zip file is the phoneword-1.0.0.jar, please make sure you have a valid JRE on your path and then open your command line navigate to this directory and execute this command:

java -jar phoneword.jar [-d "dictionaryFile"] "phoneDirectoryFile1" "phoneDirectoryFile2" 

* Should you not specify the dictionary file, the application will use the default dictionary file in the current folder that would have been unzipped with the jar file.
* Should you not specify any phone directory files the command line will prompt for them, should you wish to exit from these prompts, please type exit.

The code has been documented with javadocs and exists in Github at: [1800 Coding Challenge Source](https://github.com/justiniantaylor/playground/tree/master/phoneword)