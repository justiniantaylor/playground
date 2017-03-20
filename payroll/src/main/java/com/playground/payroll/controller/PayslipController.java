package com.playground.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.playground.payroll.service.payslip.PayslipService;
import com.playground.payroll.service.payslip.PayslipServiceImpl;
import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponseDTO;

/**
 * Rest service for employee payslip calculations for a payroll.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/payslip")
public class PayslipController {

	@Autowired
    private PayslipService payslipService;
    
	 /**
     * Retrieves the payment periods available for employee payslip calculations.
     *
     * @return	the list of available payment periods
     * @see     PayslipServiceImpl#getPaymentPeriods
     */
    @RequestMapping(value = "/payment-period", method = RequestMethod.GET, produces = "application/json")
    public List<String> getPaymentPeriods() {
    	return payslipService.getPaymentPeriods();
    }
    
	/**
     * Calculates the payslip data for a list of employees on a payroll.
     *
     * @param payslipRequests   the list of employees and their remuneration to calculate payslip information for
     * @return          		the list of calculated payslip data for the employees provided
     * @see 					PayslipRequestDTO
     * @see						PayslipResponseDTO
     * @see     				PayslipServiceImpl#calculate
     */
    @RequestMapping(value = "/calculate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<PayslipResponseDTO> calculate(@Validated @RequestBody List<PayslipRequestDTO> payslipRequests) {
        return payslipService.calculate(payslipRequests);
    }
}