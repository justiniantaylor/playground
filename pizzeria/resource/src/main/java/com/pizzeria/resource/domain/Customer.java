package com.pizzeria.resource.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OrderBy;

/**
 * Domain Entity for the Customer. 
 * 
 * A Customer may have multiple addresses, each time an order is placed it will either create a new
 * address or use an existing address from a previous Order.
 * 
 * A Customer may have a preferred notification method for campaigns.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class Customer {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private String firstName;
	@Column(nullable = false)
    private String lastName;
	
	@ManyToOne
    @JoinColumn(name = "notification_method_id", nullable = false)
    private NotificationMethod preferredNotificationMethod;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Address> addresses;
	
    @OneToMany(mappedBy = "customer")
    @OrderBy(clause = "order_date ASC")
    private List<Order> orders;
    
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

	public NotificationMethod getPreferredNotificationMethod() {
		return preferredNotificationMethod;
	}

	public void setPreferredNotificationMethod(NotificationMethod preferredNotificationMethod) {
		this.preferredNotificationMethod = preferredNotificationMethod;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}