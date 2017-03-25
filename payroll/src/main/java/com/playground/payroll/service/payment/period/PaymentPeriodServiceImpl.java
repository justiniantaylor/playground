package com.playground.payroll.service.payment.period;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playground.payroll.repository.IncomeTaxPeriodRepository;
import com.playground.payroll.service.payment.period.dto.PaymentPeriodsDTO;
import com.playground.payroll.service.payslip.PayslipService;
import com.playground.payroll.util.date.DateTimeUtils;

/**
 * Service for payment periods.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see PayslipService
 * @since 1.0
 */
@Service("paymentPeriodService")
public class PaymentPeriodServiceImpl implements PaymentPeriodService {
	
	@Autowired
    private IncomeTaxPeriodRepository incomeTaxPeriodRepository;

	/**
	 * Find all the available payment periods for existing tax periods.
	 * 
     * @return the list of available payment periods
	 */
	public PaymentPeriodsDTO findAll() {
		PaymentPeriodsDTO paymentPeriods = new PaymentPeriodsDTO(new ArrayList<String>());

		incomeTaxPeriodRepository.findAllByOrderByStartDate().forEach(incomeTaxPeriod -> {
			LocalDate paymentPeriod = incomeTaxPeriod.getStartDate().withDayOfMonth(1);
    		
    		paymentPeriods.getPaymentPeriods().add(DateTimeUtils.formateDateRange(paymentPeriod, paymentPeriod.withDayOfMonth(paymentPeriod.lengthOfMonth()), DateTimeUtils.FORMATTER, "-"));

    		for(int i = 0; i < 11; i++) {
    			paymentPeriod = paymentPeriod.plusMonths(1).withDayOfMonth(1);    		
    			paymentPeriods.getPaymentPeriods().add(DateTimeUtils.formateDateRange(paymentPeriod, paymentPeriod.withDayOfMonth(paymentPeriod.lengthOfMonth()), DateTimeUtils.FORMATTER, "-"));
    		}    
		});
		    	
        return paymentPeriods;
	}
}
