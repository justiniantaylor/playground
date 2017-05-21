package com.pizzeria.resource.controller.menu;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the menu data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class MenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "error.menu.startDate.notnull")
    private LocalDate startDate;
    private LocalDate endDate;
    
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
}
