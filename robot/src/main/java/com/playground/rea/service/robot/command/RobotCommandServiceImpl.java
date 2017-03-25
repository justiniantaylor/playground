package com.playground.rea.service.robot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.playground.rea.domain.Robot;
import com.playground.rea.domain.Table;
import com.playground.rea.util.direction.DirectionEnum;
import com.playground.rea.util.exception.InvalidRobotException;
import com.playground.rea.util.exception.RobotNotPlacedException;

/**
 * Service used for commanding the robots actions and movements.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see RobotCommandService
 * @since 1.0
 */
@Service("robotCommandService")
public class RobotCommandServiceImpl implements RobotCommandService {
	
	private static final Logger log = LoggerFactory.getLogger(RobotCommandServiceImpl.class);
	
	/**
	 * This will put the toy robot on the table in position X, Y and facing either NORTH, SOUTH, EAST or WEST.
	 * <p>
	 * The place command can be issued for a robot with its current table, or with a custom table (with whatever dimensions), 
	 * or a default table will be created for it.
	 * 
	 * @param			the robot to issue the command for
	 * @param x   		the x position on the table top.
	 * @param y   		the y position on the table top.
	 * @param direction the direction the robot should be facing on the table top.
	 * @throws			InvalidRobotException, RobotMissingTableException
     * @return          the robot command response, whether the was command accepted and the robots current location.
	 */
	public CommandResponse place(Robot robot, int x, int y, DirectionEnum direction) {
		CommandResponse commandResponse = new CommandResponse(null);		
		
		log.info("Place robot at x = " + x + ", y = " + y + " facing " + direction);
		
		try {
			validateRobot(robot);
			
			if(!robot.isPlaced()) {
				robot.setTable(new Table());
			}			
			commandResponse.setRobot(robot);
			
			if (x < 0 || x >= robot.getTable().getDimensionX() || y < 0 || y >= robot.getTable().getDimensionY()) {
				commandResponse.setMessage("Invalid placement x=" + x + ", y=" + y +" for a table with dimensions of x=" + robot.getTable().getDimensionX() + ", y=" + robot.getTable().getDimensionY());
				commandResponse.setCommandAccepted(false);		
			} else {
				commandResponse.setMessage("Placed robot at x=" + x + ", y=" + y + " facing " + direction);
				commandResponse.getRobot().setX(x);
				commandResponse.getRobot().setY(y);
				commandResponse.getRobot().setFacing(direction);
				commandResponse.setCommandAccepted(true);		
			}
		} catch (InvalidRobotException | RobotNotPlacedException  e) {
			commandResponse.setMessage(e.getMessage());
			commandResponse.setCommandAccepted(false);	
		} 
		log.info(commandResponse.getMessage());
		
		return commandResponse;
	}
	
	/**
	 * This will move the toy robot one unit forward in the direction it is currently facing.
	 * 
	 * @param  robot the robot to issue the command for
	 * @throws		 InvalidRobotException, RobotMissingTableException
     * @return       the robot command response, whether the was command accepted and the robots current location.
	 */
	public CommandResponse move(Robot robot) {					
		CommandResponse commandResponse = new CommandResponse(robot);

		try {
			validateRobotPlaced(robot);
			
			int requestXPosition = -1;
	  	    int requestYPosition = -1;	    	 	   
			switch (robot.getFacing()) {
		      case NORTH:	
		    	  requestXPosition = robot.getX();
		    	  requestYPosition = robot.getY() + 1;	 
		    	  break;
		      case SOUTH:
		    	  requestXPosition = robot.getX();
		    	  requestYPosition = robot.getY() - 1;	
		    	  break;
		      case EAST:
		    	  requestXPosition = robot.getX() + 1;
		    	  requestYPosition = robot.getY();	
		    	  break;
		      case WEST:  
		    	  requestXPosition = robot.getX() - 1;
		    	  requestYPosition = robot.getY();	
		    	  break;
		    }
			log.info("Move robot " + robot.getFacing() + " to x="+ requestXPosition + ", y=" + requestYPosition);
					
			if (requestYPosition < 0 || requestXPosition < 0 || requestYPosition >= robot.getTable().getDimensionY() || requestXPosition >= robot.getTable().getDimensionX()) {
				commandResponse.setCommandAccepted(false);
				commandResponse.setMessage("Robot will fall off the " + robot.getFacing() + " of table, do not move.");
			} else {				
				commandResponse.getRobot().setX(requestXPosition);
				commandResponse.getRobot().setY(requestYPosition);
				commandResponse.setCommandAccepted(true);	
				commandResponse.setMessage("Moved robot " +  robot.getFacing() + " to x=" + commandResponse.getRobot().getX() + ", y=" + commandResponse.getRobot().getY());
			}
		} catch (InvalidRobotException | RobotNotPlacedException  e) {
			commandResponse.setMessage(e.getMessage());
			commandResponse.setCommandAccepted(false);	
		} 
	
		log.info(commandResponse.getMessage());
		
		return commandResponse;
	}
	
