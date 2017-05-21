package com.pizzeria.resource.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.MenuItem;

/**
 * JPA Repository for the Menu Item domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see MenuItem
 */
public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {

	/**
     * Finds the menu items for a menu
     *
     * @param menuId  the menu id to retrieve the menu items for
     * @return        the menu items for the menu
     * @see		      MenuItem
     */
	List<MenuItem> findByMenuId(Long menuId);
}
