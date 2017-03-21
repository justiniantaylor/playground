package com.playground.rea.service.robot;

import org.springframework.validation.annotation.Validated;

import com.playground.rea.service.robot.model.CommandResponse;
import com.playground.rea.util.direction.DirectionEnum;
import com.playground.rea.service.robot.model.Robot;

/**
 * Interface for the Robot Service.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see RobotServiceImpl
 * @since 1.0
 */
@Validated
public interface RobotService {
	
	/**
	 * @see RobotServiceImpl#place(int, int, DirectionEnum)
	 */
	public CommandResponse place(Robot robot, int x, int y, DirectionEnum direction);
	
	/**
	 * @see RobotServiceImpl#move()
	 */
	public CommandResponse move(Robot robot);
	
	/**
	 * @see RobotServiceImpl#left()
	 */
	public CommandResponse left(Robot robot);
	
	/**
	 * @see RobotServiceImpl#right()
	 */
	public CommandResponse right(Robot robot);
}
