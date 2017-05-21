package com.pizzeria.resource.controller.campaign;

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
import com.pizzeria.resource.repository.CampaignRepository;
import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

/**
 * Rest service for campaigns.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@RestController
@RequestMapping("/campaign")
@PreAuthorize("hasRole('USER')")
public class CampaignController {

	@Autowired
    private CampaignRepository campaignRepository;
	
	/**
	 * Find all the campaigns.
	 * 
     * @return the list of all campaign DTOs
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findAll() {
  
    	CampaignsDTO campaigns = new CampaignsDTO(new ArrayList<CampaignDTO>());

    	campaignRepository.findAll().forEach(campaign -> {
			campaigns.getCampaigns().add(transformCampaign(campaign));
    	});
    	
    	return new ResponseEntity<CampaignsDTO>(campaigns, HttpStatus.OK);
    }
    
    /**
	 * Find a campaign by its unique identifier.
	 * 
	 * @param id the campaign id to retrieve
     * @return the response entity containing an error message or the requested campaign DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
  
    	Campaign campaign = campaignRepository.findOne(id);
    	
    	if (campaign == null) {    		
			return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No campaign found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	CampaignDTO campaignDTO = transformCampaign(campaign);
		
    	return new ResponseEntity<CampaignDTO>(campaignDTO, HttpStatus.OK);
    }
    
    /**
	 * Create a new campaign.
	 * 
	 * @param campaignDTO the campaign DTO to create
     * @return the response entity containing an error message or the created campaign DTO
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(@Valid @RequestBody CampaignDTO campaignDTO) {
    	
    	if (campaignDTO.getId() != null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "You may not create an campaign with an ID " + campaignDTO.getId()), HttpStatus.NOT_FOUND);
		}
    	
    	return saveOrder(campaignDTO, new Campaign());
    }
    
    /**
	 * Update an existing campaign.
	 * 
	 * @param id 		  	the campaign id to update
	 * @param campaignDTO	the updated campaign DTO
     * @return the response entity containing an error message or the updated campaign DTO
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody CampaignDTO campaignDTO) {
    	
    	Campaign campaign = campaignRepository.findOne(id);
    	
    	if (campaign == null) {    		
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No campaign found for ID " + id), HttpStatus.NOT_FOUND);
		}

    	return saveOrder(campaignDTO, campaign);
    }
    
    /**
	 * Delete an existing campaign.
	 * 
	 * @param id the campaign id to delete
     * @return the response entity containing an error message or the deleted campaign id
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

    	Campaign campaign = campaignRepository.findOne(id);
    	
    	if (campaign == null) {
    		return new ResponseEntity<MessageDTO>(new MessageDTO(MessageType.ERROR, "No campaign found for ID " + id), HttpStatus.NOT_FOUND);
		}
    	
    	campaignRepository.delete(campaign);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    /**
   	 * Transforms a campaign domain entity into a campaign DTO.
   	 * 
   	 * @param campaign the campaign domain entity to transform to DTO
     * @return the campaign DTO
   	 */
    private CampaignDTO transformCampaign(Campaign campaign) {
    	
    	CampaignDTO campaignDTO = new CampaignDTO();
    	campaignDTO.setId(campaign.getId());
		campaignDTO.setName(campaign.getName());
		campaignDTO.setText(campaign.getText());
		campaignDTO.setStartDate(campaign.getStartDate());
		campaignDTO.setEndDate(campaign.getEndDate());
		
		return campaignDTO;
    }
    
    /**
   	 * Saves a campaign DTO.
   	 * 
   	 * @param campaignDTO 	the campaign DTO used to create/update
   	 * @param campaign 		the campaign domain entity to create/update
     * @return the response entity or the created/updated campaign DTO
   	 */
    private ResponseEntity<?> saveOrder(CampaignDTO campaignDTO, Campaign campaign) {
    	
    	campaign.setName(campaignDTO.getName());
    	campaign.setText(campaignDTO.getText());
    	campaign.setStartDate(campaignDTO.getStartDate());
    	campaign.setEndDate(campaignDTO.getEndDate());

    	campaign = campaignRepository.save(campaign);
    	campaignDTO.setId(campaign.getId());
    	
    	return new ResponseEntity<CampaignDTO>(campaignDTO, HttpStatus.OK);    	
    }
}