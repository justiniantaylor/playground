package com.playground.rea;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.playground.rea.service.robot.command.RobotCommandService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobotApplicationTests {

	@Autowired
    protected RobotCommandService robotCommandService;
	
	@Test
	public void contextLoads() {
		assertThat(robotCommandService).isNotNull();
	}
}
