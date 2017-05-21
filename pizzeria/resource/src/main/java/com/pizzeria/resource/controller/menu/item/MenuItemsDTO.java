package com.pizzeria.resource.controller.menu.item;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of menu items.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class MenuItemsDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<MenuItemDTO> menuItems;

	@JsonCreator
	public MenuItemsDTO(@JsonProperty("menuItems") List<MenuItemDTO> menuItems) {
		this.menuItems = menuItems;
	}

	public List<MenuItemDTO> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItemDTO> menuItems) {
		this.menuItems = menuItems;
	}
}
