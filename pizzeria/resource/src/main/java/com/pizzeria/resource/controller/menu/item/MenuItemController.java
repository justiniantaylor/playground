package com.pizzeria.resource.controller.menu.item;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pizzeria.resource.domain.Menu;
import com.pizzeria.resource.domain.MenuItem;
import com.pizzeria.resource.repository.MenuItemRepository;
import com.pizzeria.resource.repository.MenuRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for a menus items.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/menu/{menuId}/item")
@PreAuthorize("hasRole('MANAGER')")
public class MenuItemController {

	@Autowired
    private MenuItemRepository menuItemRepository;
	
	@Autowired
    private MenuRepository menuRepository;
	
	/**
	 * Find all the menu items for a menu
	 * 
     * @return the list of all menu items for a menu
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findAll(@PathVariable("menuId") Long menuId) {
  
    	MenuItemsDTO menuItems = new MenuItemsDTO(new ArrayList<MenuItemDTO>());

    	menuItemRepository.findByMenuId(menuId).forEach(menuItem -> {
			menuItems.getMenuItems().add(transformMenuItem(menuItem));
    	});
  
    	return new ResponseEntity<MenuItemsDTO>(menuItems, HttpStatus.OK);
    }
    
    /**
	 * Find a menu item by its unique identifier.
	 * 
	 * @param id the menu item id to retrieve
     * @return the response entity containing an error message or the requested menu item DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	MenuItem menuItem = menuItemRepository.findOne(id);
    	
    	if (menuItem == null) {
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu item found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	MenuItemDTO menuItemDTO = transformMenuItem(menuItem);
		
    	return new ResponseEntity<MenuItemDTO>(menuItemDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new menu item.
	 * 
	 * @param menuItemDTO the menu item DTO to create
     * @return the response entity containing an error message or the created menu item DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(@Valid @RequestBody MenuItemDTO menuItemDTO) {
    	
    	if (menuItemDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create a menu item with an ID " + menuItemDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveMenuItem(menuItemDTO, new MenuItem());
    }
    
    /**
	 * Update an existing menu item.
	 * 
	 * @param id 		  	the menu item id to update
	 * @param menuItemDTO	the updated menu item DTO
     * @return the response entity containing an error message or the updated menu item DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody MenuItemDTO menuItemDTO) {
    	
    	MenuItem menuItem = menuItemRepository.findOne(id);
    	
    	if (menuItem == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu item found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	return saveMenuItem(menuItemDTO, menuItem);
    }
    
    /**
	 * Delete an existing menu item.
	 * 
	 * @param id the menu item id to delete
     * @return the response entity containing an error message or the deleted menu item id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	MenuItem menuItem = menuItemRepository.findOne(id);
    	
    	if (menuItem == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu item found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	menuItemRepository.delete(menuItem);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transform a menu item domain entity into a menu item DTO.
   	 * 
   	 * @param menuItem the menu item domain entity to transform to DTO
     * @return the menu item DTO
   	 */
    private MenuItemDTO transformMenuItem(MenuItem menuItem) {
    	
    	MenuItemDTO menuItemDTO = new MenuItemDTO();
    	menuItemDTO.setId(menuItem.getId());
    	menuItemDTO.setCode(menuItem.getCode());
    	menuItemDTO.setDescription(menuItem.getDescription());
    	menuItemDTO.setUnitPriceInCents(menuItem.getUnitPriceInCents());
    	menuItemDTO.setAvailable(menuItem.getAvailable());
    	menuItemDTO.setMenuId(menuItem.getMenu().getId());
		
		return menuItemDTO;
    } 
    
    /**
   	 * Save a menu item DTO.
   	 * 
   	 * @param menuItemDTO 	the menu item DTO used to create/update
   	 * @param menuItem 		the menu item domain entity to create/update
     * @return the response entity or the created/updated menu item DTO
   	 */
    private ResponseEntity<?> saveMenuItem(MenuItemDTO menuItemDTO, MenuItem menuItem) {
    	
    	menuItem.setCode(menuItemDTO.getCode());
    	menuItem.setDescription(menuItemDTO.getDescription());
    	menuItem.setUnitPriceInCents(menuItemDTO.getUnitPriceInCents());
    	menuItem.setAvailable(menuItemDTO.getAvailable());

    	Menu menu = menuRepository.findOne(menuItemDTO.getMenuId());    	
    	if (menu == null) {
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu found for ID " + menuItemDTO.getMenuId()), HttpStatus.NOT_FOUND);
		}
    	menuItem.setMenu(menu);
    	
    	menuItem = menuItemRepository.save(menuItem);
    	menuItemDTO.setId(menuItem.getId());
    	
    	return new ResponseEntity<MenuItemDTO>(menuItemDTO, HttpStatus.OK);
    }
}