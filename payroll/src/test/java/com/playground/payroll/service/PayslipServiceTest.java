package com.playground.payroll.service;

import static org.junit.Assert.*;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.playground.payroll.service.payslip.PayslipService;
import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponseDTO;
import com.playground.payroll.util.exception.MissingIncomeTaxPeriodException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayslipServiceTest {
	
	@Autowired
    private PayslipService payslipService;
	       
	@Test
	public void testCalculate() {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO davidRudd = new PayslipRequestDTO();
		davidRudd.setFirstName("David");
		davidRudd.setLastName("Rudd");
		davidRudd.setAnnualSalary(new Long(60050));
		davidRudd.setSuperRate("9%");
		davidRudd.setPaymentStartDate("01 March 2013 – 31 March 2013");	
		payslipRequests.add(davidRudd);
		
		PayslipRequestDTO ryanChen = new PayslipRequestDTO();
		ryanChen.setFirstName("Ryan");
		ryanChen.setLastName("Chen");
		ryanChen.setAnnualSalary(new Long(120000));
		ryanChen.setSuperRate("10%");
		ryanChen.setPaymentStartDate("01 March 2013 – 31 March 2013");	
		payslipRequests.add(ryanChen);
		
		List<PayslipResponseDTO> payslipResponses = payslipService.calculate(payslipRequests);
		
		assertNotNull(payslipResponses);
		assertEquals(2, payslipResponses.size());
		
		for(PayslipResponseDTO payslipResponse : payslipResponses) {

			if(payslipResponse.getName().equals(davidRudd.getFirstName() + ' ' + davidRudd.getLastName())) {
				assertEquals(davidRudd.getPaymentStartDate(), payslipResponse.getPayPeriod());
				assertEquals(5004, payslipResponse.getGrossIncome().longValue());
				assertEquals(922, payslipResponse.getIncomeTax().longValue());
				assertEquals(4082, payslipResponse.getNetIncome().longValue());
				assertEquals(450, payslipResponse.getSuperAmount().longValue());				
			} else if (payslipResponse.getName().equals(ryanChen.getFirstName() + ' ' + ryanChen.getLastName())) {
				assertEquals(ryanChen.getPaymentStartDate(), payslipResponse.getPayPeriod());
				assertEquals(10000, payslipResponse.getGrossIncome().longValue());
				assertEquals(2696, payslipResponse.getIncomeTax().longValue());
				assertEquals(7304, payslipResponse.getNetIncome().longValue());
				assertEquals(1000, payslipResponse.getSuperAmount().longValue());
			} else {
				fail("Unexpected payslip response for: " + payslipResponse.getName());
			}
		}
	}
	
	@Test
	public void testCalculateSalaryWithNoTaxRateAmount() {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO davidRudd = new PayslipRequestDTO();
		davidRudd.setFirstName("David");
		davidRudd.setLastName("Rudd");
		davidRudd.setAnnualSalary(new Long(18200));
		davidRudd.setSuperRate("9%");
		davidRudd.setPaymentStartDate("01 March 2013 – 31 March 2013");	
		payslipRequests.add(davidRudd);

		List<PayslipResponseDTO> payslipResponses = payslipService.calculate(payslipRequests);
		
		assertNotNull(payslipResponses);
		assertEquals(1, payslipResponses.size());
		
		assertEquals(davidRudd.getPaymentStartDate(), payslipResponses.get(0).getPayPeriod());
		assertEquals(1517, payslipResponses.get(0).getGrossIncome().longValue());
		assertEquals(0, payslipResponses.get(0).getIncomeTax().longValue());
		assertEquals(1517, payslipResponses.get(0).getNetIncome().longValue());
		assertEquals(137, payslipResponses.get(0).getSuperAmount().longValue());
	}
	
	@Test
	public void testCalculateSalaryWithHighestTaxRateAmount() {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO davidRudd = new PayslipRequestDTO();
		davidRudd.setFirstName("David");
		davidRudd.setLastName("Rudd");
		davidRudd.setAnnualSalary(new Long(190000));
		davidRudd.setSuperRate("9%");
		davidRudd.setPaymentStartDate("01 March 2013 – 31 March 2013");	
		payslipRequests.add(davidRudd);

		List<PayslipResponseDTO> payslipResponses = payslipService.calculate(payslipRequests);
		
		assertNotNull(payslipResponses);
		assertEquals(1, payslipResponses.size());
		
		assertEquals(davidRudd.getPaymentStartDate(), payslipResponses.get(0).getPayPeriod());
		assertEquals(15833, payslipResponses.get(0).getGrossIncome().longValue());
		assertEquals(4921, payslipResponses.get(0).getIncomeTax().longValue());
		assertEquals(10912, payslipResponses.get(0).getNetIncome().longValue());
		assertEquals(1425, payslipResponses.get(0).getSuperAmount().longValue());
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testCalculateMissingInputNotNullValues() {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO davidRudd = new PayslipRequestDTO();
		payslipRequests.add(davidRudd);
		
		payslipService.calculate(payslipRequests);
	}
	
	@Test(expected=MissingIncomeTaxPeriodException.class)
	public void testCalculateMissingTaxPeriod() {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO davidRudd = new PayslipRequestDTO();
		davidRudd.setFirstName("David");
		davidRudd.setLastName("Rudd");
		davidRudd.setAnnualSalary(new Long(60050));
		davidRudd.setSuperRate("9%");
		davidRudd.setPaymentStartDate("01 March 2017 – 31 March 2017");
		payslipRequests.add(davidRudd);
		
		payslipService.calculate(payslipRequests);
	}
	
	@Test(expected=DateTimeParseException.class)
	public void testCalculateInvalidPaymentStartDate() {
		List<PayslipRequestDTO> payslipRequests = new ArrayList<PayslipRequestDTO>();
		PayslipRequestDTO davidRudd = new PayslipRequestDTO();
		davidRudd.setFirstName("David");
		davidRudd.setLastName("Rudd");
		davidRudd.setAnnualSalary(new Long(60050));
		davidRudd.setSuperRate("9%");
		davidRudd.setPaymentStartDate("01 March – 31 March");
		payslipRequests.add(davidRudd);
		
		payslipService.calculate(payslipRequests);
	}
}

