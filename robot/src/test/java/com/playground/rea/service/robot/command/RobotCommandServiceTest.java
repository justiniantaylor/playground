package com.playground.rea.service.robot.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.playground.rea.domain.Robot;
import com.playground.rea.domain.Table;
import com.playground.rea.util.direction.DirectionEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobotCommandServiceTest {
	
	@Autowired
    private RobotCommandService robotCommandService;	      
	
	private Robot robot;
	
	@Before
    public void setUp() {
        robot = new Robot(0,0,null);
    }
	
	@Test
	public void testInvalidRobot() {
		CommandResponse commandResponse = robotCommandService.move(null);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals("No robot was supplied.", commandResponse.getMessage());
	}
	
	@Test
	public void testInvalidRobotMissingFacing() {
		CommandResponse commandResponse = robotCommandService.place(robot, 1, 1, DirectionEnum.NORTH);	
		commandResponse.getRobot().setFacing(null);
		commandResponse = robotCommandService.move(commandResponse.getRobot());	
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals("The robot is placed on a table but has no facing direction.", commandResponse.getMessage());
	}
	 
	@Test
	public void testPlace() {
		CommandResponse commandResponse = robotCommandService.place(robot, 1, 1, DirectionEnum.NORTH);			
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Placed robot at x=1, y=1 facing "+DirectionEnum.NORTH, commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, robot.getFacing());
		assertEquals("1,1," + DirectionEnum.NORTH, commandResponse.getRobot().report());
	}
	
	@Test
	public void testInvalidPlacement() {
		robot.setTable(new Table());
		
		CommandResponse commandResponse = robotCommandService.place(robot, robot.getTable().getDimensionX(), 1, DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=5, y=1 for a table with dimensions of x=" + robot.getTable().getDimensionX() + ", y=" + robot.getTable().getDimensionY(), commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		
		commandResponse = robotCommandService.place(robot, -1, 1, DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=-1, y=1 for a table with dimensions of x=" + robot.getTable().getDimensionX() + ", y=" + robot.getTable().getDimensionY(), commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		
		commandResponse = robotCommandService.place(robot, 1, robot.getTable().getDimensionY(), DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=1, y=5 for a table with dimensions of x=" + robot.getTable().getDimensionX() + ", y=" + robot.getTable().getDimensionY(), commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		
		commandResponse = robotCommandService.place(robot, 1, -1, DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=1, y=-1 for a table with dimensions of x=" + robot.getTable().getDimensionX() + ", y=" + robot.getTable().getDimensionY(), commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
	}
	
	@Test
	public void testMove() {
		CommandResponse commandResponse = robotCommandService.place(robot, 1, 1, DirectionEnum.NORTH);
		
		commandResponse = robotCommandService.move(commandResponse.getRobot());
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(2, commandResponse.getRobot().getY());
		assertEquals("Moved robot " + DirectionEnum.NORTH + " to x=1, y=2", commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, commandResponse.getRobot().getFacing());
		assertEquals("1,2," + DirectionEnum.NORTH, commandResponse.getRobot().report());
	}
	
	@Test
	public void testInvalidMoveNotPlaced() {
		CommandResponse commandResponse = robotCommandService.move(robot);
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("You may not move the robot until it has been placed on a table.", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		assertEquals("NOT PLACED", commandResponse.getRobot().report());
	}
	
	@Test
	public void testLeft() {
		CommandResponse commandResponse = robotCommandService.place(robot, 1, 1, DirectionEnum.NORTH);
		
		commandResponse = robotCommandService.left(commandResponse.getRobot());		
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());		
		assertEquals("Turned robot left, it is now facing " + DirectionEnum.WEST, commandResponse.getMessage());
		assertEquals(DirectionEnum.WEST, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.WEST, commandResponse.getRobot().report());
		
		commandResponse = robotCommandService.left(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot left, it is now facing " + DirectionEnum.SOUTH, commandResponse.getMessage());
		assertEquals(DirectionEnum.SOUTH, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.SOUTH, commandResponse.getRobot().report());
		
		commandResponse = robotCommandService.left(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot left, it is now facing " + DirectionEnum.EAST, commandResponse.getMessage());
		assertEquals(DirectionEnum.EAST, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.EAST, commandResponse.getRobot().report());
		
		commandResponse = robotCommandService.left(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot left, it is now facing " + DirectionEnum.NORTH, commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.NORTH, commandResponse.getRobot().report());
	}
	
	@Test
	public void testInvalidLeftNotPlaced() {
		CommandResponse commandResponse = robotCommandService.left(robot);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("You may not move the robot until it has been placed on a table.", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		assertEquals("NOT PLACED", commandResponse.getRobot().report());
	}
	
	@Test
	public void testRight() {
		CommandResponse commandResponse = robotCommandService.place(robot, 1, 1, DirectionEnum.NORTH);
		
		commandResponse = robotCommandService.right(commandResponse.getRobot());		
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing " + DirectionEnum.EAST, commandResponse.getMessage());
		assertEquals(DirectionEnum.EAST, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.EAST, commandResponse.getRobot().report());
		
		commandResponse = robotCommandService.right(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing " + DirectionEnum.SOUTH, commandResponse.getMessage());
		assertEquals(DirectionEnum.SOUTH, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.SOUTH, commandResponse.getRobot().report());
		
		commandResponse = robotCommandService.right(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing " + DirectionEnum.WEST, commandResponse.getMessage());
		assertEquals(DirectionEnum.WEST, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.WEST, commandResponse.getRobot().report());
		
		commandResponse = robotCommandService.right(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing " + DirectionEnum.NORTH, commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, commandResponse.getRobot().getFacing());
		assertEquals("1,1," + DirectionEnum.NORTH, commandResponse.getRobot().report());
	}
	
	@Test
	public void testInvalidRightNotPlaced() {
		CommandResponse commandResponse = robotCommandService.right(robot);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("You may not move the robot until it has been placed on a table.", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		assertEquals("NOT PLACED", commandResponse.getRobot().report());
	}
}

