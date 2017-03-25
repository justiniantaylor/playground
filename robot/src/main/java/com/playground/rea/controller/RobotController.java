package com.playground.rea.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.playground.rea.domain.Robot;

/**
 * Rest interface for robot commands.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/robot")
public class RobotController {

	/**
	 * This will create a new toy robot.
	 * 	
     * @return the new toy robot that has not been placed on a table
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public HttpEntity<Robot> create() {
		Robot robot = new Robot(0,0,null);
		return new ResponseEntity<Robot>(robot, HttpStatus.OK);
	}
}