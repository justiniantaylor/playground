package com.pizzeria.resource.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Domain Entity for an Address as per google address structure:
 * https://support.google.com/mapcontentpartners/answer/160409?hl=en
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class Address {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private String streetNumber;
	@Column(nullable = false)
    private String streetName;
	@Column(nullable = false)
    private String neighborhoodName;
	@Column(nullable = false)
    private String cityName;
	@Column(nullable = false)
    private String state;
	@Column(nullable = false)
    private String zipCode;

	@Column(nullable = false, precision=9, scale=6)
    private Double longitude;
	@Column(nullable = false, precision=9, scale=6)
    private Double latitude;    
	
	@ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getNeighborhoodName() {
		return neighborhoodName;
	}

	public void setNeighborhoodName(String neighborhoodName) {
		this.neighborhoodName = neighborhoodName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}