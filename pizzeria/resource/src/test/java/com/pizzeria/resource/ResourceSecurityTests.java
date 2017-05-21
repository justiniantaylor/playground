package com.pizzeria.resource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ResourceSecurityTests {

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();

	@Test
	public void campaignResourceProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/campaign", String.class, port);
		// N.B. better if it was UNAUTHORIZED but that means we have to add a custom authentication entry point
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void customerResourceProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/customer", String.class, port);
		// N.B. better if it was UNAUTHORIZED but that means we have to add a custom authentication entry point
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void addressResourceProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/customer/1/address", String.class, port);
		// N.B. better if it was UNAUTHORIZED but that means we have to add a custom authentication entry point
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void menuResourceProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/menu", String.class, port);
		// N.B. better if it was UNAUTHORIZED but that means we have to add a custom authentication entry point
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void menuItemResourceProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/menu/1/item", String.class, port);
		// N.B. better if it was UNAUTHORIZED but that means we have to add a custom authentication entry point
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void orderResourceProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/order", String.class, port);
		// N.B. better if it was UNAUTHORIZED but that means we have to add a custom authentication entry point
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void orderItemResourceProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/order/1/item", String.class, port);
		// N.B. better if it was UNAUTHORIZED but that means we have to add a custom authentication entry point
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
}