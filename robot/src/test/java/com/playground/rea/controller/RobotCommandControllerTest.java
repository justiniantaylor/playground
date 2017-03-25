package com.playground.rea.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.playground.rea.domain.Robot;
import com.playground.rea.domain.Table;
import com.playground.rea.util.RestControllerTest;
import com.playground.rea.util.direction.DirectionEnum;

public class RobotCommandControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/robot/command/";
	
	private Robot robot;
	
	@Before
    public void setUp() {
        robot = new Robot(0,0,null);
    }
	
	@Test
	public void testPlace() throws Exception {
		mockMvc.perform(post(CONTROLLER_BASE_URL + "place/1/1/"+DirectionEnum.NORTH)
				.content(this.json(robot))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.commandAccepted").value(true))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.x").value(1))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.y").value(1))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.facing").value(DirectionEnum.NORTH.toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Placed robot at x=1, y=1 facing "+DirectionEnum.NORTH));
	}
	
	@Test
	public void testMove() throws Exception {
		robot.setX(1);
		robot.setY(1);
		robot.setFacing(DirectionEnum.NORTH);
		robot.setTable(new Table());
		
		mockMvc.perform(post(CONTROLLER_BASE_URL + "move")
				.content(this.json(robot))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.commandAccepted").value(true))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.x").value(1))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.y").value(2))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.facing").value(DirectionEnum.NORTH.toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Moved robot " + DirectionEnum.NORTH + " to x=1, y=2"));
	}
	
	@Test
	public void testLeft() throws Exception {
		robot.setX(1);
		robot.setY(1);
		robot.setFacing(DirectionEnum.NORTH);
		robot.setTable(new Table());
		
		mockMvc.perform(post(CONTROLLER_BASE_URL + "left")
				.content(this.json(robot))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.commandAccepted").value(true))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.x").value(1))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.y").value(1))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.facing").value(DirectionEnum.WEST.toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Turned robot left, it is now facing " + DirectionEnum.WEST));
	}
	
	@Test
	public void testRight() throws Exception {
		robot.setX(1);
		robot.setY(1);
		robot.setFacing(DirectionEnum.NORTH);
		robot.setTable(new Table());
		
		mockMvc.perform(post(CONTROLLER_BASE_URL + "right")
				.content(this.json(robot))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.commandAccepted").value(true))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.x").value(1))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.y").value(1))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.robot.facing").value(DirectionEnum.EAST.toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Turned robot right, it is now facing " + DirectionEnum.EAST));
	}
}
