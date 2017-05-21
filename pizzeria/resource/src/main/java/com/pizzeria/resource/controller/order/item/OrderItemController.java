package com.pizzeria.resource.controller.order.item;

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

import com.pizzeria.resource.domain.MenuItem;
import com.pizzeria.resource.domain.Order;
import com.pizzeria.resource.domain.OrderItem;
import com.pizzeria.resource.repository.MenuItemRepository;
import com.pizzeria.resource.repository.OrderItemRepository;
import com.pizzeria.resource.repository.OrderRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for a orders items.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/order/{orderId}/item")
@PreAuthorize("hasRole('USER')")
public class OrderItemController {

	@Autowired
    private OrderItemRepository orderItemRepository;
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
    private MenuItemRepository menuItemRepository;
	
	/**
	 * Find all the order items for a order
	 * 
     * @return the list of all order items for a order
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findAll(@PathVariable("orderId") Long orderId) {
  
    	OrderItemsDTO orderItems = new OrderItemsDTO(new ArrayList<OrderItemDTO>());

    	orderItemRepository.findByOrderId(orderId).forEach(orderItem -> {
			orderItems.getOrderItems().add(transformOrderItem(orderItem));
    	});
  
    	return new ResponseEntity<OrderItemsDTO>(orderItems, HttpStatus.OK);
    }
    
    /**
	 * Find a order item by its unique identifier.
	 * 
	 * @param id the order item id to retrieve
     * @return the response entity containing an error message or the requested order item DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	OrderItem orderItem = orderItemRepository.findOne(id);
    	
    	if (orderItem == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No order item found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	OrderItemDTO orderItemDTO = transformOrderItem(orderItem);
		
    	return new ResponseEntity<OrderItemDTO>(orderItemDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new order item.
	 * 
	 * @param orderItemDTO the order item DTO to create
     * @return the response entity containing an error message or the created order item DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody OrderItemDTO orderItemDTO) {
    	
    	if (orderItemDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create a order item with an ID " + orderItemDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveOrderItem(orderItemDTO, new OrderItem());
    }
    
    /**
	 * Update an existing order item.
	 * 
	 * @param id 		  	the order item id to update
	 * @param orderItemDTO	the updated order item DTO
     * @return the response entity containing an error message or the updated order item DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody OrderItemDTO orderItemDTO) {
    	
    	OrderItem orderItem = orderItemRepository.findOne(id);
    	
    	if (orderItem == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No order item found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	return saveOrderItem(orderItemDTO, orderItem);
    }
    
    /**
	 * Delete an existing order item.
	 * 
	 * @param id the order item id to delete
     * @return the response entity containing an error message or the deleted order item id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	OrderItem orderItem = orderItemRepository.findOne(id);
    	
    	if (orderItem == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No order item found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	orderItemRepository.delete(orderItem);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transform a order item domain entity into a order item DTO.
   	 * 
   	 * @param orderItem the order item domain entity to transform to DTO
     * @return the order item DTO
   	 */
    private OrderItemDTO transformOrderItem(OrderItem orderItem) {
    	
    	OrderItemDTO orderItemDTO = new OrderItemDTO();
    	orderItemDTO.setId(orderItem.getId());
    	orderItemDTO.setQuantity(orderItem.getQuantity());    	
    	orderItemDTO.setUnitPriceInCents(orderItem.getUnitPriceInCents());    	
    	orderItemDTO.setOrderId(orderItem.getOrder().getId());    	
    	orderItemDTO.setMenuItemId(orderItem.getMenuItem().getId());
		
		return orderItemDTO;
    } 
    
    /**
   	 * Save a order item DTO.
   	 * 
   	 * @param orderItemDTO 	the order item DTO used to create/update
   	 * @param orderItem 		the order item domain entity to create/update
     * @return the response entity or the created/updated order item DTO
   	 */
    private ResponseEntity<?> saveOrderItem(OrderItemDTO orderItemDTO, OrderItem orderItem) {
    	
    	orderItem.setQuantity(orderItemDTO.getQuantity());    	
    	orderItem.setUnitPriceInCents(orderItemDTO.getUnitPriceInCents());    	
    
    	Order order = orderRepository.findOne(orderItemDTO.getOrderId());    
    	if (order == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No order found for ID " + orderItemDTO.getOrderId()), HttpStatus.NOT_FOUND);
		}
    	orderItem.setOrder(order); 
    	
    	MenuItem menuItem = menuItemRepository.findOne(orderItemDTO.getMenuItemId());    
    	if (menuItem == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No menu item found for ID " + orderItemDTO.getMenuItemId()), HttpStatus.NOT_FOUND);
		}
    	orderItem.setMenuItem(menuItem);
    	
    	orderItem = orderItemRepository.save(orderItem);
    	orderItemDTO.setId(orderItem.getId());
    	
    	return new ResponseEntity<OrderItemDTO>(orderItemDTO, HttpStatus.OK);
    }
}