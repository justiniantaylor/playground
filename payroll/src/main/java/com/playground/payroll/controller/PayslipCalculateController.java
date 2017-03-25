package com.playground.payroll.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.playground.payroll.service.payslip.PayslipService;
import com.playground.payroll.service.payslip.PayslipServiceImpl;
import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponsesDTO;

/**
 * Rest service for employee payslip calculations.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/payslip/calculate")
public class PayslipCalculateController {

	@Autowired
    private PayslipService payslipService;

	/**
     * @see PayslipServiceImpl#calculatePayslips
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public HttpEntity<PayslipResponsesDTO> calculate(@Validated @RequestBody List<PayslipRequestDTO> payslipRequests) {
    	
    	PayslipResponsesDTO payslipResponses = payslipService.calculatePayslips(payslipRequests);
    	payslipResponses.add(linkTo(methodOn(PayslipCalculateController.class).calculate(payslipRequests)).withSelfRel());
    	
    	return new ResponseEntity<PayslipResponsesDTO>(payslipResponses, HttpStatus.OK);
    }
}