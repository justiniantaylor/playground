package com.playground.payroll.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OrderBy;

/**
 * JPA Entity for the income tax period / tax year. The tax period has various tax rates (brackets)
 * associated with it which are used to calculate an employees income tax.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class IncomeTaxPeriod {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private LocalDate startDate;
	@Column(nullable = false)
    private LocalDate endDate;
    
    @OneToMany(mappedBy = "incomeTaxPeriod", cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @OrderBy(clause = "income_start_amount_in_cents ASC")
    private List<IncomeTaxRate> incomeTaxRates;
    
    protected IncomeTaxPeriod() {}

    public IncomeTaxPeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Long getId() {
		return id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<IncomeTaxRate> getIncomeTaxRates() {
		return incomeTaxRates;
	}

	public void setIncomeTaxRates(List<IncomeTaxRate> incomeTaxRates) {
		this.incomeTaxRates = incomeTaxRates;
	}
}