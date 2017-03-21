package com.playground.rea.service.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.playground.rea.service.robot.model.CommandResponse;
import com.playground.rea.util.direction.DirectionEnum;
import com.playground.rea.service.robot.model.Robot;
import com.playground.rea.service.robot.model.Table;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class RobotServiceTest {
	
	@Autowired
    private RobotService robotService;	      
	
	private Robot robot;
	
	@Before
    public void setUp() {
        robot = new Robot();
    }
	
	@Test
	public void testInvalidRobot() {
		CommandResponse commandResponse = robotService.move(null);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals("No robot was supplied.", commandResponse.getMessage());
	}
	
	@Test
	public void testInvalidRobotMissingFacing() {
		CommandResponse commandResponse = robotService.place(robot, 1, 1, DirectionEnum.NORTH);	
		commandResponse.getRobot().setFacing(null);
		commandResponse = robotService.move(commandResponse.getRobot());	
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals("The robot is placed on a table but has no facing direction.", commandResponse.getMessage());
	}
	 
	@Test
	public void testPlace() {
		CommandResponse commandResponse = robotService.place(robot, 1, 1, DirectionEnum.NORTH);			
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Placed robot at x=1, y=1 facing NORTH", commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, robot.getFacing());
	}
	
	@Test
	public void testInvalidPlacement() {
		robot.setTable(new Table());
		
		CommandResponse commandResponse = robotService.place(robot, robot.getTable().getDimensionX(), 1, DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=5, y=1 for a table with dimensions of x=5, y=5", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.place(robot, -1, 1, DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=-1, y=1 for a table with dimensions of x=5, y=5", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.place(robot, 1, robot.getTable().getDimensionY(), DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=1, y=5 for a table with dimensions of x=5, y=5", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.place(robot, 1, -1, DirectionEnum.NORTH);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("Invalid placement x=1, y=-1 for a table with dimensions of x=5, y=5", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
	}
	
	@Test
	public void testMove() {
		CommandResponse commandResponse = robotService.place(robot, 1, 1, DirectionEnum.NORTH);
		
		commandResponse = robotService.move(commandResponse.getRobot());
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(2, commandResponse.getRobot().getY());
		assertEquals("Moved robot NORTH to x=1, y=2", commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, commandResponse.getRobot().getFacing());
	}
	
	@Test
	public void testInvalidMoveNotPlaced() {
		CommandResponse commandResponse = robotService.move(robot);
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("You may not move the robot until it has been placed on a table.", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
	}
	
	@Test
	public void testLeft() {
		CommandResponse commandResponse = robotService.place(robot, 1, 1, DirectionEnum.NORTH);
		
		commandResponse = robotService.left(commandResponse.getRobot());		
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());		
		assertEquals("Turned robot left, it is now facing WEST", commandResponse.getMessage());
		assertEquals(DirectionEnum.WEST, commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.left(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot left, it is now facing SOUTH", commandResponse.getMessage());
		assertEquals(DirectionEnum.SOUTH, commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.left(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot left, it is now facing EAST", commandResponse.getMessage());
		assertEquals(DirectionEnum.EAST, commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.left(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot left, it is now facing NORTH", commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, commandResponse.getRobot().getFacing());
	}
	
	@Test
	public void testInvalidLeftNotPlaced() {
		CommandResponse commandResponse = robotService.left(robot);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("You may not move the robot until it has been placed on a table.", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
	}
	
	@Test
	public void testRight() {
		CommandResponse commandResponse = robotService.place(robot, 1, 1, DirectionEnum.NORTH);
		
		commandResponse = robotService.right(commandResponse.getRobot());		
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing EAST", commandResponse.getMessage());
		assertEquals(DirectionEnum.EAST, commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.right(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing SOUTH", commandResponse.getMessage());
		assertEquals(DirectionEnum.SOUTH, commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.right(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing WEST", commandResponse.getMessage());
		assertEquals(DirectionEnum.WEST, commandResponse.getRobot().getFacing());
		
		commandResponse = robotService.right(commandResponse.getRobot());	
		assertTrue(commandResponse.isCommandAccepted());
		assertEquals(1, commandResponse.getRobot().getX());
		assertEquals(1, commandResponse.getRobot().getY());
		assertEquals("Turned robot right, it is now facing NORTH", commandResponse.getMessage());
		assertEquals(DirectionEnum.NORTH, commandResponse.getRobot().getFacing());
	}
	
	@Test
	public void testInvalidRightNotPlaced() {
		CommandResponse commandResponse = robotService.right(robot);		
		assertFalse(commandResponse.isCommandAccepted());
		assertEquals(0, commandResponse.getRobot().getX());
		assertEquals(0, commandResponse.getRobot().getY());
		assertEquals("You may not move the robot until it has been placed on a table.", commandResponse.getMessage());
		assertNull(commandResponse.getRobot().getFacing());
	}
}

