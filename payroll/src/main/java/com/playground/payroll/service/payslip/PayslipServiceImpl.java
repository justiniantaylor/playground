package com.playground.payroll.service.payslip;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playground.payroll.domain.IncomeTaxPeriod;
import com.playground.payroll.domain.IncomeTaxRate;
import com.playground.payroll.repository.IncomeTaxPeriodRepository;
import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponseDTO;
import com.playground.payroll.util.date.DateTimeUtils;
import com.playground.payroll.util.exception.MissingIncomeTaxPeriodException;

/**
 * Service used for the employee payslip calculations for a payroll. This service will calculate
 * and employees gross income, income tax, net income and super amount.
 * <p>
 * We have encapsulted this business logic into a service component so that we may re-use
 * this logic for multiple interfaces (Rest, SOAP etc), batch processes or events.
 * <p>
 * We have chosen to run our bean validation at this service level instead of at the controller level  
 * so that every mechanism using this calculation service would benefit from this input data validation.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see PayslipService
 * @since 1.0
 */
@Service("payslipService")
public class PayslipServiceImpl implements PayslipService {
	
	private static final Logger log = LoggerFactory.getLogger(PayslipServiceImpl.class);
	
	@Autowired
    private IncomeTaxPeriodRepository incomeTaxPeriodRepository;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM uuuu");
	
	/**
	 * Gets the available payment periods for all the tax periods we have tax rates for.
	 * 
     * @return the list of available payment periods
	 */
	public List<String> getPaymentPeriods() {
    	List<String> paymentPeriods = new ArrayList<String>();

    	for( IncomeTaxPeriod incomeTaxPeriod : incomeTaxPeriodRepository.findAllByOrderByStartDate()) {
    		
    		LocalDate paymentPeriod = incomeTaxPeriod.getStartDate().withDayOfMonth(1);
    		
    		log.info("ADDING: " + DateTimeUtils.formateDateRange(paymentPeriod, paymentPeriod.withDayOfMonth(paymentPeriod.lengthOfMonth()), formatter, "-"));
    		paymentPeriods.add(DateTimeUtils.formateDateRange(paymentPeriod, paymentPeriod.withDayOfMonth(paymentPeriod.lengthOfMonth()), formatter, "-"));

    		for(int i = 0; i < 11; i++) {
    			paymentPeriod = paymentPeriod.plusMonths(1).withDayOfMonth(1);
    			log.info("ADDING: " +DateTimeUtils.formateDateRange(paymentPeriod, paymentPeriod.withDayOfMonth(paymentPeriod.lengthOfMonth()), formatter, "-"));
    			paymentPeriods.add(DateTimeUtils.formateDateRange(paymentPeriod, paymentPeriod.withDayOfMonth(paymentPeriod.lengthOfMonth()), formatter, "-"));
    		}    	
    	}
    	
        return paymentPeriods;
	}
	
	/**
	 * Calculates the gross income, income tax, net income and super amount for a list of employees.
	 * 
	 * @param payslipRequests   the list of employees and their remuneration used to calculate the payslip data
     * @return          		the list of calculated payslip data (gross income, income tax, net income and super amount) for the employees provided
     * @throws 					MissingIncomeTaxPeriodException
     * @see 					PayslipRequestDTO
     * @see						PayslipResponseDTO
	 */
	public List<PayslipResponseDTO> calculate(List<PayslipRequestDTO> payslipRequests) {
		List<PayslipResponseDTO> payslipResponses = new ArrayList<PayslipResponseDTO>();
		
		for (PayslipRequestDTO payslipRequest: payslipRequests ) {
			log.info("Payslip calculation requested: " + payslipRequest.toString());
			
			//for calculations with monetary values we use BigDecimal for precision on fractions
			BigDecimal annualSalary = new BigDecimal(payslipRequest.getAnnualSalary().longValue());
			
			PayslipResponseDTO payslipResponse = new PayslipResponseDTO();
			
			//copy values from request object
			payslipResponse.setName(payslipRequest.getFirstName() + ' ' + payslipRequest.getLastName());
			payslipResponse.setPayPeriod(payslipRequest.getPaymentStartDate());
			
			//extract the relevant dates for tax calculation purposes
			LocalDate startDate = extractPaymentStartDate(payslipRequest.getPaymentStartDate());
		
			//calculate gross income
			payslipResponse.setGrossIncome(calculateGrossIncome(annualSalary));

			//calculate income tax
			payslipResponse.setIncomeTax(calculateIncomeTax(startDate, annualSalary));
			
			//calculate net income;
			payslipResponse.setNetIncome(calculateNetIncome(new BigDecimal(payslipResponse.getGrossIncome().longValue()), new BigDecimal(payslipResponse.getIncomeTax().longValue())));
			
			//extract super rate from string input and then calculate super amount
			Double superRate = Double.parseDouble(payslipRequest.getSuperRate().replace("%", ""));
			payslipResponse.setSuperAmount(calculateSuperAmount(new BigDecimal(payslipResponse.getGrossIncome().longValue()), superRate));
			
			//add to the response list
			payslipResponses.add(payslipResponse);
			
			log.info("Successfully calculated payslip: " + payslipResponse.toString());
		}
		
		return payslipResponses;
	}
	
