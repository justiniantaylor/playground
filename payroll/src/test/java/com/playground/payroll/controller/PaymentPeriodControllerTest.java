package com.playground.payroll.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.playground.payroll.util.RestControllerTest;

public class PaymentPeriodControllerTest extends RestControllerTest {

	private static final String CONTROLLER_BASE_URL = "/payment/period/";
	
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get(CONTROLLER_BASE_URL)
				.contentType(contentType))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[0]").value("01 July 2012 - 31 July 2012"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[1]").value("01 August 2012 - 31 August 2012"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[2]").value("01 September 2012 - 30 September 2012"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[3]").value("01 October 2012 - 31 October 2012"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[4]").value("01 November 2012 - 30 November 2012"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[5]").value("01 December 2012 - 31 December 2012"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[6]").value("01 January 2013 - 31 January 2013"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[7]").value("01 February 2013 - 28 February 2013"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[8]").value("01 March 2013 - 31 March 2013"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[9]").value("01 April 2013 - 30 April 2013"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[10]").value("01 May 2013 - 31 May 2013"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.paymentPeriods[11]").value("01 June 2013 - 30 June 2013"));
	}
}