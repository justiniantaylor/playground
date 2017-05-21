package com.pizzeria.resource.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.NotificationMethod;

/**
 * JPA Repository for the Notification Method domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see NotificationMethod
 */
public interface NotificationMethodRepository extends CrudRepository<NotificationMethod, Long> {

	/**
     * Finds the notification method by its code.
     *
     * @param code  the code for the notification method
     * @return      the notification method for the requested code
     * @see			NotificationMethod
     */
	public NotificationMethod findOneByCode(String code);
}
