package com.pizzeria.resource.controller.customer.address;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the address data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "error.address.streetNumber.notnull")
    private String streetNumber;
	@NotNull(message = "error.address.streetName.notnull")
    private String streetName;
	@NotNull(message = "error.address.neighborhoodName.notnull")
    private String neighborhoodName;
	@NotNull(message = "error.address.cityName.notnull")
    private String cityName;
	@NotNull(message = "error.address.state.notnull")
    private String state;
	@NotNull(message = "error.address.zipCode.notnull")
    private String zipCode;

	@NotNull(message = "error.address.longitude.notnull")
    private Double longitude;
	@NotNull(message = "error.address.latitude.notnull")
    private Double latitude;
	
	@NotNull(message = "error.address.customerID.notnull")
    private Long customerId;
	
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
}
