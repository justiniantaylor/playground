package com.playground.rea.controller.robot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.playground.rea.service.robot.RobotService;
import com.playground.rea.service.robot.RobotServiceImpl;
import com.playground.rea.service.robot.model.CommandResponse;
import com.playground.rea.service.robot.model.Robot;
import com.playground.rea.util.direction.DirectionEnum;

/**
 * Rest interface for robot service.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/robot")
public class RobotController {

	@Autowired
    private RobotService robotService;
    
	/**
	 * @see RobotServiceImpl#place(int, int, DirectionEnum)
	 */
	@RequestMapping(value = "/place/{x}/{y}/{direction}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public CommandResponse place(@RequestBody Robot robot, @PathVariable("x") int x, @PathVariable("y") int y, @PathVariable("direction") DirectionEnum direction) {
		return robotService.place(robot, x, y, direction);
	}
	
	/**
	 * @see RobotServiceImpl#move()
	 */
	@RequestMapping(value = "/move", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public CommandResponse move(@RequestBody Robot robot) {
		System.out.println(robot);
		return robotService.move(robot);
	}
	
	/**
	 * @see RobotServiceImpl#left()
	 */
	@RequestMapping(value = "/left", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public CommandResponse left(@RequestBody Robot robot) {
		return robotService.left(robot);
	}
	
	/**
	 * @see RobotServiceImpl#right()
	 */
	@RequestMapping(value = "/right", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public CommandResponse right(@RequestBody Robot robot) {
		return robotService.right(robot);
	}
}