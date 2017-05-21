package com.pizzeria.resource.domain;

import java.time.LocalDate;
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
import javax.persistence.Table;

/**
 * Domain Entity for a customer order. An order can be placed online, over the telephone or in-store.
 * 
 * If the order is placed online or over the telephone the deliver flag will be set.
 * 
 * If this order is for delivery the customer must be set, and one of the customers addresses must be selected
 * or new one created for the delivery.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity(name = "order")
@Table(name = "order_") //this is due to order being a reserved word
public class Order {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private Boolean deliver;

	@Column(nullable = false)
    private LocalDate orderedDate;
	@Column(nullable = true)
    private LocalDate fulfilledDate;
	
	@ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee cashier;
	
	@ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;
	
	@ManyToOne
    @JoinColumn(name = "address_id", nullable = true)
    private Address address;
	
	@ManyToOne
    @JoinColumn(name = "delivery_id", nullable =true)
    private Delivery delivery;
	
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<OrderItem> orderItems;

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

	public Employee getCashier() {
		return cashier;
	}

	public void setCashier(Employee cashier) {
		this.cashier = cashier;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}