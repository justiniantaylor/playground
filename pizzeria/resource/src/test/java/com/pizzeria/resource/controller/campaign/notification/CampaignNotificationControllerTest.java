package com.pizzeria.resource.controller.campaign.notification;

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
import com.pizzeria.resource.domain.CampaignNotification;
import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.repository.CampaignNotificationRepository;
import com.pizzeria.resource.repository.CampaignRepository;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.NotificationMethodRepository;
import com.pizzeria.resource.repository.NotificationStatusRepository;
import com.pizzeria.resource.util.RestControllerTest;

@WithMockUser(roles={"USER"})
public class CampaignNotificationControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/notification/";
	
	@Autowired
    private CampaignNotificationRepository campaignNotificationRepository;

	@Autowired
    private CampaignRepository campaignRepository;
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private NotificationMethodRepository notificationMethodRepository;
	
	@Autowired
    private NotificationStatusRepository notificationStatusRepository;
	
	private CampaignNotification campaignNotification1 = new CampaignNotification();
	private CampaignNotification campaignNotification2 = new CampaignNotification();
	
	private Campaign campaign = new Campaign();
	
	@Before
	public void setUp() {	
		campaign.setName("Campaign");
		campaign.setText("Campaign Special");
		campaign.setStartDate(LocalDate.now());
		campaign.setEndDate(LocalDate.now().plus(5, ChronoUnit.DAYS));
		campaign = campaignRepository.save(campaign);
		
		Customer customer1 = new Customer();
		customer1.setFirstName("Customer");
		customer1.setLastName("1");		
		customer1.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("sms"));
		customer1 = customerRepository.save(customer1);
		
		Customer customer2 = new Customer();		
		customer2.setFirstName("Customer");
		customer2.setLastName("2");		
		customer2.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("push_notification"));
		customer2 = customerRepository.save(customer2);
		
		campaignNotification1.setNotificationDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
    	campaignNotification1.setCampaign(campaign);
    	campaignNotification1.setCustomer(customer1);
    	campaignNotification1.setNotificationMethod(notificationMethodRepository.findOneByCode("sms"));
    	campaignNotification1.setNotificationStatus(notificationStatusRepository.findOneByCode("sent"));
    	campaignNotification1 = campaignNotificationRepository.save(campaignNotification1);
    	
    	campaignNotification2.setNotificationDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
    	campaignNotification2.setCampaign(campaign);
    	campaignNotification2.setCustomer(customer2);
    	campaignNotification2.setNotificationMethod(notificationMethodRepository.findOneByCode("push_notification"));
    	campaignNotification2.setNotificationStatus(notificationStatusRepository.findOneByCode("failed"));
    	campaignNotification2 = campaignNotificationRepository.save(campaignNotification2);
	}
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL)
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[0].notificationDate").value(campaignNotification1.getNotificationDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[0].campaignId").value(campaignNotification1.getCampaign().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[0].customerId").value(campaignNotification1.getCustomer().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[0].notificationMethodId").value(campaignNotification1.getNotificationMethod().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[0].notificationStatusId").value(campaignNotification1.getNotificationStatus().getId()))		        	       
			    		     
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[1].notificationDate").value(campaignNotification2.getNotificationDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[1].campaignId").value(campaignNotification2.getCampaign().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[1].customerId").value(campaignNotification2.getCustomer().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[1].notificationMethodId").value(campaignNotification2.getNotificationMethod().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignNotifications[1].notificationStatusId").value(campaignNotification2.getNotificationStatus().getId()));
	}
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(get("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL + "/" + campaignNotification1.getId())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationDate").value(campaignNotification1.getNotificationDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignId").value(campaignNotification1.getCampaign().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(campaignNotification1.getCustomer().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationMethodId").value(campaignNotification1.getNotificationMethod().getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationStatusId").value(campaignNotification1.getNotificationStatus().getId()));
	}
	
	@Test
	public void testFindWithInvalidId() throws Exception {
		mockMvc.perform(get("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL + "/" + 99)
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No campaign notification found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreate() throws Exception {
		Customer customer3 = new Customer();		
		customer3.setFirstName("Customer");
		customer3.setLastName("3");		
		customer3.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("push_notification"));
		customer3 = customerRepository.save(customer3);
		
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
    	campaignNotificationDTO.setCampaignId(campaignNotification1.getCampaign().getId());
    	campaignNotificationDTO.setCustomerId(customer3.getId());
    	campaignNotificationDTO.setNotificationDate(campaignNotification1.getNotificationDate());    
    	campaignNotificationDTO.setNotificationMethodId(campaignNotification1.getNotificationMethod().getId());	
    	campaignNotificationDTO.setNotificationStatusId(campaignNotification1.getNotificationStatus().getId());

		mockMvc.perform(post("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationDate").value(campaignNotificationDTO.getNotificationDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignId").value(campaignNotificationDTO.getCampaignId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(campaignNotificationDTO.getCustomerId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationMethodId").value(campaignNotificationDTO.getNotificationMethodId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationStatusId").value(campaignNotificationDTO.getNotificationStatusId()));
	}
	
	@Test
	public void testCreateWithValidationErrors() throws Exception {
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
		
		MvcResult result = mockMvc.perform(post("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isBadRequest()).andReturn();;
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, containsString("{\"message\":\"Notification date is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Campaign ID is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Customer ID is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Notification method ID is a required field\",\"type\":\"ERROR\"}"));
		assertThat(content, containsString("{\"message\":\"Notification status ID is a required field\",\"type\":\"ERROR\"}"));
				
	}
	
	@Test
	public void testCreateWithInvalidCampaign() throws Exception {
		Customer customer3 = new Customer();		
		customer3.setFirstName("Customer");
		customer3.setLastName("3");		
		customer3.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("push_notification"));
		customer3 = customerRepository.save(customer3);
		
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
    	campaignNotificationDTO.setCampaignId(new Long(99));
    	campaignNotificationDTO.setCustomerId(customer3.getId());
    	campaignNotificationDTO.setNotificationDate(campaignNotification1.getNotificationDate());    
    	campaignNotificationDTO.setNotificationMethodId(campaignNotification1.getNotificationMethod().getId());	
    	campaignNotificationDTO.setNotificationStatusId(campaignNotification1.getNotificationStatus().getId());
		
		mockMvc.perform(post("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No campaign found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreateWithInvalidCustomer() throws Exception {
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
    	campaignNotificationDTO.setCampaignId(campaignNotification1.getCampaign().getId());
    	campaignNotificationDTO.setCustomerId(new Long(99));
    	campaignNotificationDTO.setNotificationDate(campaignNotification1.getNotificationDate());    
    	campaignNotificationDTO.setNotificationMethodId(campaignNotification1.getNotificationMethod().getId());	
    	campaignNotificationDTO.setNotificationStatusId(campaignNotification1.getNotificationStatus().getId());
		
		mockMvc.perform(post("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No customer found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreateWithInvalidNotificationMethod() throws Exception {
		Customer customer3 = new Customer();		
		customer3.setFirstName("Customer");
		customer3.setLastName("3");		
		customer3.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("push_notification"));
		customer3 = customerRepository.save(customer3);
		
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
    	campaignNotificationDTO.setCampaignId(campaignNotification1.getCampaign().getId());
    	campaignNotificationDTO.setCustomerId(customer3.getId());
    	campaignNotificationDTO.setNotificationDate(campaignNotification1.getNotificationDate());    
    	campaignNotificationDTO.setNotificationMethodId(new Long(99));	
    	campaignNotificationDTO.setNotificationStatusId(campaignNotification1.getNotificationStatus().getId());
		
		mockMvc.perform(post("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No notification method found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCreateWithInvalidNotificationStatus() throws Exception {
		Customer customer3 = new Customer();		
		customer3.setFirstName("Customer");
		customer3.setLastName("3");		
		customer3.setPreferredNotificationMethod(notificationMethodRepository.findOneByCode("push_notification"));
		customer3 = customerRepository.save(customer3);
		
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
    	campaignNotificationDTO.setCampaignId(campaignNotification1.getCampaign().getId());
    	campaignNotificationDTO.setCustomerId(customer3.getId());
    	campaignNotificationDTO.setNotificationDate(campaignNotification1.getNotificationDate());    
    	campaignNotificationDTO.setNotificationMethodId(campaignNotification1.getNotificationMethod().getId());	
    	campaignNotificationDTO.setNotificationStatusId(new Long(99));
		
		mockMvc.perform(post("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL).with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No notification status found for ID 99\",\"type\":\"ERROR\"}"));
	}

	@Test
	public void testUpdate() throws Exception {
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
		campaignNotificationDTO.setId(campaignNotification1.getId());
    	campaignNotificationDTO.setCampaignId(campaignNotification2.getCampaign().getId());
    	campaignNotificationDTO.setCustomerId(campaignNotification2.getCustomer().getId());
    	campaignNotificationDTO.setNotificationDate(campaignNotification2.getNotificationDate());    
    	campaignNotificationDTO.setNotificationMethodId(campaignNotification2.getNotificationMethod().getId());	
    	campaignNotificationDTO.setNotificationStatusId(campaignNotification2.getNotificationStatus().getId());

		mockMvc.perform(put("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL + "/" + campaignNotification1.getId()).with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(campaignNotification1.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationDate").value(campaignNotificationDTO.getNotificationDate().toString()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.campaignId").value(campaignNotificationDTO.getCampaignId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(campaignNotificationDTO.getCustomerId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationMethodId").value(campaignNotificationDTO.getNotificationMethodId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationStatusId").value(campaignNotificationDTO.getNotificationStatusId()));
	}
	
	@Test
	public void testUpdateWithInvalidId() throws Exception {
		CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
		campaignNotificationDTO.setId(new Long(99));
    	campaignNotificationDTO.setCampaignId(campaignNotification2.getCampaign().getId());
    	campaignNotificationDTO.setCustomerId(campaignNotification2.getCustomer().getId());
    	campaignNotificationDTO.setNotificationDate(campaignNotification2.getNotificationDate());    
    	campaignNotificationDTO.setNotificationMethodId(campaignNotification2.getNotificationMethod().getId());	
    	campaignNotificationDTO.setNotificationStatusId(campaignNotification2.getNotificationStatus().getId());
		
		mockMvc.perform(put("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL + "/99").with(csrf())
				.content(this.json(campaignNotificationDTO))
				.contentType(contentType))
		        .andExpect(status().isNotFound())
		        .andExpect(content().string("{\"message\":\"No campaign notification found for ID 99\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testDelete() throws Exception {
		
		mockMvc.perform(delete("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL + "/" + campaignNotification1.getId()).with(csrf())
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$").value(campaignNotification1.getId()));
	}
	
	@Test
	public void testDeleteWithInvalidId() throws Exception {
		mockMvc.perform(delete("/campaign/" + campaign.getId() + CONTROLLER_BASE_URL + "/" + 99).with(csrf())
			.contentType(contentType))
			.andExpect(status().isNotFound())
	        .andExpect(content().string("{\"message\":\"No campaign notification found for ID 99\",\"type\":\"ERROR\"}"));
	}
}
