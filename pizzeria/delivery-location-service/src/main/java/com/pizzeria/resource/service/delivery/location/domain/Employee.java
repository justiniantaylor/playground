package com.pizzeria.resource.service.delivery.location.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OrderBy;

/**
 * Domain Entity for an employee. This is a person who may be a cashier taking an order, the person doing deliveries or the manager.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class Employee {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

	@Column(nullable = false)
    private String firstName;
	@Column(nullable = false)
    private String lastName;
	@Column(nullable = false)
	private String userName;

    @OneToMany(mappedBy = "deliveryPerson")
    @OrderBy(clause = "start_date DESC")
    private List<Delivery> deliveries;

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

	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}