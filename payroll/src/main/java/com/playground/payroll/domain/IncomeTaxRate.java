package com.playground.payroll.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * JPA Entity for the income tax rates (brackets). These are used to calculate an employees
 * income tax based on their annual salary. Each tax rate is for a particular amount of the
 * annual salary.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class IncomeTaxRate {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private Long incomeStartAmountInCents;
    @Column(nullable = true)
    private Long incomeEndAmountInCents;
    
    @Column(nullable = true)
    private BigDecimal taxRateAmountInCents;
    
    @ManyToOne
    @JoinColumn(name = "income_tax_period_id")
    private IncomeTaxPeriod incomeTaxPeriod;

    protected IncomeTaxRate() {}

    public IncomeTaxRate(IncomeTaxPeriod incomeTaxPeriod, Long incomeStartAmountInCents, Long incomeEndAmountInCents, BigDecimal taxRateAmountInCents) {
    	this.incomeTaxPeriod = incomeTaxPeriod;
        this.incomeStartAmountInCents = incomeStartAmountInCents;
        this.incomeEndAmountInCents = incomeEndAmountInCents;
        this.taxRateAmountInCents = taxRateAmountInCents;
    }

	public Long getId() {
		return id;
	}

	public Long getIncomeStartAmountInCents() {
		return incomeStartAmountInCents;
	}

	public void setIncomeStartAmountInCents(Long incomeStartAmountInCents) {
		this.incomeStartAmountInCents = incomeStartAmountInCents;
	}

	public Long getIncomeEndAmountInCents() {
		return incomeEndAmountInCents;
	}

	public void setIncomeEndAmountInCents(Long incomeEndAmountInCents) {
		this.incomeEndAmountInCents = incomeEndAmountInCents;
	}

	public BigDecimal getTaxRateAmountInCents() {
		return taxRateAmountInCents;
	}

	public void setTaxRateAmountInCents(BigDecimal taxRateAmountInCents) {
		this.taxRateAmountInCents = taxRateAmountInCents;
	}

	public IncomeTaxPeriod getIncomeTaxPeriod() {
		return incomeTaxPeriod;
	}

	public void setIncomeTaxPeriod(IncomeTaxPeriod incomeTaxPeriod) {
		this.incomeTaxPeriod = incomeTaxPeriod;
	}
}