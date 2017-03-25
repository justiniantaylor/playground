package com.playground.rea.integration.robot;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.playground.rea.domain.Robot;
import com.playground.rea.service.robot.command.CommandResponse;
import com.playground.rea.service.robot.command.RobotCommandService;
import com.playground.rea.util.direction.DirectionEnum;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class RobotIntegrationTest {
	
	@Autowired
    private RobotCommandService robotCommandService;	      
	
	private Robot robot;
	
	@Before
    public void setUp() {
        robot = new Robot(0,0,null);
    }
	
	/**
	 * This is integration test 1 based on Example A from the requirements.
	 * PLACE 0,0,NORTH
	 * MOVE
	 * REPORT
	 * Result = 0,1,NORTH
	 */
	@Test
	public void testCase1() {	
		CommandResponse commandResponse = robotCommandService.place(robot, 0, 0, DirectionEnum.NORTH);		
		commandResponse = robotCommandService.move(commandResponse.getRobot());		
		
		assertEquals("0,1,"+DirectionEnum.NORTH, commandResponse.getRobot().report());
	}
	
	/**
	 * This is integration test 2 based on Example B from the requirements.
	 * PLACE 0,0,NORTH
	 * LEFT
	 * REPORT
	 * Result = 0,0,WEST
	 */
	@Test
	public void testCase2() {	
		CommandResponse commandResponse = robotCommandService.place(robot, 0, 0, DirectionEnum.NORTH);		
		commandResponse = robotCommandService.left(commandResponse.getRobot());		
		
		assertEquals("0,0,"+DirectionEnum.WEST, commandResponse.getRobot().report());
	}
	
	/**
	 * This is integration test 3 based on Example C from the requirements.
	 * PLACE 1,2,EAST
	 * MOVE
	 * MOVE
	 * LEFT
	 * MOVE
	 * REPORT
	 * Result = 3,3,NORTH
	 */
	@Test
	public void testCase3() {	
		CommandResponse commandResponse = robotCommandService.place(robot, 1, 2, DirectionEnum.EAST);		
		commandResponse = robotCommandService.move(commandResponse.getRobot());		
		commandResponse = robotCommandService.move(commandResponse.getRobot());	
		commandResponse = robotCommandService.left(commandResponse.getRobot());	
		commandResponse = robotCommandService.move(commandResponse.getRobot());	
		
		assertEquals("3,3,"+DirectionEnum.NORTH, commandResponse.getRobot().report());
	}
}

