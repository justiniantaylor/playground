package com.pizzeria.resource.controller.order;

import java.time.LocalDate;
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

import com.pizzeria.resource.domain.Address;
import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.domain.Employee;
import com.pizzeria.resource.domain.Order;
import com.pizzeria.resource.repository.AddressRepository;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.EmployeeRepository;
import com.pizzeria.resource.repository.OrderRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for orders.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/order")
@PreAuthorize("hasRole('USER')")
public class OrderController {

	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private AddressRepository addressRepository;
	
	/**
	 * Find all the orders.
	 * 
     * @return the list of all orders
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findAll() {
  
    	OrdersDTO orders = new OrdersDTO(new ArrayList<OrderDTO>());

    	orderRepository.findAllByOrderByOrderedDateAsc().forEach(order -> {
			orders.getOrders().add(transformOrder(order));
    	});
  
    	return new ResponseEntity<OrdersDTO>(orders, HttpStatus.OK);
    }
    
    /**
	 * Find an order by its unique identifier.
	 * 
	 * @param id the order id to retrieve
     * @return the response entity containing an error message or the requested order DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	Order order = orderRepository.findOne(id);
    	
    	if (order == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No order found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	OrderDTO orderDTO = transformOrder(order);
		
    	return new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new order.
	 * 
	 * @param orderDTO the order DTO to create
     * @return the response entity containing an error message or the created order DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody OrderDTO orderDTO) {
    	
    	if(orderDTO.getOrderedDate() == null) {
    		orderDTO.setOrderedDate(LocalDate.now());
    	}
    	
    	if (orderDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create an order with an ID " + orderDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveOrder(orderDTO, new Order());
    }
    
    /**
	 * Update an existing order.
	 * 
	 * @param id 		the order id to update
	 * @param orderDTO	the updated order DTO
     * @return the response entity containing an error message or the updated order DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody OrderDTO orderDTO) {
    	
    	Order order = orderRepository.findOne(id);
    	
    	if (order == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No order found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	return saveOrder(orderDTO, order);
    }
    
    /**
	 * Delete an existing order.
	 * 
	 * @param id the order id to delete
     * @return the response entity containing an error message or the deleted order id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	Order order = orderRepository.findOne(id);
    	
    	if (order == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No order found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	orderRepository.delete(order);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transform an order domain entity into a order DTO.
   	 * 
   	 * @param order the order domain entity to transform to DTO
     * @return the order DTO
   	 */
    private OrderDTO transformOrder(Order order) {
    	
    	OrderDTO orderDTO = new OrderDTO();
    	orderDTO.setId(order.getId());
    	
		orderDTO.setDeliver(order.getDeliver());
		if(order.getCashier() != null) {
			orderDTO.setCashierId(order.getCashier().getId());
		}
		if(order.getCustomer() != null) {
			orderDTO.setCustomerId(order.getCustomer().getId());
		}
		if(order.getAddress() != null) {
			orderDTO.setAddressId(order.getAddress().getId());
		}
		orderDTO.setOrderedDate(order.getOrderedDate());
		orderDTO.setFulfilledDate(order.getFulfilledDate());
		
		return orderDTO;
    }
    
    /**
   	 * Save an order DTO.
   	 * 
   	 * @param orderDTO 	the order DTO used to create/update
   	 * @param order 	the order domain entity to create/update
     * @return the response entity or the created/updated order DTO
   	 */
    private ResponseEntity<?> saveOrder(OrderDTO orderDTO, Order order) {
    	
		order.setDeliver(orderDTO.getDeliver());
		
		if(orderDTO.getDeliver().booleanValue() == true) {		
			if(orderDTO.getCustomerId() == null) {				
				return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "Customer is required if delivery has been requested"), HttpStatus.BAD_REQUEST);
			}
			if(orderDTO.getAddressId() == null) {				
				return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "Address is required if delivery has been requested"), HttpStatus.BAD_REQUEST);
			}
		} else {
			if(orderDTO.getCashierId() == null) {				
				return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "Cashier is required if this is in store"), HttpStatus.BAD_REQUEST);
			}
		}
		if(orderDTO.getCashierId() != null) {			
			Employee employee = employeeRepository.findOne(orderDTO.getCashierId());
	    	if (employee == null) {	    		
				return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No employee found for ID " + orderDTO.getCashierId()), HttpStatus.NOT_FOUND);
			}
	    	order.setCashier(employee);
		}else {
			order.setCashier(null);
		}
		
		if(orderDTO.getCustomerId() != null) {			
			Customer customer = customerRepository.findOne(orderDTO.getCustomerId());
	    	if (customer == null) {	    		
				return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No customer found for ID " + orderDTO.getCustomerId()), HttpStatus.NOT_FOUND);
			}
	    	order.setCustomer(customer);
		} else {
			order.setCustomer(null);
		}
		
		if(orderDTO.getAddressId() != null) {			
			Address address = addressRepository.findOne(orderDTO.getAddressId());	    	
	    	if (address == null) {	    		
				return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No address found for ID " + orderDTO.getAddressId()), HttpStatus.NOT_FOUND);
			}	    	
	    	order.setAddress(address);
		} else {
			order.setAddress(null);
		}
		
		if(order.getOrderedDate() == null && orderDTO.getOrderedDate() != null) {
			order.setOrderedDate(orderDTO.getOrderedDate());
		}
		order.setFulfilledDate(orderDTO.getFulfilledDate());
		
		order = orderRepository.save(order);
		orderDTO.setId(order.getId());
		orderDTO.setOrderedDate(order.getOrderedDate());
		
		return new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);
    }
}