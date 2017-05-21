package com.pizzeria.resource.controller.campaign.notification;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of campaign notifications.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CampaignNotificationsDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CampaignNotificationDTO> campaignNotifications;

	@JsonCreator
	public CampaignNotificationsDTO(@JsonProperty("campaignNotifications") List<CampaignNotificationDTO> campaignNotifications) {
		this.campaignNotifications = campaignNotifications;
	}

	public List<CampaignNotificationDTO> getCampaignNotifications() {
		return campaignNotifications;
	}

	public void setCampaignNotifications(List<CampaignNotificationDTO> campaignNotifications) {
		this.campaignNotifications = campaignNotifications;
	}
}
