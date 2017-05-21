package com.pizzeria.resource.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.OrderItem;

/**
 * JPA Repository for the Order Item domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see OrderItem
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

	/**
     * Finds the order items for an order
     *
     * @param orderId the order id to retrieve the order items for
     * @return        the order items for the order
     * @see		      OrderItem
     */
	List<OrderItem> findByOrderId(Long orderId);
}
