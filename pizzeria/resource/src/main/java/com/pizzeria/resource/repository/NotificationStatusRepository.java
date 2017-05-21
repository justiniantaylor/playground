package com.pizzeria.resource.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.NotificationStatus;

/**
 * JPA Repository for the Notification Status domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see NotificationStatus
 */
public interface NotificationStatusRepository extends CrudRepository<NotificationStatus, Long> {

	/**
     * Finds the notification status by its code.
     *
     * @param code  the code for the notification status
     * @return      the notification status for the requested code
     * @see			NotificationStatus
     */
	public NotificationStatus findOneByCode(String code);
}
