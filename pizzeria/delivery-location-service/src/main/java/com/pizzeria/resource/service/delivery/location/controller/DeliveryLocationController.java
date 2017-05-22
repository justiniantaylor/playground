package com.pizzeria.resource.service.delivery.location.controller;

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

import com.pizzeria.resource.service.delivery.location.domain.Delivery;
import com.pizzeria.resource.service.delivery.location.repository.DeliveryRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest micro service for updating a deliveries location.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/delivery/")
@PreAuthorize("hasRole('USER')")
public class DeliveryLocationController {

	@Autowired
    private DeliveryRepository deliveryRepository;
    
    /**
	 * Update a deliveries location coordinates, ie from mobile phone.
	 * 
	 * @param id 					the delivery id to update
	 * @param deliveryLocationDTO	the updated delivery location DTO
     * @return the response entity containing an error message or the delivery id that was updated
	 */
    @RequestMapping(value = "/{deliveryId}/location", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateDeliveryLocation(@PathVariable("deliveryId") Long deliveryId, @Valid @RequestBody DeliveryLocationDTO deliveryLocationDTO) {
    	
    	Delivery delivery = deliveryRepository.findOne(deliveryId);
    	
    	if (delivery == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No delivery found for ID " + deliveryId), HttpStatus.NOT_FOUND);
		}
    	delivery.setCurrentLatitude(deliveryLocationDTO.getLatitude());
    	delivery.setCurrentLongitude(deliveryLocationDTO.getLongitude());
    	deliveryRepository.save(delivery);
    	
    	return new ResponseEntity<Long>(deliveryId, HttpStatus.OK);
    }
}