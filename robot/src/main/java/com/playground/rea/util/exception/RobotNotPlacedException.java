package com.playground.rea.util.exception;

/**
 * Runtime exception for when a robot command is given but the robot has not been allocated a table.
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
public class RobotNotPlacedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RobotNotPlacedException() {
		super("You may not move the robot until it has been placed on a table.");
	}
}