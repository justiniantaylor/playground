package com.pizzeria.resource.controller.customer.address;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of addresses.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class AddressesDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<AddressDTO> addresses;

	@JsonCreator
	public AddressesDTO(@JsonProperty("addresss") List<AddressDTO> addresses) {
		this.addresses = addresses;
	}

	public List<AddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDTO> addresses) {
		this.addresses = addresses;
	}
}
