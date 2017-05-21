package com.pizzeria.resource.controller.order.item;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of order items.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class OrderItemsDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<OrderItemDTO> orderItems;

	@JsonCreator
	public OrderItemsDTO(@JsonProperty("orderItems") List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}
}
