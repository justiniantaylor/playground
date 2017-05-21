package com.pizzeria.resource.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OrderBy;

/**
 * Domain Entity for the menu.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class Menu {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private LocalDate startDate;
	@Column(nullable = true)
    private LocalDate endDate;
    
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
    @OrderBy(clause = "description ASC")
    private List<MenuItem> menuItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
}