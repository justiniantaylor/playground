package com.pizzeria.resource.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.CampaignNotification;

/**
 * JPA Repository for the Campaign Notification domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see CampaignNotification
 */
public interface CampaignNotificationRepository extends CrudRepository<CampaignNotification, Long> {

	/**
     * Finds the notifications for a campaign
     *
     * @param menuId  the campaign id to retrieve the notifications for
     * @return        the notifications for the campaign
     * @see		      CampaignNotification
     */
	List<CampaignNotification> findByCampaignId(Long campaignId);
}
