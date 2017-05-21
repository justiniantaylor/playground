package com.pizzeria.resource.controller.menu;

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

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.pizzeria.resource.domain.Menu;
import com.pizzeria.resource.repository.MenuRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class MenuControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/menu/";
	
	@Autowired
    private MenuRepository menuRepository;
 
	private Menu menu1 = new Menu();
	private Menu menu2 = new Menu();
	
	@Before
	public void setUp() {
		menu1.setStartDate( LocalDate.now());
		menu1.setEndDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		menu1 = menuRepository.save(menu1);
		
		menu2.setStartDate( LocalDate.now().plus(10, ChronoUnit.DAYS));
		menu2.setEndDate(LocalDate.now().plus(15, ChronoUnit.DAYS));
		menu2 = menuRepository.save(menu2);
	}
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL)
			.contentType(contentType))
	        .andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menus[0].startDate").value(menu1.getStartDate().toString()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menus[0].endDate").value(menu1.getEndDate().toString()))
	        
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menus[1].startDate").value(menu2.getStartDate().toString()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.menus[1].endDate").value(menu2.getEndDate().toString()));
	}
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + menu1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(menu1.getStartDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(menu1.getEndDate().toString()));
	}
	
	@Test
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No menu found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreate() throws Exception {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setStartDate(LocalDate.now().plus(25, ChronoUnit.DAYS));
		menuDTO.setEndDate(LocalDate.now().plus(30, ChronoUnit.DAYS));
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(menuDTO.getStartDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(menuDTO.getEndDate().toString()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateSecured() throws Exception {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		menuDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
			.content(this.json(menuDTO))
			.contentType(contentType))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreateWithInvalidStartDate() throws Exception {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setStartDate(LocalDate.now().plus(25, ChronoUnit.DAYS));
		menuDTO.setEndDate(LocalDate.now().plus(24, ChronoUnit.DAYS));
		
		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Start date cannot be before end date\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreateWithInvalidDuplicateMenu() throws Exception {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS)); //starts the same day first menu ends
		menuDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));

		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"There is already an existing menu for the start date supplied\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreateWithValidationErrors() throws Exception {
		MenuDTO menuDTO = new MenuDTO();
		
		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(menuDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Start date is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testUpdate() throws Exception {
		
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(menu1.getId());
		menuDTO.setStartDate(LocalDate.now().plus(6, ChronoUnit.DAYS));
		menuDTO.setEndDate(LocalDate.now().plus(9, ChronoUnit.DAYS));
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + menu1.getId()).with(csrf())
				.content(this.json(menuDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(menu1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(menuDTO.getStartDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(menuDTO.getEndDate().toString()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdateSecured() throws Exception {
		
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(menu1.getId());
		menuDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		menuDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + menu1.getId()).with(csrf())
			.content(this.json(menuDTO))
			.contentType(contentType))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testUpdateWithInvalidId() throws Exception {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(new Long(99));
		menuDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		menuDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(menuDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No menu found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDelete() throws Exception {
		
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + menu1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$").value(menu1.getId()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testDeleteSecured() throws Exception {
		
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + menu1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No menu found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
