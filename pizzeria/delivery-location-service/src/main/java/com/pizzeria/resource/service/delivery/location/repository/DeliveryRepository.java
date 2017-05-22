package com.pizzeria.resource.service.delivery.location.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.service.delivery.location.domain.Delivery;

/**
 * JPA Repository for the Delivery domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Delivery
 */
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
	
}
