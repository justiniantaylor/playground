package com.pizzeria.resource.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.Address;

/**
 * JPA Repository for the Address domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Address
 */
public interface AddressRepository extends CrudRepository<Address, Long> {

	/**
     * Finds the addresses for a customer
     *
     * @param menuId  the customer id to retrieve the addresses for
     * @return        the addresses for the customer
     * @see		      Address
     */
	List<Address> findByCustomerId(Long customerId);
}
