package com.pizzeria.resource.controller.menu;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing a list of menus.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class MenusDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<MenuDTO> menus;

	@JsonCreator
	public MenusDTO(@JsonProperty("menus") List<MenuDTO> menus) {
		this.menus = menus;
	}

	public List<MenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDTO> menus) {
		this.menus = menus;
	}
}
