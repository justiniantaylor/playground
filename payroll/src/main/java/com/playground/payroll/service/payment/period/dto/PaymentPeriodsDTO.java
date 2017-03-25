package com.playground.payroll.service.payment.period.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of payment periods.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class PaymentPeriodsDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> paymentPeriods;

	@JsonCreator
	public PaymentPeriodsDTO(@JsonProperty("paymentPeriods") List<String> paymentPeriods) {
		this.paymentPeriods = paymentPeriods;
	}

	public List<String> getPaymentPeriods() {
		return paymentPeriods;
	}

	public void setPaymentPeriods(List<String> paymentPeriods) {
		this.paymentPeriods = paymentPeriods;
	}
}
