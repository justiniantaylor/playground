package com.pizzeria.resource.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Domain Entity for a Campaign to notify Customers of specials.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class Campaign {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false)
    private String text;
	
	@Column(nullable = false)
    private LocalDate startDate;
	
	@Column(nullable = false)
    private LocalDate endDate;
	
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<CampaignNotification> campaignNotifications;

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

	public List<CampaignNotification> getCampaignNotifications() {
		return campaignNotifications;
	}

	public void setCampaignNotifications(List<CampaignNotification> campaignNotifications) {
		this.campaignNotifications = campaignNotifications;
	}
}