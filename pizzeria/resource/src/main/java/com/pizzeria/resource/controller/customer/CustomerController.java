package com.pizzeria.resource.controller.customer;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.domain.NotificationMethod;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.NotificationMethodRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for customers.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('EMPLOYEE')")
public class CustomerController {

	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private NotificationMethodRepository notificationMethodRepository;
    
	/**
	 * Find all the customers.
	 * 
     * @return the list of all customers
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findAll() {
  
    	CustomersDTO customers = new CustomersDTO(new ArrayList<CustomerDTO>());

    	customerRepository.findAll().forEach(customer -> {
			customers.getCustomers().add(transformCustomer(customer));
    	});
  
    	customers.add(linkTo(methodOn(CustomerController.class).findAll()).withSelfRel());
    	
    	return new ResponseEntity<CustomersDTO>(customers, HttpStatus.OK);
    }
    
    /**
	 * Find a customer by its unique identifier.
	 * 
	 * @param id the customer id to retrieve
     * @return the response entity containing an error message or the requested customer DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	Customer customer = customerRepository.findOne(id);
    	
    	if (customer == null) {    		     		 
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No customer found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	CustomerDTO customerDTO = transformCustomer(customer);
		
    	return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new customer.
	 * 
	 * @param customerDTO the customer DTO to create
     * @return the response entity containing a string error or the created customer DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody CustomerDTO customerDTO) {
    	
    	if (customerDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create an customer with an ID " + customerDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveCustomer(customerDTO, new Customer());
    }
    
    /**
	 * Update an existing customer.
	 * 
	 * @param id 		  	the campaign id to update
	 * @param customerDTO	the updated customer DTO
     * @return the response entity containing an error message or the updated customer DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody CustomerDTO customerDTO) {
    	
    	Customer customer = customerRepository.findOne(id);
    	
    	if (customer == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No customer found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	return saveCustomer(customerDTO, customer);
    }
    
    /**
	 * Delete an existing customer.
	 * 
	 * @param id the customer id to delete
     * @return the response entity containing an error message or the deleted customer id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	Customer customer = customerRepository.findOne(id);
    	
    	if (customer == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No customer found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	customerRepository.delete(customer);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transform a Customer domain entity into a Customer DTO.
   	 * 
   	 * @param customer the customer domain entity to transform to DTO
     * @return the Customer DTO
   	 */
    private CustomerDTO transformCustomer(Customer customer) {
    	
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setId(customer.getId());
		customerDTO.setFirstName(customer.getFirstName());
		customerDTO.setLastName(customer.getLastName());
		customerDTO.setPreferredNotificationMethodId(customer.getPreferredNotificationMethod().getId());
		
		return customerDTO;
    } 
    
    /**
   	 * Save a Customer DTO.
   	 * 
   	 * @param customerDTO 	the customer DTO used to create/update
   	 * @param customer 		the customer domain entity to create/update
     * @return the response entity or the created/updated Customer DTO
   	 */
    private ResponseEntity<?> saveCustomer(CustomerDTO customerDTO, Customer customer) {
    	
    	customer.setFirstName(customerDTO.getFirstName());
    	customer.setLastName(customerDTO.getLastName());
    	
    	NotificationMethod preferredNotificationMethod = notificationMethodRepository.findOne(customerDTO.getPreferredNotificationMethodId());    	
    	if (preferredNotificationMethod == null) {    	
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No notification method found for ID " + customerDTO.getPreferredNotificationMethodId()), HttpStatus.NOT_FOUND);
		}    	
    	customer.setPreferredNotificationMethod(preferredNotificationMethod);
    
    	customer = customerRepository.save(customer);
    	customerDTO.setId(customer.getId());
    	
    	return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
    }
}