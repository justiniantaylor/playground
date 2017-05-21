package com.pizzeria.resource.controller.campaign;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the campaign data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CampaignDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "error.campaign.name.notnull")
    private String name;
	
	@NotNull(message = "error.campaign.text.notnull")
    private String text;

	@NotNull(message = "error.campaign.startDate.notnull")
    private LocalDate startDate;
	
	@NotNull(message = "error.campaign.endDate.notnull")
    private LocalDate endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
