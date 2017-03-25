package com.playground.payroll.service.payslip.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the employee details including their remuneration used to calculate the payslip data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class PayslipRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "error.payslip.firstname.notnull")
	private String firstName;
	
	@NotNull(message = "error.payslip.lastname.notnull")
	private String lastName;
	
	@NotNull(message = "error.payslip.annualsalary.notnull")
	private Long annualSalary;
	
	@NotNull(message = "error.payslip.superrate.notnull")
	private String superRate;
	
	@NotNull(message = "error.payslip.paymentstartdate.notnull")
	private String paymentStartDate;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Long getAnnualSalary() {
		return annualSalary;
	}
	public void setAnnualSalary(Long annualSalary) {
		this.annualSalary = annualSalary;
	}
	
	public String getSuperRate() {
		return superRate;
	}
	public void setSuperRate(String superRate) {
		this.superRate = superRate;
	}
	
	public String getPaymentStartDate() {
		return paymentStartDate;
	}
	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}
	
	@Override
	public String toString() {
		return "PayslipRequestDTO [firstName=" + firstName + ", lastName=" + lastName + ", annualSalary=" + annualSalary
				+ ", superRate=" + superRate + ", paymentStartDate=" + paymentStartDate + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
		PayslipRequestDTO other = (PayslipRequestDTO) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
}
