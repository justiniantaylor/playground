package com.playground.payroll.service.payslip.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO containing the payslip data for an employee.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class PayslipResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String payPeriod;
	private Long grossIncome;
	private Long incomeTax;
	private Long netIncome;
	private Long superAmount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	
	public Long getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(Long grossIncome) {
		this.grossIncome = grossIncome;
	}
	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = new Long(grossIncome.longValueExact());
	}
	
	public Long getIncomeTax() {
		return incomeTax;
	}
	public void setIncomeTax(Long incomeTax) {
		this.incomeTax = incomeTax;
	}
	public void setIncomeTax(BigDecimal incomeTax) {
		this.incomeTax = new Long(incomeTax.longValueExact());
	}
	
	public Long getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(Long netIncome) {
		this.netIncome = netIncome;
	}
	public void setNetIncome(BigDecimal netIncome) {
		this.netIncome = new Long(netIncome.longValueExact());
	}
	
	public Long getSuperAmount() {
		return superAmount;
	}
	public void setSuperAmount(Long superAmount) {
		this.superAmount = superAmount;
	}
	public void setSuperAmount(BigDecimal superAmount) {
		this.superAmount = new Long(superAmount.longValueExact());
	}
	
	@Override
	public String toString() {
		return "PayslipResponseDTO [name=" + name + ", payPeriod=" + payPeriod + ", grossIncome=" + grossIncome
				+ ", incomeTax=" + incomeTax + ", netIncome=" + netIncome + ", superAmount=" + superAmount + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PayslipResponseDTO other = (PayslipResponseDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
