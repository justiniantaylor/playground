package com.pizzeria.resource.controller.customer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.NotificationMethodRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class CustomerControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/customer/";
	
	@Autowired
    private CustomerRepository customerRepository;

	@Autowired
    private NotificationMethodRepository notificationMethodRepository;
	 
	private Customer customer1 = new Customer();
	private Customer customer2 = new Customer();
	
	@Before
	public void setUp() {
		customer1.setFirstName("Customer");
		customer1.setLastName("1");		
		customer1.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("sms"));
		customer1 = customerRepository.save(customer1);
		
		customer2.setFirstName("Customer");
		customer2.setLastName("2");		
		customer2.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("push_notification"));
		customer2 = customerRepository.save(customer2);
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testFindAll() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL)
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].firstName").value(customer1.getFirstName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].lastName").value(customer1.getLastName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].preferredNotificationMethodId").value(customer1.getPreferredNotificationMethod().getId()))
		        
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customers[1].firstName").value(customer2.getFirstName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customers[1].lastName").value(customer2.getLastName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customers[1].preferredNotificationMethodId").value(customer2.getPreferredNotificationMethod().getId()));
	}
	
	@Test
	public void testFindAllSecured() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL)
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testFind() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + customer1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customer1.getFirstName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customer1.getLastName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.preferredNotificationMethodId").value(customer1.getPreferredNotificationMethod().getId()));
	}
	
	@Test
	public void testFindSecured() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + customer1.getId())
			.contentType(contentType))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No customer found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreate() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Customer");
		customerDTO.setLastName("3");
		customerDTO.setPreferredNotificationMethodId(notificationMethodRepository.findOneByCode("sms").getId());
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(customerDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customerDTO.getFirstName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customerDTO.getLastName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.preferredNotificationMethodId").value(customerDTO.getPreferredNotificationMethodId()));
	}
	
	@Test
	public void testCreateSecured() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Customer");
		customerDTO.setLastName("3");
		customerDTO.setPreferredNotificationMethodId(notificationMethodRepository.findOneByCode("sms").getId());
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
			.content(this.json(customerDTO))
			.contentType(contentType))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithValidationErrors() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		
		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(customerDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"First name is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Last name is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Preferred communication method is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithInvalidNotificationMethod() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Customer");
		customerDTO.setLastName("3");
		customerDTO.setPreferredNotificationMethodId(new Long(99));
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(customerDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No notification method found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdate() throws Exception {
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customer1.getId());
		customerDTO.setFirstName("Update Customer");
		customerDTO.setLastName("4");
		customerDTO.setPreferredNotificationMethodId(notificationMethodRepository.findOneByCode("push_notification").getId());
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + customer1.getId()).with(csrf())
				.content(this.json(customerDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customer1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customerDTO.getFirstName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customerDTO.getLastName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.preferredNotificationMethodId").value(customerDTO.getPreferredNotificationMethodId()));
	}
	
	@Test
	public void testUpdateSecured() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customer1.getId());
		customerDTO.setFirstName("Update Customer");
		customerDTO.setLastName("4");
		customerDTO.setPreferredNotificationMethodId(notificationMethodRepository.findOneByCode("push_notification").getId());
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + customer1.getId()).with(csrf())
				.content(this.json(customerDTO))
				.contentType(contentType))
				.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdateWithInvalidId() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(new Long(99));
		customerDTO.setFirstName("Update Customer");
		customerDTO.setLastName("4");
		customerDTO.setPreferredNotificationMethodId(notificationMethodRepository.findOneByCode("push_notification").getId());
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(customerDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No customer found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDelete() throws Exception {
		
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + customer1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$").value(customer1.getId()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testDeleteSecured() throws Exception {
		
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + customer1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No customer found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
