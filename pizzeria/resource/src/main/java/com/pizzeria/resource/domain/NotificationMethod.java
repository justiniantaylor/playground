package com.pizzeria.resource.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain Entity for the notification method that is used to send notifications to a customer, ie none, sms, push notification.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
@Entity
public class NotificationMethod {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private String description;
	@Column(nullable = false)
    private String code;
	
    protected NotificationMethod() {}

    public NotificationMethod(String description, String code) {    
        this.description = description;
        this.code = code;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}