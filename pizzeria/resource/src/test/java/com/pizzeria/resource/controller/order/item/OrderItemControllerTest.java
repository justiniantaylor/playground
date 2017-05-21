package com.pizzeria.resource.controller.order.item;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.pizzeria.resource.domain.Address;
import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.domain.Menu;
import com.pizzeria.resource.domain.MenuItem;
import com.pizzeria.resource.domain.Order;
import com.pizzeria.resource.domain.OrderItem;
import com.pizzeria.resource.repository.AddressRepository;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.MenuItemRepository;
import com.pizzeria.resource.repository.MenuRepository;
import com.pizzeria.resource.repository.NotificationMethodRepository;
import com.pizzeria.resource.repository.OrderItemRepository;
import com.pizzeria.resource.repository.OrderRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class OrderItemControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/item/";
	
	@Autowired
    private OrderItemRepository orderItemRepository;

	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private NotificationMethodRepository notificationMethodRepository;
	
	@Autowired
    private AddressRepository addressRepository;
	
	@Autowired
    private MenuRepository menuRepository;
	
	@Autowired
    private MenuItemRepository menuItemRepository;
	
	private Order order = new Order();
	private OrderItem orderItem1 = new OrderItem();
	private OrderItem orderItem2 = new OrderItem();
	
	@Before
	public void setUp() {	
		//order
		order.setDeliver(true);	    
	    
	    Customer customer = new Customer();
	    customer.setFirstName("Customer");
	    customer.setLastName("1");		
	    customer.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("sms"));	   
	    customer = customerRepository.save(customer);
		order.setCustomer(customer);			   

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
	    order.setAddress(address);
	 
	    order.setOrderedDate(LocalDate.now());
	    
	    order = orderRepository.save(order);
				
	    //menu and items
	    Menu menu = new Menu();
	    menu.setStartDate( LocalDate.now());
		menu.setEndDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		menu = menuRepository.save(menu);
		
		MenuItem menuItem1 = new MenuItem();
		menuItem1.setCode("item_1");
		menuItem1.setDescription("Item 1");
		menuItem1.setUnitPriceInCents(new BigDecimal(500));
		menuItem1.setAvailable(true);
    	menuItem1.setMenu(menu);    	
    	menuItem1 = menuItemRepository.save(menuItem1);
    	
    	MenuItem menuItem2 = new MenuItem();
		menuItem2.setCode("item_1");
		menuItem2.setDescription("Item 1");
		menuItem2.setUnitPriceInCents(new BigDecimal(550));
		menuItem2.setAvailable(true);
    	menuItem2.setMenu(menu);    	
    	menuItem2 = menuItemRepository.save(menuItem2);
    	
    	//order items
	    orderItem1.setQuantity(2);    	
	    orderItem1.setUnitPriceInCents(menuItem1.getUnitPriceInCents());    	
	    orderItem1.setOrder(order);    	
    	orderItem1.setMenuItem(menuItem1);
    	orderItem1 = orderItemRepository.save(orderItem1);
    	
    	orderItem2.setQuantity(3);    	
	    orderItem2.setUnitPriceInCents(menuItem2.getUnitPriceInCents());    	
	    orderItem2.setOrder(order);    	
    	orderItem2.setMenuItem(menuItem2);
    	orderItem2 = orderItemRepository.save(orderItem2);
	}
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get("/order/" + order.getId() + CONTROLLER_BASE_URL)
			.contentType(contentType))
	        .andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].quantity").value(orderItem1.getQuantity()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].unitPriceInCents").value(500.0))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].orderId").value(orderItem1.getOrder().getId()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].menuItemId").value(orderItem1.getMenuItem().getId()))
	        
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[1].quantity").value(orderItem2.getQuantity()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[1].unitPriceInCents").value(550.0))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[1].orderId").value(orderItem2.getOrder().getId()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[1].menuItemId").value(orderItem2.getMenuItem().getId()));
	}
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(get("/order/" + order.getId() + CONTROLLER_BASE_URL + "/" + orderItem1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(orderItem1.getQuantity()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.unitPriceInCents").value(500.0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(orderItem1.getOrder().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItemId").value(orderItem1.getMenuItem().getId()));
	}
	
	@Test
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get("/order/" + order.getId() + CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No order item found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreate() throws Exception {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
    	orderItemDTO.setQuantity(4);    	
    	orderItemDTO.setUnitPriceInCents(orderItem1.getUnitPriceInCents());    	
    	orderItemDTO.setOrderId(orderItem1.getOrder().getId());    	
    	orderItemDTO.setMenuItemId(orderItem1.getMenuItem().getId());

		mockMvc.perform(post("/order/" + order.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderItemDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(orderItemDTO.getQuantity()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.unitPriceInCents").value(500.0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(orderItemDTO.getOrderId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItemId").value(orderItemDTO.getMenuItemId()));
	}
	
	@Test
	public void testCreateWithValidationErrors() throws Exception {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		
		MvcResult result = mockMvc.perform(post("/order/" + order.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderItemDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Quantity is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Unit price in cents a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Order ID a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Menu item ID is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreateWithInvalidOrder() throws Exception {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
    	orderItemDTO.setQuantity(4);    	
    	orderItemDTO.setUnitPriceInCents(orderItem1.getUnitPriceInCents());    	
    	orderItemDTO.setOrderId(new Long(99));    	
    	orderItemDTO.setMenuItemId(orderItem1.getMenuItem().getId());
		
		mockMvc.perform(post("/order/" + order.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderItemDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No order found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreateWithInvalidMenuItem() throws Exception {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
    	orderItemDTO.setQuantity(4);    	
    	orderItemDTO.setUnitPriceInCents(orderItem1.getUnitPriceInCents());    	
    	orderItemDTO.setOrderId(orderItem1.getMenuItem().getId());    	
    	orderItemDTO.setMenuItemId(new Long(99));
		
		mockMvc.perform(post("/order/" + order.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(orderItemDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No menu item found for ID 99\",\"type\":\"ERROR\"}"));
	}

	@Test
	public void testUpdate() throws Exception {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(orderItem1.getId());
    	orderItemDTO.setQuantity(4);    	
    	orderItemDTO.setUnitPriceInCents(orderItem1.getUnitPriceInCents());    	
    	orderItemDTO.setOrderId(orderItem1.getOrder().getId());    	
    	orderItemDTO.setMenuItemId(orderItem1.getMenuItem().getId());

		mockMvc.perform(put("/order/" + order.getId() + CONTROLLER_BASE_URL + "/" + orderItem1.getId()).with(csrf())
				.content(this.json(orderItemDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(orderItem1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(orderItemDTO.getQuantity()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.unitPriceInCents").value(500.0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(orderItemDTO.getOrderId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItemId").value(orderItemDTO.getMenuItemId()));
	}
	
	@Test
	public void testUpdateWithInvalidId() throws Exception {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(new Long(99));
    	orderItemDTO.setQuantity(4);    	
    	orderItemDTO.setUnitPriceInCents(orderItem1.getUnitPriceInCents());    	
    	orderItemDTO.setOrderId(orderItem1.getOrder().getId());    	
    	orderItemDTO.setMenuItemId(orderItem1.getMenuItem().getId());
		
		mockMvc.perform(put("/order/" + order.getId() + CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(orderItemDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No order item found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDelete() throws Exception {
		
		mockMvc.perform(delete("/order/" + order.getId() + CONTROLLER_BASE_URL + "/" + orderItem1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$").value(orderItem1.getId()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testDeleteSecured() throws Exception {
		
		mockMvc.perform(delete("/order/" + order.getId() + CONTROLLER_BASE_URL + "/" + orderItem1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete("/order/" + order.getId() + CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No order item found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
