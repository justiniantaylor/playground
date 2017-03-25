package com.playground.rea.service.robot.command;

import org.springframework.validation.annotation.Validated;

import com.playground.rea.domain.Robot;
import com.playground.rea.util.direction.DirectionEnum;

/**
 * Interface for the Robot Command Service.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see RobotCommandServiceImpl
 * @since 1.0
 */
@Validated
public interface RobotCommandService {
	
	/**
	 * @see RobotCommandServiceImpl#place(int, int, DirectionEnum)
	 */
	public CommandResponse place(Robot robot, int x, int y, DirectionEnum direction);
	
	/**
	 * @see RobotCommandServiceImpl#move()
	 */
	public CommandResponse move(Robot robot);
	
	/**
	 * @see RobotCommandServiceImpl#left()
	 */
	public CommandResponse left(Robot robot);
	
	/**
	 * @see RobotCommandServiceImpl#right()
	 */
	public CommandResponse right(Robot robot);
}
