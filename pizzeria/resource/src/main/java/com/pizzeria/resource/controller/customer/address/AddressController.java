package com.pizzeria.resource.controller.customer.address;

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
import com.pizzeria.resource.repository.AddressRepository;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for a customers addresses.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/customer/{customerId}/address")
@PreAuthorize("hasRole('USER')")
public class AddressController {

	@Autowired
    private AddressRepository addressRepository;
	
	@Autowired
    private CustomerRepository customerRepository;

	/**
	 * Find all the addresses for a customer.
	 * 
     * @return the list of all addresses
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")   
    public ResponseEntity<?> findAll(@PathVariable("customerId") Long customerId) {
  
    	AddressesDTO addresses = new AddressesDTO(new ArrayList<AddressDTO>());

    	addressRepository.findByCustomerId(customerId).forEach(address -> {
			addresses.getAddresses().add(transformAddress(address));
    	});
  
    	return new ResponseEntity<AddressesDTO>(addresses, HttpStatus.OK);
    }
    
    /**
	 * Find an address by its unique identifier.
	 * 
	 * @param id the address id to retrieve
     * @return the response entity containing an error message or the requested address DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	Address address = addressRepository.findOne(id);
    	
    	if (address == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No address found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	AddressDTO addressDTO = transformAddress(address);
		
    	return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new address.
	 * 
	 * @param addressDTO the address DTO to create
     * @return the response entity containing an error message or the created address DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody AddressDTO addressDTO) {
    	
    	if (addressDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create an address with an ID " + addressDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveAddress(addressDTO, new Address());
    }
    
    /**
	 * Update an existing address.
	 * 
	 * @param id 		  	the address id to update
	 * @param addressDTO	the updated address DTO
     * @return the response entity containing an error message or the updated address DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody AddressDTO addressDTO) {
    	
    	Address address = addressRepository.findOne(id);
    	
    	if (address == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No address found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	return saveAddress(addressDTO, address);
    }
    
    /**
	 * Delete an existing address.
	 * 
	 * @param id the address id to delete
     * @return the response entity containing an error message or the deleted address id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	Address address = addressRepository.findOne(id);
    	
    	if (address == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No address found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	addressRepository.delete(address);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transform an address domain entity into a address DTO.
   	 * 
   	 * @param address the address domain entity to transform to DTO
     * @return the address DTO
   	 */
    private AddressDTO transformAddress(Address address) {
    	
    	AddressDTO addressDTO = new AddressDTO();
    	addressDTO.setId(address.getId());
    	addressDTO.setStreetNumber(address.getStreetNumber());
    	addressDTO.setStreetName(address.getStreetName());
    	addressDTO.setNeighborhoodName(address.getNeighborhoodName());
    	addressDTO.setCityName(address.getCityName());
    	addressDTO.setState(address.getState());
    	addressDTO.setZipCode(address.getZipCode());
    	addressDTO.setLongitude(address.getLongitude());
    	addressDTO.setLatitude(address.getLatitude());
    	addressDTO.setCustomerId(address.getCustomer().getId());
		
		return addressDTO;
    } 
    
    /**
   	 * Save an address DTO.
   	 * 
   	 * @param addressDTO 	the address DTO used to create/update
   	 * @param address 		the address domain entity to create/update
     * @return the response entity or the created/updated address DTO
   	 */
    private ResponseEntity<?> saveAddress(AddressDTO addressDTO, Address address) {
    	
    	address.setStreetNumber(addressDTO.getStreetNumber());
    	address.setStreetName(addressDTO.getStreetName());
    	address.setNeighborhoodName(addressDTO.getNeighborhoodName());
    	address.setCityName(addressDTO.getCityName());
    	address.setState(addressDTO.getState());
    	address.setZipCode(addressDTO.getZipCode());
    	address.setLongitude(addressDTO.getLongitude());
    	address.setLatitude(addressDTO.getLatitude());

		Customer customer = customerRepository.findOne(addressDTO.getCustomerId());
    	if (customer == null) {    	
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No customer found for ID " + addressDTO.getCustomerId()), HttpStatus.NOT_FOUND);
		}
    	address.setCustomer(customer);
    	
    	address = addressRepository.save(address);
    	addressDTO.setId(address.getId());
    	
    	return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.OK);
    }
}