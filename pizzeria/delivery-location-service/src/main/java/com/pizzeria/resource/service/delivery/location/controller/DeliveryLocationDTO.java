package com.pizzeria.resource.service.delivery.location.controller;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the delivery location coordinates.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class DeliveryLocationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "error.delivery.longitude.notnull")
    private Double longitude;
	@NotNull(message = "error.delivery.latitude.notnull")
    private Double latitude;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
}
