package com.pizzeria.resource.controller.order;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.pizzeria.resource.domain.Address;
import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.domain.Employee;
import com.pizzeria.resource.domain.Order;
import com.pizzeria.resource.repository.AddressRepository;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.EmployeeRepository;
import com.pizzeria.resource.repository.NotificationMethodRepository;
import com.pizzeria.resource.repository.OrderRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class OrderControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/order/";
	
	@Autowired
    private OrderRepository orderRepository;

	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
	 
	@Autowired
    private NotificationMethodRepository notificationMethodRepository;
	
	@Autowired
    private AddressRepository addressRepository;
	
	private Order order1 = new Order();
	private Order order2 = new Order();

	@Before
	public void setUp() {		
		//order 1
	    order1.setDeliver(true);	    
	    
	    Customer customer = new Customer();
	    customer.setFirstName("Customer");
	    customer.setLastName("1");		
	    customer.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("sms"));	   
	    customer = customerRepository.save(customer);
		order1.setCustomer(customer);			   

		Address address = new Address();
		address.setStreetNumber("15");
		address.setStreetName("Stonevillage Close");
		address.setNeighborhoodName("Westlake");
		address.setCityName("Cape Town");
		address.setState("Western Cape");
		address.setZipCode("7945");
		address.setLongitude(34.0840328);
	    address.setLatitude(18.4424704);
	    address.setCustomer(customer);
	    address = addressRepository.save(address);
	    order1.setAddress(address);
	 
	    order1.setOrderedDate(LocalDate.now());
	    
	    order1 = orderRepository.save(order1);
	    
	    //order 2
	    order2.setDeliver(false);	    
	    
	    Employee employee = new Employee();
	    employee.setFirstName("Justin");
	    employee.setLastName("Taylor");
	    employee.setUserName("employee");
	    employee = employeeRepository.save(employee);
	    order2.setCashier(employee);
	 
	    order2.setOrderedDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
	    
	    order2 = orderRepository.save(order2);
	}
	
	@After
	public void tearDown() {
		orderRepository.delete(order1.getId());
		orderRepository.delete(order2.getId());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testFindAll() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL)
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[0].deliver").value(true))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[0].cashierId").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[0].customerId").value(order1.getCustomer().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[0].addressId").value(order1.getAddress().getId()))	 
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[0].orderedDate").value(order1.getOrderedDate().toString()))	   
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[0].fulfilledDate").isEmpty())	   
			    		     
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[1].deliver").value(false))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[1].cashierId").value(order2.getCashier().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[1].customerId").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[1].addressId").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[1].orderedDate").value(order2.getOrderedDate().toString()))	 
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orders[1].fulfilledDate").isEmpty());	   
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testFind() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + order1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.deliver").value(true))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.cashierId").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(order1.getCustomer().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addressId").value(order1.getAddress().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orderedDate").value(order1.getOrderedDate().toString()))	   
		        .andExpect(MockMvcResultMatchers.jsonPath("$.fulfilledDate").isEmpty());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No order found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreate() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setDeliver(true);
		orderDTO.setCustomerId(order1.getCustomer().getId());
		orderDTO.setAddressId(order1.getAddress().getId());

		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.deliver").value(true))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.cashierId").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(order1.getCustomer().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addressId").value(order1.getAddress().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orderedDate").value(order1.getOrderedDate().toString()))	   
		        .andExpect(MockMvcResultMatchers.jsonPath("$.fulfilledDate").isEmpty());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithValidationErrors() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		
		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Deliver is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithInvalidCashier() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setDeliver(false);
		orderDTO.setCashierId(new Long(99));
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No employee found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithInvalidCustomer() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setDeliver(true);
		orderDTO.setCustomerId(new Long(99));
		orderDTO.setAddressId(order1.getAddress().getId());
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No customer found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithInvalidAddress() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setDeliver(true);
		orderDTO.setCustomerId(order1.getCustomer().getId());
		orderDTO.setAddressId(new Long(99));
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No address found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithDeliverMissingCustomer() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setDeliver(true);
		orderDTO.setAddressId(new Long(99));
		
		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Customer is required if delivery has been requested\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithDeliverMissingAddress() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setDeliver(true);
		orderDTO.setCustomerId(order1.getCustomer().getId());
		
		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Address is required if delivery has been requested\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateWithInStoreMissingCashier() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setDeliver(false);

		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Cashier is required if this is in store\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdate() throws Exception {
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order1.getId());
		orderDTO.setDeliver(false);
		orderDTO.setCashierId(order2.getCashier().getId());
		orderDTO.setCustomerId(null);
		orderDTO.setAddressId(null);

		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + order1.getId()).with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(order1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.deliver").value(false))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.cashierId").value(order2.getCashier().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.addressId").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orderedDate").value(order1.getOrderedDate().toString()))	   
		        .andExpect(MockMvcResultMatchers.jsonPath("$.fulfilledDate").isEmpty());
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdateWithInvalidId() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(new Long(99));
		orderDTO.setDeliver(false);
		orderDTO.setCashierId(order2.getCashier().getId());
		orderDTO.setCustomerId(null);
		orderDTO.setAddressId(null);
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(orderDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No order found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testDeleteSecured() throws Exception {
		
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + order1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No order found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
