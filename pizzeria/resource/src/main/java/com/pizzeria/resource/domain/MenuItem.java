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
 * Domain Entity for the Menu Item. Price is stored in cents for rounding accuracy.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class MenuItem {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private String code;
	
	@Column(nullable = false)
	private String description;
    
	@Column(nullable = false)
    private BigDecimal unitPriceInCents;
	
	@Column(nullable = false)
    private Boolean available;
	
	@ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

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

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}