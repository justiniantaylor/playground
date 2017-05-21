package com.pizzeria.resource.controller.order;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of Orders.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class OrdersDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<OrderDTO> orders;

	@JsonCreator
	public OrdersDTO(@JsonProperty("orders") List<OrderDTO> orders) {
		this.orders = orders;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}
}
