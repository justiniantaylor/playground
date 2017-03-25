package com.playground.payroll.service.payslip;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playground.payroll.domain.IncomeTaxPeriod;
import com.playground.payroll.domain.IncomeTaxRate;
import com.playground.payroll.repository.IncomeTaxPeriodRepository;
import com.playground.payroll.service.payslip.dto.PayslipRequestDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponseDTO;
import com.playground.payroll.service.payslip.dto.PayslipResponsesDTO;
import com.playground.payroll.util.date.DateTimeUtils;
import com.playground.payroll.util.exception.MissingIncomeTaxPeriodException;

/**
 * Service used for the employee payslip calculations. This service will calculate
 * an employees gross income, income tax, net income and super amount.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see PayslipService
 * @since 1.0
 */
@Service("payslipService")
public class PayslipServiceImpl implements PayslipService {
	
	@Autowired
    private IncomeTaxPeriodRepository incomeTaxPeriodRepository;
	
	/**
	 * Calculates the gross income, income tax, net income and super amount for a list of employees.
	 * 
	 * @param payslipRequests   the list of employees and their remuneration used to calculate the payslip data
     * @return          		the list of calculated payslip data (gross income, income tax, net income and super amount) for the employees provided
     * @throws 					MissingIncomeTaxPeriodException
	 */
	public PayslipResponsesDTO calculatePayslips(List<PayslipRequestDTO> payslipRequests) {
		PayslipResponsesDTO payslipResponses = new PayslipResponsesDTO(new ArrayList<PayslipResponseDTO>());
		
		payslipRequests.forEach(payslipRequest-> payslipResponses
				.getPayslipResponses()
				.add(calculatePayslip(payslipRequest)));
		
		return payslipResponses;
	}
	
	/**
	 * Calculates the gross income, income tax, net income and super amount for an employee.
	 * 
	 * @param payslipRequest an individual payslip calculation request.
     * @return          	 the payslip calculation for an employee
	 */
	private PayslipResponseDTO calculatePayslip(PayslipRequestDTO payslipRequest) {	
		PayslipResponseDTO payslipResponse = new PayslipResponseDTO();
		
		BigDecimal annualSalary = new BigDecimal(payslipRequest.getAnnualSalary().longValue());	
		
		payslipResponse.setName(payslipRequest.getFirstName() + ' ' + payslipRequest.getLastName());
		payslipResponse.setPayPeriod(payslipRequest.getPaymentStartDate());
		payslipResponse.setGrossIncome(calculateGrossIncome(annualSalary));
		payslipResponse.setIncomeTax(calculateIncomeTax(extractPaymentStartDate(payslipRequest.getPaymentStartDate()), annualSalary));
		payslipResponse.setNetIncome(calculateNetIncome(new BigDecimal(payslipResponse.getGrossIncome().longValue()), new BigDecimal(payslipResponse.getIncomeTax().longValue())));
		payslipResponse.setSuperAmount(calculateSuperAmount(new BigDecimal(payslipResponse.getGrossIncome().longValue()), Double.parseDouble(payslipRequest.getSuperRate().replace("%", ""))));

		return payslipResponse;
	}
	
	/**
	 * Extracts the payment start date from the string provided in format <code>"01 March 2013 – 31 March 2013"<code>.
	 * 
	 * @param paymentStartDate  the payment start date (range)
     * @return          		the actual start date extracted from the date range string
     * @throws					DateTimeParseException
	 */
	private LocalDate extractPaymentStartDate(String paymentStartDate) {
		return LocalDate.parse( paymentStartDate.split(" - ")[0],  DateTimeUtils.FORMATTER);
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
		BigDecimal incomeTax = new BigDecimal(0);
			
		IncomeTaxPeriod incomeTaxPeriod = incomeTaxPeriodRepository
				.findIncomeTaxPeriod(startDate)
				.orElseThrow(() -> new MissingIncomeTaxPeriodException(startDate));
		
		Iterator<IncomeTaxRate> taxRatesWithAmount = incomeTaxPeriod.getIncomeTaxRates()
				.stream()
				.filter(incomeTaxRate -> incomeTaxRate.getTaxRateAmountInCents() != null && incomeTaxRate.getTaxRateAmountInCents().compareTo(BigDecimal.ZERO) != 0)
				.iterator();
		
		while(taxRatesWithAmount.hasNext()) {
			IncomeTaxRate incomeTaxRate = (IncomeTaxRate) taxRatesWithAmount.next(); 
			BigDecimal incomeStartAmount = new BigDecimal(incomeTaxRate.getIncomeStartAmountInCents().longValue() / 100);
			
			if (incomeTaxRate.getIncomeEndAmountInCents() != null) {
				BigDecimal incomeEndAmount = new BigDecimal(incomeTaxRate.getIncomeEndAmountInCents().longValue() / 100);
				
				if (annualSalary.compareTo(new BigDecimal(incomeTaxRate.getIncomeEndAmountInCents().longValue() / 100)) > 0) {;
					incomeTax = incomeTax.add(calculateIncomeTaxForRate(incomeEndAmount.subtract(incomeStartAmount.subtract(BigDecimal.ONE)), incomeTaxRate));	
				} else if (annualSalary.compareTo(incomeStartAmount) >= 0 && annualSalary.compareTo(incomeEndAmount) <= 0) {
					incomeTax = incomeTax.add(calculateIncomeTaxForRate(annualSalary.subtract(incomeStartAmount.subtract(BigDecimal.ONE)), incomeTaxRate));	
				}
			} else {
				if (annualSalary.compareTo(incomeStartAmount) > 0) {
					incomeTax = incomeTax.add(calculateIncomeTaxForRate(annualSalary.subtract(incomeStartAmount), incomeTaxRate));	
				}
			}
		}
		return incomeTax.divide(new BigDecimal("12"), 0, RoundingMode.HALF_UP);
	}
	
	/**
	 * Calculates the income tax for the employees taxable amount in the specified rate bracket.
	 * 
	 * @param taxableAmount the employees taxable amount at this specified rate.
	 * @param incomeTaxRate the income tax rate for this bracket
     * @return              the income tax to pay for this bracket
	 */
	private BigDecimal calculateIncomeTaxForRate(BigDecimal taxableAmount, IncomeTaxRate incomeTaxRate) {
		return taxableAmount.multiply(incomeTaxRate.getTaxRateAmountInCents()).divide( new BigDecimal("100"), 0, RoundingMode.HALF_UP);	
	}

	/**
	 * Calculates the net income tax for an employees. Formula: gross income (monthly) - income tax(monthly)
	 * 
	 * @param grossIncome the employee monthly gross income
	 * @param incomeTax   the employee monthly income tax
     * @return            the employee monthly net income amount
	 */
	private BigDecimal calculateNetIncome(BigDecimal grossIncome, BigDecimal incomeTax) {	
		return grossIncome.subtract(incomeTax);
	}
	
	/**
	 * Calculates the super annuation amount. Formula: gross income (monthly) x super rate
	 * 
	 * @param grossIncome the employee monthly gross income
	 * @param superRate   the super rate percentage (between 0% - 50%)
     * @return            the super annuation amount
	 */
	private BigDecimal calculateSuperAmount(BigDecimal grossIncome, Double superRate) {
		return grossIncome.multiply(new BigDecimal(superRate)).divide(new BigDecimal("100"), 0, RoundingMode.HALF_UP);
	}
}
