package com.playground.payroll.service.payslip;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponsesDTO;

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
	 * @see PayslipServiceImpl#calculate(List)
	 */
	public PayslipResponsesDTO calculatePayslips(@Valid List<PayslipRequestDTO> payslipRequests);
}
