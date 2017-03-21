package com.playground.rea.util.exception;

/**
 * Runtime exception for when a command is for an invalid robot.
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
public class InvalidRobotException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidRobotException(String message) {
		super(message);
	}
}