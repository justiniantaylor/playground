package com.pizzeria.resource.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.Order;

/**
 * JPA Repository for the Order domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Order
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

	/**
     * Finds all the orders ordered by ordered date.
     *
     * @return	the orders ordered by ordered date
     * @see		Order
     */
	List<Order> findAllByOrderByOrderedDateAsc();
}
