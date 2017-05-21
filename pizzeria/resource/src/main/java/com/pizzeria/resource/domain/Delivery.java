package com.pizzeria.resource.domain;

import java.time.LocalDate;
import java.util.List;

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
 * Domain Entity for the menu. You may 
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class Delivery {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private LocalDate startDate;
	@Column(nullable = false)
    private LocalDate endDate;
	
	@Column(nullable = false, precision=9, scale=6)
    private Double currentLongitude;
	@Column(nullable = false, precision=9, scale=6)
    private Double currentLatitude;
	
	@ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Employee deliveryPerson;
	
	@OneToMany(mappedBy = "delivery")
    @OrderBy(clause = "order_date ASC")
    private List<Order> orders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(Double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public Double getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(Double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

	public Employee getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(Employee deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}