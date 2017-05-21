package com.pizzeria.resource.controller.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.pizzeria.resource.repository.MenuRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for customers menus.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/menu")
@PreAuthorize("hasRole('USER')")
public class MenuController {

	@Autowired
    private MenuRepository menuRepository;
	
	/**
	 * Find all the menus.
	 * 
     * @return the list of all menus
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findAll() {
  
    	MenusDTO menus = new MenusDTO(new ArrayList<MenuDTO>());

    	menuRepository.findAll().forEach(menu -> {
			menus.getMenus().add(transformMenu(menu));
    	});
  
    	return new ResponseEntity<MenusDTO>(menus, HttpStatus.OK);
    }
    
    /**
	 * Find a menu by its unique identifier.
	 * 
	 * @param id the menu id to retrieve
     * @return the response entity containing an error message or the requested menu DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	Menu menu = menuRepository.findOne(id);
    	
    	if (menu == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	MenuDTO menuDTO = transformMenu(menu);
		
    	return new ResponseEntity<MenuDTO>(menuDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new menu.
	 * 
	 * @param menuDTO the menu DTO to create
     * @return the response entity containing an error message or the created menu DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(@Valid @RequestBody MenuDTO menuDTO) {
    	
    	if (menuDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create a menu with an ID " + menuDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveMenu(menuDTO, new Menu());
    }
    
    /**
	 * Update an existing menu.
	 * 
	 * @param id 		the menu id to update
	 * @param menuDTO	the updated menu DTO
     * @return the response entity containing an error message or the updated menu DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody MenuDTO menuDTO) {
    	
    	Menu menu = menuRepository.findOne(id);
    	
    	if (menu == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	return saveMenu(menuDTO, menu);
    }
    
    /**
	 * Delete an existing menu.
	 * 
	 * @param id the menu id to delete
     * @return the response entity containing an error message or the deleted menu id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	Menu menu = menuRepository.findOne(id);
    	
    	if (menu == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	menuRepository.delete(menu);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transform a menu domain entity into a menu DTO.
   	 * 
   	 * @param menu the menu domain entity to transform to DTO
     * @return the menu DTO
   	 */
    private MenuDTO transformMenu(Menu menu) {
    	
    	MenuDTO menuDTO = new MenuDTO();
    	menuDTO.setId(menu.getId());
    	menuDTO.setStartDate(menu.getStartDate());
    	menuDTO.setEndDate(menu.getEndDate());
    	
		return menuDTO;
    } 
    
    /**
   	 * Save a menu DTO.
   	 * 
   	 * @param menuDTO 	the menu DTO used to create/update
   	 * @param menu 		the menu domain entity to create/update
     * @return the response entity or the created/updated menu DTO
   	 */
    private ResponseEntity<?> saveMenu(MenuDTO menuDTO, Menu menu) {
    	
    	menu.setStartDate(menuDTO.getStartDate());
    	menu.setEndDate(menuDTO.getEndDate());

		if (menuDTO.getEndDate()!= null && menuDTO.getEndDate().isBefore(menuDTO.getStartDate())) {    			
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "Start date cannot be before end date"), HttpStatus.BAD_REQUEST);
		}        	
		
		Optional<List<Menu>> existingMenus = menu.getId() != null 
				? menuRepository.findExistingMenuForDateExcludingId(menuDTO.getStartDate(), menu.getId()) : menuRepository.findExistingMenuForDate(menuDTO.getStartDate());

		if(existingMenus.isPresent() && existingMenus.get().size() > 0) {
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "There is already an existing menu for the start date supplied"), HttpStatus.BAD_REQUEST);        			    		
		}  		
	
    	menu = menuRepository.save(menu);
    	menuDTO.setId(menu.getId());
    	
    	return new ResponseEntity<MenuDTO>(menuDTO, HttpStatus.OK);
    }
}