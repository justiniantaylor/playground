package com.playground.rea;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.playground.rea.service.robot.RobotService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class RobotApplicationTests {

	@Autowired
    protected RobotService robotService;
	
	@Test
	public void contextLoads() {
		assertThat(robotService).isNotNull();
	}
}