	/**
	 * Extracts the payment start date from the string provided in format <code>"01 March 2013 – 31 March 2013"<code>.
	 * 
	 * @param paymentStartDate  the payment start date (range)
     * @return          		the actual start date extracted from the date range string
     * @throws					DateTimeParseException
     * @see 					LocalDate
	 */
	private LocalDate extractPaymentStartDate(String paymentStartDate) {
		String[] paymentStartDates = paymentStartDate.split(" - ");
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM uuuu");
		
		return LocalDate.parse(paymentStartDates[0], formatter);
	}
	
	/**
	 * Calculates the monthly gross income from the annual salary.
	 * Formula: annual salary / 12 (months in year)
	 * 
	 * @param annualSalary  the employee annual salary
     * @return          	the monthly gross income for an employee
	 */
	private BigDecimal calculateGrossIncome(BigDecimal annualSalary) {
		return annualSalary.divide(new BigDecimal("12"), 0, RoundingMode.HALF_UP);
	}
	
	/**
	 * Calculates the income tax for an employees salary for a tax period.
	 * 
	 * @param startDate 	the start date of the payment period / payroll used to get the tax rates
	 * @param annualSalary  the employees annual salary
     * @return          	the monthly income income for an employee
	 */
	private BigDecimal calculateIncomeTax(LocalDate startDate, BigDecimal annualSalary) {
		//use BigDecimal for precision and rounding mode
		BigDecimal incomeTax = new BigDecimal(0);
			
		//retrieve the relevant tax period / tax year
		IncomeTaxPeriod incomeTaxPeriod = incomeTaxPeriodRepository
				.findIncomeTaxPeriod(startDate)
				.orElseThrow(() -> new MissingIncomeTaxPeriodException(startDate));
		
		//getIncomeRates is return by JPA ordered by start amount in cents ascending with nulls first
		for (IncomeTaxRate incomeTaxRate: incomeTaxPeriod.getIncomeTaxRates() ) {
			
			if(incomeTaxRate.getTaxRateAmountInCents() != null && incomeTaxRate.getTaxRateAmountInCents().compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal incomeStartAmount = new BigDecimal(incomeTaxRate.getIncomeStartAmountInCents().longValue() / 100);
				
				if (incomeTaxRate.getIncomeEndAmountInCents() != null) {
					BigDecimal incomeEndAmount = new BigDecimal(incomeTaxRate.getIncomeEndAmountInCents().longValue() / 100);
					
					if (annualSalary.compareTo(incomeEndAmount) > 0) {;
						BigDecimal amountInBracket = incomeEndAmount.subtract(incomeStartAmount.subtract(BigDecimal.ONE));
						incomeTax = incomeTax.add(amountInBracket.multiply(incomeTaxRate.getTaxRateAmountInCents()).divide( new BigDecimal("100"), 0, RoundingMode.HALF_UP));	
					} else if (annualSalary.compareTo(incomeStartAmount) >= 0 && annualSalary.compareTo(incomeEndAmount) <= 0) {
						BigDecimal taxableIncomeForThisRate = annualSalary.subtract(incomeStartAmount.subtract(BigDecimal.ONE));	
						incomeTax = incomeTax.add(taxableIncomeForThisRate.multiply(incomeTaxRate.getTaxRateAmountInCents()).divide( new BigDecimal("100"), 0, RoundingMode.HALF_UP));	
					}
				} else {
					if (annualSalary.compareTo(incomeStartAmount) > 0) {
						BigDecimal taxableIncomeForThisRate = annualSalary.subtract(incomeStartAmount);
						incomeTax = incomeTax.add(taxableIncomeForThisRate.multiply(incomeTaxRate.getTaxRateAmountInCents()).divide( new BigDecimal("100"), 0, RoundingMode.HALF_UP));	
					}
				}
			}
		}
		//divide the annual tax by 12 to get the monthly tax amount
		return incomeTax.divide(new BigDecimal("12"), 0, RoundingMode.HALF_UP);
	}

	/**
	 * Calculates the net income tax for an employees.
	 * Formula: gross income (monthly) - income tax(monthly)
	 * 
	 * @param grossIncome the employee monthly gross income
	 * @param incomeTax   the employee monthly income tax
     * @return            the employee monthly net income amount
	 */
	private BigDecimal calculateNetIncome(BigDecimal grossIncome, BigDecimal incomeTax) {
		BigDecimal netIncome = grossIncome.subtract(incomeTax);
				
		return netIncome;
	}
	
	/**
	 * Calculates the super annuation amount.
	 * Formula: gross income (monthly) x super rate
	 * 
	 * @param grossIncome the employee monthly gross income
	 * @param superRate   the super rate percentage (between 0% - 50%)
     * @return            the super annuation amount
	 */
	private BigDecimal calculateSuperAmount(BigDecimal grossIncome, Double superRate) {
		BigDecimal superAmount = grossIncome.multiply(new BigDecimal(superRate));
		
		return superAmount.divide(new BigDecimal("100"), 0, RoundingMode.HALF_UP);
	}
}
