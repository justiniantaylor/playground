package com.pizzeria.resource.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.OrderStatus;

/**
 * JPA Repository for the Order Status domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see OrderStatus
 */
public interface OrderStatusRepository extends CrudRepository<OrderStatus, Long> {

}
