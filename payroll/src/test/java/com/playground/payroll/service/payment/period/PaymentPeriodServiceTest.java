package com.playground.payroll.service.payment.period;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.playground.payroll.service.payment.period.dto.PaymentPeriodsDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentPeriodServiceTest {
	
	@Autowired
    private PaymentPeriodService paymentPeriodService;
	       
	@Test
	public void testGetPaymentPeriods() {
		PaymentPeriodsDTO paymentPeriods = paymentPeriodService.findAll();
		
		assertEquals(12, paymentPeriods.getPaymentPeriods().size());
		
		assertEquals("01 July 2012 - 31 July 2012", paymentPeriods.getPaymentPeriods().get(0));
		assertEquals("01 August 2012 - 31 August 2012", paymentPeriods.getPaymentPeriods().get(1));
		assertEquals("01 September 2012 - 30 September 2012", paymentPeriods.getPaymentPeriods().get(2));
		assertEquals("01 October 2012 - 31 October 2012", paymentPeriods.getPaymentPeriods().get(3));
		assertEquals("01 November 2012 - 30 November 2012", paymentPeriods.getPaymentPeriods().get(4));
		assertEquals("01 December 2012 - 31 December 2012", paymentPeriods.getPaymentPeriods().get(5));
		assertEquals("01 January 2013 - 31 January 2013", paymentPeriods.getPaymentPeriods().get(6));
		assertEquals("01 February 2013 - 28 February 2013", paymentPeriods.getPaymentPeriods().get(7));
		assertEquals("01 March 2013 - 31 March 2013", paymentPeriods.getPaymentPeriods().get(8));
		assertEquals("01 April 2013 - 30 April 2013", paymentPeriods.getPaymentPeriods().get(9));
		assertEquals("01 May 2013 - 31 May 2013", paymentPeriods.getPaymentPeriods().get(10));
		assertEquals("01 June 2013 - 30 June 2013", paymentPeriods.getPaymentPeriods().get(11));
	}
}

