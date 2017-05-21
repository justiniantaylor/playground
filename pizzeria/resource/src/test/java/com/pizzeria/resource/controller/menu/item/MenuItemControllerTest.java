package com.pizzeria.resource.controller.menu.item;

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

import com.pizzeria.resource.domain.Menu;
import com.pizzeria.resource.domain.MenuItem;
import com.pizzeria.resource.repository.MenuItemRepository;
import com.pizzeria.resource.repository.MenuRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class MenuItemControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/item/";
	
	@Autowired
    private MenuItemRepository menuItemRepository;

	@Autowired
    private MenuRepository menuRepository;
	
	private MenuItem menuItem1 = new MenuItem();
	private MenuItem menuItem2 = new MenuItem();
	private Menu menu = new Menu();
	
	@Before
	public void setUp() {	
		menu.setStartDate( LocalDate.now());
		menu.setEndDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		menu = menuRepository.save(menu);
		
		menuItem1.setCode("item_1");
		menuItem1.setDescription("Item 1");
		menuItem1.setUnitPriceInCents(new BigDecimal(500));
		menuItem1.setAvailable(true);
    	menuItem1.setMenu(menu);    	
    	menuItem1 = menuItemRepository.save(menuItem1);
    	
    	menuItem2.setCode("item_2");
		menuItem2.setDescription("Item 2");
		menuItem2.setUnitPriceInCents(new BigDecimal(550));
		menuItem2.setAvailable(false);
    	menuItem2.setMenu(menu);    	
    	menuItem2 = menuItemRepository.save(menuItem2);
	}
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get("/menu/" + menu.getId() + CONTROLLER_BASE_URL)
			.contentType(contentType))
	        .andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[0].code").value(menuItem1.getCode()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[0].description").value(menuItem1.getDescription()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[0].unitPriceInCents").value(500.0))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[0].available").value(menuItem1.getAvailable()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[0].menuId").value(menuItem1.getMenu().getId()))

	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[1].code").value(menuItem2.getCode()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[1].description").value(menuItem2.getDescription()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[1].unitPriceInCents").value(550.0))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[1].available").value(menuItem2.getAvailable()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menuItems[1].menuId").value(menuItem2.getMenu().getId()));
	}
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(get("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/" + menuItem1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(menuItem1.getCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(menuItem1.getDescription()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.unitPriceInCents").value(500.0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(menuItem1.getAvailable()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.menuId").value(menuItem1.getMenu().getId()));
	}
	
	@Test
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No menu item found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreate() throws Exception {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
    	menuItemDTO.setCode("item_3");
    	menuItemDTO.setDescription("Item 3");
    	menuItemDTO.setUnitPriceInCents(new BigDecimal(600));
    	menuItemDTO.setAvailable(true);
    	menuItemDTO.setMenuId(menu.getId());

		mockMvc.perform(post("/menu/" + menu.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuItemDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(menuItemDTO.getCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(menuItemDTO.getDescription()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.unitPriceInCents").value(600.0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(menuItemDTO.getAvailable()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.menuId").value(menuItemDTO.getMenuId()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateSecured() throws Exception {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
    	menuItemDTO.setCode("item_3");
    	menuItemDTO.setDescription("Item 3");
    	menuItemDTO.setUnitPriceInCents(new BigDecimal(600));
    	menuItemDTO.setAvailable(true);
    	menuItemDTO.setMenuId(menu.getId());
    	
    	mockMvc.perform(post("/menu/" + menu.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuItemDTO))
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreateWithValidationErrors() throws Exception {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		
		MvcResult result = mockMvc.perform(post("/menu/" + menu.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuItemDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Code is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Description is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Unit price in cents is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Available indicator is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Menu ID is a required field\",\"type\":\"ERROR\"}"));				
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreateWithInvalidMenu() throws Exception {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
    	menuItemDTO.setCode("item_3");
    	menuItemDTO.setDescription("Item 3");
    	menuItemDTO.setUnitPriceInCents(new BigDecimal(600));
    	menuItemDTO.setAvailable(true);
    	menuItemDTO.setMenuId(new Long(99));
		
		mockMvc.perform(post("/menu/" + menu.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuItemDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No menu found for ID 99\",\"type\":\"ERROR\"}"));
	}

	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testUpdate() throws Exception {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		menuItemDTO.setId(menuItem1.getId());
    	menuItemDTO.setCode("item_3");
    	menuItemDTO.setDescription("Item 3");
    	menuItemDTO.setUnitPriceInCents(new BigDecimal(600));
    	menuItemDTO.setAvailable(true);
    	menuItemDTO.setMenuId(menu.getId());

		mockMvc.perform(put("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/" + menuItem1.getId()).with(csrf())
				.content(this.json(menuItemDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(menuItem1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(menuItemDTO.getCode()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(menuItemDTO.getDescription()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.unitPriceInCents").value(600.0))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(menuItemDTO.getAvailable()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.menuId").value(menuItemDTO.getMenuId()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdateSecured() throws Exception {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		menuItemDTO.setId(menuItem1.getId());
    	menuItemDTO.setCode("item_3");
    	menuItemDTO.setDescription("Item 3");
    	menuItemDTO.setUnitPriceInCents(new BigDecimal(600));
    	menuItemDTO.setAvailable(true);
    	menuItemDTO.setMenuId(menu.getId());
    	
    	mockMvc.perform(put("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/" + menuItem1.getId()).with(csrf())
				.content(this.json(menuItemDTO))
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testUpdateWithInvalidId() throws Exception {
		
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		menuItemDTO.setId(new Long(99));
    	menuItemDTO.setCode("item_3");
    	menuItemDTO.setDescription("Item 3");
    	menuItemDTO.setUnitPriceInCents(new BigDecimal(600));
    	menuItemDTO.setAvailable(true);
    	menuItemDTO.setMenuId(menu.getId());
		
		mockMvc.perform(put("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(menuItemDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No menu item found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDelete() throws Exception {
		
		mockMvc.perform(delete("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/" + menuItem1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$").value(menuItem1.getId()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testDeleteSecured() throws Exception {
		
		mockMvc.perform(delete("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/" + menuItem1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete("/menu/" + menu.getId() + CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No menu item found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
