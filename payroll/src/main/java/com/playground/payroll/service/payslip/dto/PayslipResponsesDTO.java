package com.playground.payroll.service.payslip.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing the list of calculated payslips.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class PayslipResponsesDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<PayslipResponseDTO> payslipResponses;

	@JsonCreator
	public PayslipResponsesDTO(@JsonProperty("payslipResponses") List<PayslipResponseDTO> payslipResponses) {
		this.payslipResponses = payslipResponses;
		
	}
	public List<PayslipResponseDTO> getPayslipResponses() {
		return payslipResponses;
	}

	public void setPayslipResponses(List<PayslipResponseDTO> payslipResponses) {
		this.payslipResponses = payslipResponses;
	}
}
