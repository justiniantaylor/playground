package com.playground.payroll.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.playground.payroll.service.payment.period.PaymentPeriodService;
import com.playground.payroll.service.payment.period.dto.PaymentPeriodsDTO;

/**
 * Rest service for the tax payment periods.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/payment/period")
public class PaymentPeriodController {

	@Autowired
    private PaymentPeriodService paymentPeriodService;
    
	 /**
     * @see PaymentPeriodServiceImpls#findAll
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public HttpEntity<PaymentPeriodsDTO> findAll() {
  
    	PaymentPeriodsDTO paymentPeriods = paymentPeriodService.findAll();
    	paymentPeriods.add(linkTo(methodOn(PaymentPeriodController.class).findAll()).withSelfRel());
    	
    	return new ResponseEntity<PaymentPeriodsDTO>(paymentPeriods, HttpStatus.OK);
    }
}