	/**
	 * This will rotate the robot 90 degrees to the left without changing the position of the robot.
	 * 
	 * @param  robot the robot to issue the command for
	 * @throws		 InvalidRobotException, RobotMissingTableException
     * @return 		 the robot command response, whether the was command accepted and the robots current direction.
	 */
	public CommandResponse left(Robot robot) {
		return turn(robot, false);
	}
	
	/**
	 * This will rotate the robot 90 degrees to the right without changing the position of the robot.
	 * 
	 * @param  robot the robot to issue the command for
	 * @throws		 InvalidRobotException, RobotMissingTableException
     * @return       the robot command response, whether the was command accepted and the robots current direction.
	 */
	public CommandResponse right(Robot robot) { 
		return turn(robot, true);
	}
	
	/**
	 * This will rotate the robot 90 degrees in requested direction (left or right) without changing the position of the robot.
	 * 
	 * @param robot  the robot to turn
	 * @param right  whether to turn right or left
     * @return 		 the robot command response, whether the was command accepted and the robots current direction.
	 */
	private CommandResponse turn(Robot robot, boolean right) {	
		CommandResponse commandResponse = new CommandResponse(robot);
		
		log.info("Turn robot " + (right ? "right" : "left") + " from " + robot.getFacing());
		
		try {
			validateRobotPlaced(robot);
			
			switch (commandResponse.getRobot().getFacing()) {
		      case NORTH:	
		    	  commandResponse.getRobot().setFacing(right ? DirectionEnum.EAST : DirectionEnum.WEST);			  
		    	  break;
		      case SOUTH:
		    	  commandResponse.getRobot().setFacing(right ? DirectionEnum.WEST : DirectionEnum.EAST);		
		    	  break;
		      case EAST:
		    	  commandResponse.getRobot().setFacing(right ? DirectionEnum.SOUTH : DirectionEnum.NORTH);		
		    	  break;
		      case WEST:  
		    	  commandResponse.getRobot().setFacing(right ? DirectionEnum.NORTH : DirectionEnum.SOUTH);		
		    	  break;
		    }
			commandResponse.setMessage("Turned robot " + (right ? "right" : "left") + ", it is now facing " + commandResponse.getRobot().getFacing());		
			commandResponse.setCommandAccepted(true);	
		} catch (InvalidRobotException | RobotNotPlacedException  e) {
			commandResponse.setMessage(e.getMessage());
			commandResponse.setCommandAccepted(false);	
		} 
		log.info(commandResponse.getMessage());
		
		return commandResponse;
	}
	
	/**
	 * This will validate the robot and raise any exceptions.
	 * <p>
	 * It will check if a valid robot was supplied, ie not null.
	 * 
	 * @param robot  the robot to validate
	 * @throws		 InvalidRobotException
	 */
	private void validateRobot(Robot robot) {
		if(robot == null) {
			throw new InvalidRobotException("No robot was supplied.");
		} 
	}
	
	/**
	 * This will validate the robot after it has been placed and raise any exceptions.
	 * <p>
	 * It will check if a valid robot was supplied, ie not null.
	 * <p>
	 * It will check if the robot is placed.
	 * <p>
	 * It will check that if the robot is placed that it has a valid facing.
	 * 
	 * @param robot  the robot to validate
	 * @throws		 InvalidRobotException, RobotMissingTableException
	 */
	private void validateRobotPlaced(Robot robot) {
		validateRobot(robot);
		
		if(!robot.isPlaced()) {
			throw new RobotNotPlacedException();
		}
	
		if(robot.isPlaced() && robot.getFacing() == null) {
			throw new InvalidRobotException("The robot is placed on a table but has no facing direction.");
		}
	}
}
