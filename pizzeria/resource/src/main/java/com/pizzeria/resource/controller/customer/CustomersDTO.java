package com.pizzeria.resource.controller.customer;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of customers.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CustomersDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CustomerDTO> customers;

	@JsonCreator
	public CustomersDTO(@JsonProperty("customers") List<CustomerDTO> customers) {
		this.customers = customers;
	}

	public List<CustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}
}
