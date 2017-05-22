package com.pizzeria.resource.service.delivery.location.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.pizzeria.resource.service.delivery.location.domain.Delivery;
import com.pizzeria.resource.service.delivery.location.domain.Employee;
import com.pizzeria.resource.service.delivery.location.repository.DeliveryRepository;
import com.pizzeria.resource.service.delivery.location.repository.EmployeeRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class DeliveryLocationControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/delivery/";
	
	@Autowired
    private DeliveryRepository deliveryRepository;

	@Autowired
    private EmployeeRepository employeeRepository;
	
	private Delivery delivery = new Delivery();

	@Before
	public void setUp() {
		delivery.setStartDate(LocalDate.now());
		delivery.setCurrentLatitude(18.4424704);
		delivery.setCurrentLongitude(34.0840328);
		
		Employee employee = new Employee();
	    employee.setFirstName("Justin");
	    employee.setLastName("Taylor");
	    employee.setUserName("employee");
	    employee = employeeRepository.save(employee);
	    delivery.setDeliveryPerson(employee);
	    
	    delivery = deliveryRepository.save(delivery);
	}

	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdateDeliveryLocation() throws Exception {
		
		DeliveryLocationDTO deliveryLocationDTO = new DeliveryLocationDTO();
		deliveryLocationDTO.setLatitude(19.4424704);
		deliveryLocationDTO.setLongitude(35.0840328);

		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + delivery.getId() + "/location").with(csrf())
				.content(this.json(deliveryLocationDTO))
				.contentType(contentType))
		        .andExpect(status().isOk());
		
		 delivery = deliveryRepository.findOne(delivery.getId());
		 
		 Assert.assertEquals(""+19.4424704, ""+delivery.getCurrentLatitude().doubleValue());
		 Assert.assertEquals(""+35.0840328, ""+delivery.getCurrentLongitude().doubleValue());
	}
	
	@Test
	public void testUpdateDeliveryLocationSecured() throws Exception {
		DeliveryLocationDTO deliveryLocationDTO = new DeliveryLocationDTO();
		deliveryLocationDTO.setLatitude(19.4424704);
		deliveryLocationDTO.setLongitude(35.0840328);
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + delivery.getId() + "/location").with(csrf())
				.content(this.json(deliveryLocationDTO))
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdateWithInvalidId() throws Exception {
		DeliveryLocationDTO deliveryLocationDTO = new DeliveryLocationDTO();
		deliveryLocationDTO.setLatitude(19.4424704);
		deliveryLocationDTO.setLongitude(35.0840328);
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + 99 + "/location").with(csrf())
				.content(this.json(deliveryLocationDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No delivery found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
