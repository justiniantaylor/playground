package com.pizzeria.resource.controller.campaign.notification;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the campaign campaignNotification data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class CampaignNotificationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "error.campaign.notification.notificationDate.notnull")
    private LocalDate notificationDate;
	
	@NotNull(message = "error.campaign.notification.campaignId.notnull")
    private Long campaignId;
	
	@NotNull(message = "error.campaign.notification.customerId.notnull")
    private Long customerId;
	
	@NotNull(message = "error.campaign.notification.notificationMethodId.notnull")
    private Long notificationMethodId;
	
	@NotNull(message = "error.campaign.notification.notificationStatusId.notnull")
    private Long notificationStatusId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(LocalDate notificationDate) {
		this.notificationDate = notificationDate;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getNotificationMethodId() {
		return notificationMethodId;
	}

	public void setNotificationMethodId(Long notificationMethodId) {
		this.notificationMethodId = notificationMethodId;
	}

	public Long getNotificationStatusId() {
		return notificationStatusId;
	}

	public void setNotificationStatusId(Long notificationStatusId) {
		this.notificationStatusId = notificationStatusId;
	}
}
