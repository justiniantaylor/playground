package com.pizzeria.resource.controller.order.item;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the order item data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class OrderItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "error.order.item.quantity.notnull")
    private Integer quantity;
	
	@NotNull(message = "error.order.item.unitPriceInCents.notnull")
    private BigDecimal unitPriceInCents;
	
	@NotNull(message = "error.order.item.orderId.notnull")
    private Long orderId;
	
	@NotNull(message = "error.order.item.menuItemId.notnull")
    private Long menuItemId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPriceInCents() {
		return unitPriceInCents;
	}

	public void setUnitPriceInCents(BigDecimal unitPriceInCents) {
		this.unitPriceInCents = unitPriceInCents;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}
}
