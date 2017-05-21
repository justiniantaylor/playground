package com.pizzeria.resource.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Domain Entity for a Campaign Notification to a Customer.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class CampaignNotification {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private LocalDate notificationDate;
	
	@ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;
	
	@ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
	
	@ManyToOne
    @JoinColumn(name = "notification_method_id", nullable = false)
    private NotificationMethod notificationMethod;
	
	@ManyToOne
    @JoinColumn(name = "notification_status_id", nullable = false)
    private NotificationStatus notificationStatus;

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

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public NotificationMethod getNotificationMethod() {
		return notificationMethod;
	}

	public void setNotificationMethod(NotificationMethod notificationMethod) {
		this.notificationMethod = notificationMethod;
	}

	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
}