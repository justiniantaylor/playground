package com.pizzeria.resource.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Domain Entity for the order item and its quantity. Unit price is stored in cents for rounding accuracy.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class OrderItem {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private Integer quantity;
	
	@Column(nullable = false)
    private BigDecimal unitPriceInCents;
	
	@ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
	
	@ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
}