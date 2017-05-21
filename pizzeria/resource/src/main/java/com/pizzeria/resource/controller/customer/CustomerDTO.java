package com.pizzeria.resource.controller.customer;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the customer data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
    @NotNull(message = "error.customer.firstName.notnull")
    private String firstName;
	@NotNull(message = "error.customer.lastName.notnull")
    private String lastName;
	@NotNull(message = "error.customer.preferredNotificationMethodId.notnull")
    private Long preferredNotificationMethodId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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

	public Long getPreferredNotificationMethodId() {
		return preferredNotificationMethodId;
	}

	public void setPreferredNotificationMethodId(Long preferredNotificationMethodId) {
		this.preferredNotificationMethodId = preferredNotificationMethodId;
	}
}
