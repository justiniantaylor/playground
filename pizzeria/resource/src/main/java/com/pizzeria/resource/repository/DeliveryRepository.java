package com.pizzeria.resource.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.Delivery;

/**
 * JPA Repository for the Delivery domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Delivery
 */
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

}
