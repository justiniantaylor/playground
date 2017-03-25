package com.playground.rea.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.playground.rea.domain.Robot;
import com.playground.rea.util.RestControllerTest;

public class RobotControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/robot/";
	
	private Robot robot;
	
	@Before
    public void setUp() {
        robot = new Robot(0,0,null);
    }
	
	@Test
	public void testCreate() throws Exception {
		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(robot))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.x").value(0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.y").value(0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.facing").isEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.table").isEmpty());
	}
}
