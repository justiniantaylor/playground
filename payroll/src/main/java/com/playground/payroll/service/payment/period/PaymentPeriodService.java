package com.playground.payroll.service.payment.period;

import org.springframework.validation.annotation.Validated;

import com.playground.payroll.service.payment.period.dto.PaymentPeriodsDTO;

/**
 * Interface for the Payment Period Service.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see PaymentPeriodServiceImpl
 * @since 1.0
 */
@Validated
public interface PaymentPeriodService {
	
	/**
	 * @see PaymentPeriodServiceImpl#findAll()
	 */
	public PaymentPeriodsDTO findAll();
}
