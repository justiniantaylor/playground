package com.pizzeria.resource.controller.campaign;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of campaigns.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CampaignsDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CampaignDTO> campaigns;

	@JsonCreator
	public CampaignsDTO(@JsonProperty("campaigns") List<CampaignDTO> campaigns) {
		this.campaigns = campaigns;
	}

	public List<CampaignDTO> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<CampaignDTO> campaigns) {
		this.campaigns = campaigns;
	}
}
