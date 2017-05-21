package com.pizzeria.resource.controller.menu.item;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the menu item data.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class MenuItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "error.menu.item.code.notnull")
	private String code;
	
	@NotNull(message = "error.menu.item.description.notnull")
	private String description;
    
	@NotNull(message = "error.menu.item.unitPriceInCents.notnull")
    private BigDecimal unitPriceInCents;
	
	@NotNull(message = "error.menu.item.available.notnull")
    private Boolean available;
	
	@NotNull(message = "error.menu.item.menuId.notnull")
    private Long menuId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitPriceInCents() {
		return unitPriceInCents;
	}

	public void setUnitPriceInCents(BigDecimal unitPriceInCents) {
		this.unitPriceInCents = unitPriceInCents;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}
