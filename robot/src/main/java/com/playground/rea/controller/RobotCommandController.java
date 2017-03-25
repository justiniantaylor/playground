package com.playground.rea.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.playground.rea.domain.Robot;
import com.playground.rea.service.robot.command.CommandResponse;
import com.playground.rea.service.robot.command.RobotCommandService;
import com.playground.rea.util.direction.DirectionEnum;

/**
 * Rest interface for robot commands.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/robot/command")
public class RobotCommandController {

	@Autowired
    private RobotCommandService robotCommandService;
    
	/**
	 * @see RobotServiceImpl#place(int, int, DirectionEnum)
	 */
	@RequestMapping(value = "/place/{x}/{y}/{direction}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public HttpEntity<CommandResponse> place(@RequestBody Robot robot, @PathVariable("x") int x, @PathVariable("y") int y, @PathVariable("direction") DirectionEnum direction) {
		CommandResponse commandResponse = robotCommandService.place(robot, x, y, direction);
		return new ResponseEntity<CommandResponse>(addPlacedLinks(commandResponse), HttpStatus.OK);
	}
	
	/**
	 * @see RobotServiceImpl#move()
	 */
	@RequestMapping(value = "/move", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public HttpEntity<CommandResponse> move(@RequestBody Robot robot) {
		CommandResponse commandResponse = robotCommandService.move(robot);
		return new ResponseEntity<CommandResponse>(addPlacedLinks(commandResponse), HttpStatus.OK);
	}
	
	/**
	 * @see RobotServiceImpl#left()
	 */
	@RequestMapping(value = "/left", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public HttpEntity<CommandResponse> left(@RequestBody Robot robot) {
		CommandResponse commandResponse = robotCommandService.left(robot);
		return new ResponseEntity<CommandResponse>(addPlacedLinks(commandResponse), HttpStatus.OK);
	}
	
	/**
	 * @see RobotServiceImpl#right()
	 */
	@RequestMapping(value = "/right", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public HttpEntity<CommandResponse> right(@RequestBody Robot robot) {
		CommandResponse commandResponse = robotCommandService.right(robot);		
		return new ResponseEntity<CommandResponse>(addPlacedLinks(commandResponse), HttpStatus.OK);
	}
	
	private CommandResponse addPlacedLinks(CommandResponse commandResponse) {
		commandResponse.add(linkTo(methodOn(RobotCommandController.class).move(commandResponse.getRobot())).withSelfRel());
		commandResponse.add(linkTo(methodOn(RobotCommandController.class).left(commandResponse.getRobot())).withSelfRel());
		commandResponse.add(linkTo(methodOn(RobotCommandController.class).right(commandResponse.getRobot())).withSelfRel());
		return commandResponse;
	}
}