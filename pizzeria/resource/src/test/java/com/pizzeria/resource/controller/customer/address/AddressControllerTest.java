package com.pizzeria.resource.controller.customer.address;

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

import com.pizzeria.resource.domain.Address;
import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.repository.AddressRepository;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.NotificationMethodRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class AddressControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/address/";
	
	@Autowired
    private AddressRepository addressRepository;

	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private NotificationMethodRepository notificationMethodRepository;
	
	private Address address1 = new Address();
	private Address address2 = new Address();
	private Customer customer = new Customer();
	@Before
	public void setUp() {	
	    customer.setFirstName("Customer");
		customer.setLastName("1");		
		customer.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("sms"));
		customer = customerRepository.save(customer);
		
	    address1.setStreetNumber("15");
	    address1.setStreetName("Stonevillage Close");
	    address1.setNeighborhoodName("Westlake");
	    address1.setCityName("Cape Town");
	    address1.setState("Western Cape");
	    address1.setZipCode("7945");
	    address1.setLongitude(34.0840328);
	    address1.setLatitude(18.4424704);
	    address1.setCustomer(customer);
	    address1 = addressRepository.save(address1);

	    address2.setStreetNumber("4");
	    address2.setStreetName("Stonevillage Rd");
	    address2.setNeighborhoodName("Steenberg");
	    address2.setCityName("Cape Town");
	    address2.setState("Western Cape");
	    address2.setZipCode("7945");
	    address2.setLongitude(35.0840328);
	    address2.setLatitude(19.4424704);
	    address2.setCustomer(customer);
	    address2 = addressRepository.save(address2);
	}
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get("/customer/" + customer.getId() + CONTROLLER_BASE_URL)
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].streetNumber").value(address1.getStreetNumber()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].streetName").value(address1.getStreetName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].neighborhoodName").value(address1.getNeighborhoodName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].cityName").value(address1.getCityName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(address1.getState()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].zipCode").value(address1.getZipCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].longitude").value(address1.getLongitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].latitude").value(address1.getLatitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].customerId").value(address1.getCustomer().getId()))		        
			    		     
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].streetNumber").value(address2.getStreetNumber()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].streetName").value(address2.getStreetName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].neighborhoodName").value(address2.getNeighborhoodName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].cityName").value(address2.getCityName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].state").value(address2.getState()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].zipCode").value(address2.getZipCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].longitude").value(address2.getLongitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].latitude").value(address2.getLatitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[1].customerId").value(address2.getCustomer().getId()));
	}
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(get("/customer/" + customer.getId() + CONTROLLER_BASE_URL + "/" + address1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.streetNumber").value(address1.getStreetNumber()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.streetName").value(address1.getStreetName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.neighborhoodName").value(address1.getNeighborhoodName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value(address1.getCityName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(address1.getState()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(address1.getZipCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(address1.getLongitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(address1.getLatitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(address1.getCustomer().getId()));
	}
	
	@Test
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get("/customer/" + customer.getId() + CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No address found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreate() throws Exception {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setStreetNumber("17");
		addressDTO.setStreetName("Stonevillage Ave");
		addressDTO.setNeighborhoodName("Tokai");
		addressDTO.setCityName("Cape Town");
		addressDTO.setState("Western Cape");
		addressDTO.setZipCode("7945");
		addressDTO.setLongitude(36.0840328);
		addressDTO.setLatitude(20.4424704);
		addressDTO.setCustomerId(address1.getCustomer().getId());

		mockMvc.perform(post("/customer/" + customer.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(addressDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.streetNumber").value(addressDTO.getStreetNumber()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.streetName").value(addressDTO.getStreetName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.neighborhoodName").value(addressDTO.getNeighborhoodName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value(addressDTO.getCityName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(addressDTO.getState()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(addressDTO.getZipCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(addressDTO.getLongitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(addressDTO.getLatitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(addressDTO.getCustomerId()));
	}
	
	@Test
	public void testCreateWithValidationErrors() throws Exception {
		AddressDTO addressDTO = new AddressDTO();
		
		MvcResult result = mockMvc.perform(post("/customer/" + customer.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(addressDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Street number is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Street name is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Neighborhood name is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"City name is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"State is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Zip code is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Longitude is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Latitude is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Customer ID is a required field\",\"type\":\"ERROR\"}"));
				
	}
	
	@Test
	public void testCreateWithInvalidCustomer() throws Exception {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setStreetNumber("17");
		addressDTO.setStreetName("Stonevillage Ave");
		addressDTO.setNeighborhoodName("Tokai");
		addressDTO.setCityName("Cape Town");
		addressDTO.setState("Western Cape");
		addressDTO.setZipCode("7945");
		addressDTO.setLongitude(36.0840328);
		addressDTO.setLatitude(20.4424704);
		addressDTO.setCustomerId(new Long(99));
		
		mockMvc.perform(post("/customer/" + customer.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(addressDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No customer found for ID 99\",\"type\":\"ERROR\"}"));
	}

	@Test
	public void testUpdate() throws Exception {
		
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(address1.getId());
		addressDTO.setStreetNumber("17");
		addressDTO.setStreetName("Stonevillage Ave");
		addressDTO.setNeighborhoodName("Tokai");
		addressDTO.setCityName("Cape Town");
		addressDTO.setState("Western Cape");
		addressDTO.setZipCode("7945");
		addressDTO.setLongitude(36.0840328);
		addressDTO.setLatitude(20.4424704);
		addressDTO.setCustomerId(address1.getCustomer().getId());

		mockMvc.perform(put("/customer/" + customer.getId() + CONTROLLER_BASE_URL + "/" + address1.getId()).with(csrf())
				.content(this.json(addressDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(address1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.streetNumber").value(addressDTO.getStreetNumber()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.streetName").value(addressDTO.getStreetName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.neighborhoodName").value(addressDTO.getNeighborhoodName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value(addressDTO.getCityName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(addressDTO.getState()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(addressDTO.getZipCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(addressDTO.getLongitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(addressDTO.getLatitude()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(addressDTO.getCustomerId()));
	}
	
	@Test
	public void testUpdateWithInvalidId() throws Exception {
		
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(new Long(99));
		addressDTO.setStreetNumber("17");
		addressDTO.setStreetName("Stonevillage Ave");
		addressDTO.setNeighborhoodName("Tokai");
		addressDTO.setCityName("Cape Town");
		addressDTO.setState("Western Cape");
		addressDTO.setZipCode("7945");
		addressDTO.setLongitude(36.0840328);
		addressDTO.setLatitude(20.4424704);
		addressDTO.setCustomerId(address1.getCustomer().getId());
		
		mockMvc.perform(put("/customer/" + customer.getId() + CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(addressDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No address found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testDelete() throws Exception {
		
		mockMvc.perform(delete("/customer/" + customer.getId() + CONTROLLER_BASE_URL + "/" + address1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$").value(address1.getId()));
	}
	
	@Test
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete("/customer/" + customer.getId() + CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No address found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
