package com.pizzeria.resource.controller.campaign;

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

import com.pizzeria.resource.domain.Campaign;
import com.pizzeria.resource.repository.CampaignRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class CampaignControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/campaign/";
	
	@Autowired
    private CampaignRepository campaignRepository;
 
	private Campaign campaign1 = new Campaign();
	private Campaign campaign2 = new Campaign();
	
	@Before
	public void setUp() {
		campaign1.setName("Campaign 1");
		campaign1.setText("Campaign 1 Special");
		campaign1.setStartDate(LocalDate.now());
		campaign1.setEndDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		campaign1 = campaignRepository.save(campaign1);
		
		campaign2.setName("Campaign 2");
		campaign2.setText("Campaign 2 Special");
		campaign2.setStartDate(LocalDate.now().plus(6, ChronoUnit.DAYS));
		campaign2.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		campaign2 = campaignRepository.save(campaign2);
	}
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL)
			.contentType(contentType))
	        .andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[0].name").value(campaign1.getName()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[0].text").value(campaign1.getText()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[0].startDate").value(campaign1.getStartDate().toString()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[0].endDate").value(campaign1.getEndDate().toString()))
	        
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[1].name").value(campaign2.getName()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[1].text").value(campaign2.getText()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[1].startDate").value(campaign2.getStartDate().toString()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.campaigns[1].endDate").value(campaign2.getEndDate().toString()));
	}
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + campaign1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(campaign1.getName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(campaign1.getText()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(campaign1.getStartDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(campaign1.getEndDate().toString()));
	}
	
	@Test
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No campaign found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreate() throws Exception {
		CampaignDTO campaignDTO = new CampaignDTO();
		campaignDTO.setName("Campaign 3");
		campaignDTO.setText("Campaign 3 Special");
		campaignDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		campaignDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(campaignDTO.getName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(campaignDTO.getText()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(campaignDTO.getStartDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(campaignDTO.getEndDate().toString()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testCreateSecured() throws Exception {
		CampaignDTO campaignDTO = new CampaignDTO();
		campaignDTO.setName("Campaign 3");
		campaignDTO.setText("Campaign 3 Special");
		campaignDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		campaignDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
			.content(this.json(campaignDTO))
			.contentType(contentType))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testCreateWithValidationErrors() throws Exception {
		CampaignDTO campaignDTO = new CampaignDTO();
		
		MvcResult result = mockMvc.perform(post(CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Name is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Text is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Start date is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"End date is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testUpdate() throws Exception {
		
		CampaignDTO campaignDTO = new CampaignDTO();
		campaignDTO.setId(campaign1.getId());
		campaignDTO.setName("Campaign 3");
		campaignDTO.setText("Campaign 3 Special");
		campaignDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		campaignDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + campaign1.getId()).with(csrf())
				.content(this.json(campaignDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(campaign1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(campaignDTO.getName()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(campaignDTO.getText()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(campaignDTO.getStartDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(campaignDTO.getEndDate().toString()));
	}
	
	@Test
	@WithMockUser(roles={"USER", "EMPLOYEE"})
	public void testUpdateSecured() throws Exception {
		
		CampaignDTO campaignDTO = new CampaignDTO();
		campaignDTO.setId(campaign1.getId());
		campaignDTO.setName("Campaign 3");
		campaignDTO.setText("Campaign 3 Special");
		campaignDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		campaignDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/" + campaign1.getId()).with(csrf())
			.content(this.json(campaignDTO))
			.contentType(contentType))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testUpdateWithInvalidId() throws Exception {
		CampaignDTO campaignDTO = new CampaignDTO();
		campaignDTO.setId(new Long(99));
		campaignDTO.setName("Campaign 3");
		campaignDTO.setText("Campaign 3 Special");
		campaignDTO.setStartDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		campaignDTO.setEndDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
		
		mockMvc.perform(put(CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(campaignDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No campaign found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDelete() throws Exception {
		
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + campaign1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$").value(campaign1.getId()));
	}
	
	@Test
	public void testDeleteSecured() throws Exception {
		
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + campaign1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles={"USER", "MANAGER"})
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete(CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No campaign found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
