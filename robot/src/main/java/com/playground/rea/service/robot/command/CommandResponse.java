package com.playground.rea.service.robot.command;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.playground.rea.domain.Robot;

/**
 * Contains the response to the robot command.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CommandResponse extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Robot robot;
	private boolean commandAccepted;
	private String message;
	
	@JsonCreator
	public CommandResponse(@JsonProperty("robot") Robot robot) {
		this.robot = robot;
		this.commandAccepted = false;
		this.message = null;
	}
	
	@JsonCreator
	public CommandResponse(@JsonProperty("robot") Robot robot, @JsonProperty("commandAccepted") boolean commandAccepted, @JsonProperty("message") String message) {
		this.robot = robot;
		this.commandAccepted = commandAccepted;
		this.message = message;
	}

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
