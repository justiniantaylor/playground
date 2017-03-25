package com.playground.payroll.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.util.RestControllerTest;

public class PayslipCalculateControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/payslip/calculate/";
	
	@Test
	public void testCalculate() throws Exception {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO payslipRequest = new PayslipRequestDTO();
		payslipRequest.setFirstName("David");
		payslipRequest.setLastName("Rudd");
		payslipRequest.setAnnualSalary(new Long(60050));
		payslipRequest.setSuperRate("9%");
		payslipRequest.setPaymentStartDate("01 March 2013 - 31 March 2013");
		payslipRequests.add(payslipRequest);
		
		PayslipRequestDTO ryanChen = new PayslipRequestDTO();
		ryanChen.setFirstName("Ryan");
		ryanChen.setLastName("Chen");
		ryanChen.setAnnualSalary(new Long(120000));
		ryanChen.setSuperRate("10%");
		ryanChen.setPaymentStartDate("01 March 2013 - 31 March 2013");	
		payslipRequests.add(ryanChen);

		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[0].name").value("David Rudd"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[0].payPeriod").value("01 March 2013 - 31 March 2013"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[0].grossIncome").value(5004))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[0].incomeTax").value(922))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[0].netIncome").value(4082))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[0].superAmount").value(450))
		        
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[1].name").value("Ryan Chen"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[1].payPeriod").value("01 March 2013 - 31 March 2013"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[1].grossIncome").value(10000))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[1].incomeTax").value(2696))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[1].netIncome").value(7304))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.payslipResponses[1].superAmount").value(1000));
	}

	@Test
	public void testCalculateMissingTaxPeriod() throws Exception {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO payslipRequest = new PayslipRequestDTO();
		payslipRequest.setFirstName("David");
		payslipRequest.setLastName("Rudd");
		payslipRequest.setAnnualSalary(new Long(60050));
		payslipRequest.setSuperRate("9%");
		payslipRequest.setPaymentStartDate("01 March 2017 - 31 March 2017");
		payslipRequests.add(payslipRequest);

		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(payslipRequests))
				.contentType(contentType)).andExpect(status().isNotFound())
				.andExpect(status().reason(containsString("Missing income tax period")));
	}
	
	@Test
	public void testCalculateMissingFirstName() throws Exception {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO payslipRequest = new PayslipRequestDTO();
		payslipRequest.setLastName("Rudd");
		payslipRequest.setAnnualSalary(new Long(60050));
		payslipRequest.setSuperRate("9%");
		payslipRequest.setPaymentStartDate("01 March 2013 - 31 March 2013");
		payslipRequests.add(payslipRequest);

		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isBadRequest())
		        .andExpect(content().string("{\"message\":\"First name is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCalculateMissingLastName() throws Exception {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO payslipRequest = new PayslipRequestDTO();
		payslipRequest.setFirstName("David");
		payslipRequest.setAnnualSalary(new Long(60050));
		payslipRequest.setSuperRate("9%");
		payslipRequest.setPaymentStartDate("01 March 2013 - 31 March 2013");
		payslipRequests.add(payslipRequest);
		
		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isBadRequest())
		        .andExpect(content().string("{\"message\":\"Last name is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCalculateMissingAnnualSalary() throws Exception {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		
		PayslipRequestDTO payslipRequest = new PayslipRequestDTO();
		payslipRequest.setFirstName("David");
		payslipRequest.setLastName("Rudd");
		payslipRequest.setSuperRate("9%");
		payslipRequest.setPaymentStartDate("01 March 2013 - 31 March 2013");
		payslipRequests.add(payslipRequest);
		
		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isBadRequest())
		        .andExpect(content().string("{\"message\":\"Annual salary is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCalculateMissingSuperRate() throws Exception {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		
		PayslipRequestDTO payslipRequest = new PayslipRequestDTO();
		payslipRequest.setFirstName("David");
		payslipRequest.setLastName("Rudd");
		payslipRequest.setAnnualSalary(new Long(60050));
		payslipRequest.setPaymentStartDate("01 March 2013 - 31 March 2013");
		payslipRequests.add(payslipRequest);
		
		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isBadRequest())
		        .andExpect(content().string("{\"message\":\"Super rate is a required field\",\"type\":\"ERROR\"}"));
	}
	
	@Test
	public void testCalculateMissingPaymentStartDate() throws Exception {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();

		PayslipRequestDTO payslipRequest = new PayslipRequestDTO();
		payslipRequest.setFirstName("David");
		payslipRequest.setLastName("Rudd");
		payslipRequest.setAnnualSalary(new Long(60050));
		payslipRequest.setSuperRate("9%");
		payslipRequests.add(payslipRequest);
		
		mockMvc.perform(post(CONTROLLER_BASE_URL)
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isBadRequest())
		        .andExpect(content().string("{\"message\":\"Payment start date is a required field\",\"type\":\"ERROR\"}"));
	}
}
