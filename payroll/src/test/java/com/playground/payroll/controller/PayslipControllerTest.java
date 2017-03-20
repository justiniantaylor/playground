package com.playground.payroll.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.util.RestControllerTest;

public class PayslipControllerTest extends RestControllerTest {

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

		mockMvc.perform(post("/payslip/calculate")
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isOk());
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

		mockMvc.perform(post("/payslip/calculate")
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

		mockMvc.perform(post("/payslip/calculate")
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
		
		mockMvc.perform(post("/payslip/calculate")
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
		
		mockMvc.perform(post("/payslip/calculate")
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
		
		mockMvc.perform(post("/payslip/calculate")
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
		
		mockMvc.perform(post("/payslip/calculate")
				.content(this.json(payslipRequests))
				.contentType(contentType))
		        .andExpect(status().isBadRequest())
		        .andExpect(content().string("{\"message\":\"Payment start date is a required field\",\"type\":\"ERROR\"}"));
	}
}
