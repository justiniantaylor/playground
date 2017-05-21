package com.pizzeria.resource.controller.order;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the Order data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
    @NotNull(message = "error.order.deliver.notnull")
    private Boolean deliver;
    private Long cashierId;
    private Long customerId;
    private Long addressId;
    private LocalDate orderedDate;
    private LocalDate fulfilledDate;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getDeliver() {
		return deliver;
	}
	public void setDeliver(Boolean deliver) {
		this.deliver = deliver;
	}
	public Long getCashierId() {
		return cashierId;
	}
	public void setCashierId(Long cashierId) {
		this.cashierId = cashierId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public LocalDate getOrderedDate() {
		return orderedDate;
	}
	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}
	public LocalDate getFulfilledDate() {
		return fulfilledDate;
	}
	public void setFulfilledDate(LocalDate fulfilledDate) {
		this.fulfilledDate = fulfilledDate;
	}
}
