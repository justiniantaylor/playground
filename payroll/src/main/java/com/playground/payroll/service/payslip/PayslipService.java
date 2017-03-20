package com.playground.payroll.service.payslip;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponseDTO;

/**
 * Interface for the Payslip Service.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see PayslipServiceImpl
 * @since 1.0
 */
@Validated
public interface PayslipService {
	
	/**
	 * @see PayslipServiceImpl#getPaymentPeriods()
	 */
	public List<String> getPaymentPeriods();
	
	/**
	 * @see PayslipServiceImpl#calculate(List)
	 */
	public List<PayslipResponseDTO> calculate(@Valid List<PayslipRequestDTO> payslipRequests);
}
