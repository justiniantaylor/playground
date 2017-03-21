package com.playground.rea.service.robot.model;

import java.io.Serializable;

/**
 * Contains the response to the robot command.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CommandResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Robot robot;
	
	private boolean commandAccepted;
	private String message;

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public boolean isCommandAccepted() {
		return commandAccepted;
	}

	public void setCommandAccepted(boolean commandAccepted) {
		this.commandAccepted = commandAccepted;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
