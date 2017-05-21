package com.pizzeria.resource.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.Campaign;

/**
 * JPA Repository for the Campaign domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Campaign
 */
public interface CampaignRepository extends CrudRepository<Campaign, Long> {

}
