package com.pizzeria.resource.controller.campaign.notification;

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

import com.pizzeria.resource.domain.Campaign;
import com.pizzeria.resource.domain.CampaignNotification;
import com.pizzeria.resource.domain.Customer;
import com.pizzeria.resource.domain.NotificationMethod;
import com.pizzeria.resource.domain.NotificationStatus;
import com.pizzeria.resource.repository.CampaignNotificationRepository;
import com.pizzeria.resource.repository.CampaignRepository;
import com.pizzeria.resource.repository.CustomerRepository;
import com.pizzeria.resource.repository.NotificationMethodRepository;
import com.pizzeria.resource.repository.NotificationStatusRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for a campaign notifications.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/campaign/{campaignId}/notification")
@PreAuthorize("hasRole('USER')")
public class CampaignNotificationController {

	@Autowired
    private CampaignNotificationRepository campaignNotificationRepository;
	
	@Autowired
    private CampaignRepository campaignRepository;
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private NotificationMethodRepository notificationMethodRepository;
	
	@Autowired
    private NotificationStatusRepository notificationStatusRepository;

	/**
	 * Find all the campaign notifications for a customer.
	 * 
     * @return the list of all campaign notifications
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")   
    public ResponseEntity<?> findAll(@PathVariable("campaignId") Long campaignId) {
  
    	CampaignNotificationsDTO campaignNotifications = new CampaignNotificationsDTO(new ArrayList<CampaignNotificationDTO>());

    	campaignNotificationRepository.findByCampaignId(campaignId).forEach(campaignNotification -> {
			campaignNotifications.getCampaignNotifications().add(transformCampaignNotification(campaignNotification));
    	});
  
    	return new ResponseEntity<CampaignNotificationsDTO>(campaignNotifications, HttpStatus.OK);
    }
    
    /**
	 * Find an campaign notification by its unique identifier.
	 * 
	 * @param id the campaign notification id to retrieve
     * @return the response entity containing an error message or the requested campaign notification DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	CampaignNotification campaignNotification = campaignNotificationRepository.findOne(id);
    	
    	if (campaignNotification == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No campaign notification found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	CampaignNotificationDTO campaignNotificationDTO = transformCampaignNotification(campaignNotification);
		
    	return new ResponseEntity<CampaignNotificationDTO>(campaignNotificationDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new campaign notification.
	 * 
	 * @param campaignNotificationDTO the campaign notification DTO to create
     * @return the response entity containing an error message or the created campaign notification DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody CampaignNotificationDTO campaignNotificationDTO) {
    	
    	if (campaignNotificationDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create an campaignNotification with an ID " + campaignNotificationDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveCampaignNotification(campaignNotificationDTO, new CampaignNotification());
    }
    
    /**
	 * Update an existing campaign notification.
	 * 
	 * @param id 		  				the campaign notification id to update
	 * @param campaignNotificationDTO	the updated campaign notification DTO
     * @return the response entity containing an error message or the updated campaign notification DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody CampaignNotificationDTO campaignNotificationDTO) {
    	
    	CampaignNotification campaignNotification = campaignNotificationRepository.findOne(id);
    	
    	if (campaignNotification == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No campaign notification found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	return saveCampaignNotification(campaignNotificationDTO, campaignNotification);
    }
    
    /**
	 * Delete an existing campaign notification.
	 * 
	 * @param id the campaign notification id to delete
     * @return the response entity containing an error message or the deleted campaign notification id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	CampaignNotification campaignNotification = campaignNotificationRepository.findOne(id);
    	
    	if (campaignNotification == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No campaign notification found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	campaignNotificationRepository.delete(campaignNotification);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transform an campaign notification domain entity into a campaign notification DTO.
   	 * 
   	 * @param campaignNotification the campaign notification domain entity to transform to DTO
     * @return the campaign notification DTO
   	 */
    private CampaignNotificationDTO transformCampaignNotification(CampaignNotification campaignNotification) {
    	
    	CampaignNotificationDTO campaignNotificationDTO = new CampaignNotificationDTO();
    	campaignNotificationDTO.setId(campaignNotification.getId());
    	campaignNotificationDTO.setCampaignId(campaignNotification.getCampaign().getId());
    	campaignNotificationDTO.setCustomerId(campaignNotification.getCustomer().getId());
    	campaignNotificationDTO.setNotificationDate(campaignNotification.getNotificationDate());
    	
    	if(campaignNotification.getNotificationMethod() != null) {
    		campaignNotificationDTO.setNotificationMethodId(campaignNotification.getNotificationMethod().getId());	
    	}    	
    	if(campaignNotification.getNotificationStatus() != null) {
    		campaignNotificationDTO.setNotificationStatusId(campaignNotification.getNotificationStatus().getId());	
    	}
		
		return campaignNotificationDTO;
    } 
    
    /**
   	 * Save an campaign notification DTO.
   	 * 
   	 * @param campaignNotificationDTO 	the campaign notification DTO used to create/update
   	 * @param campaignNotification 		the campaign notification domain entity to create/update
     * @return the response entity or the created/updated campaign notification DTO
   	 */
    private ResponseEntity<?> saveCampaignNotification(CampaignNotificationDTO campaignNotificationDTO, CampaignNotification campaignNotification) {
    	
    	campaignNotification.setNotificationDate(campaignNotificationDTO.getNotificationDate());
    	
		Campaign campaign = campaignRepository.findOne(campaignNotificationDTO.getCampaignId());
    	if (campaign == null) {    	
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No campaign found for ID " + campaignNotificationDTO.getCampaignId()), HttpStatus.NOT_FOUND);
		}
    	campaignNotification.setCampaign(campaign);
    	
    	Customer customer = customerRepository.findOne(campaignNotificationDTO.getCustomerId());
    	if (customer == null) {    	
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No customer found for ID " + campaignNotificationDTO.getCustomerId()), HttpStatus.NOT_FOUND);
		}
    	campaignNotification.setCustomer(customer);
    	
    	NotificationMethod notificationMethod = notificationMethodRepository.findOne(campaignNotificationDTO.getNotificationMethodId());
    	if (notificationMethod == null) {    	
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No notification method found for ID " + campaignNotificationDTO.getNotificationMethodId()), HttpStatus.NOT_FOUND);
		}
    	campaignNotification.setNotificationMethod(notificationMethod);
    	
    	NotificationStatus notificationStatus = notificationStatusRepository.findOne(campaignNotificationDTO.getNotificationStatusId());
    	if (notificationStatus == null) {    	
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No notification status found for ID " + campaignNotificationDTO.getNotificationStatusId()), HttpStatus.NOT_FOUND);
		}
    	campaignNotification.setNotificationStatus(notificationStatus);
    	
    	campaignNotification = campaignNotificationRepository.save(campaignNotification);
    	campaignNotificationDTO.setId(campaignNotification.getId());
    	
    	return new ResponseEntity<CampaignNotificationDTO>(campaignNotificationDTO, HttpStatus.OK);
    }
}