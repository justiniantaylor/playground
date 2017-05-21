package com.pizzeria.resource.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.Customer;

/**
 * JPA Repository for the Customer domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Customer
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
