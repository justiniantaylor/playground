package com.pizzeria.resource.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizzeria.resource.domain.Employee;

/**
 * JPA Repository for the employee domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Server
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
