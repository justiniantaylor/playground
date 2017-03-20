package com.playground.payroll.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.playground.payroll.domain.IncomeTaxPeriod;

/**
 * JPA Repository for the Income Tax Period domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see IncomeTaxPeriod
 */
public interface IncomeTaxPeriodRepository extends CrudRepository<IncomeTaxPeriod, Long> {

	/**
     * Finds all the tax period and returns them order by their start dates ascending.
     *
     * @param paymentStartDate  the payment start date indicating the payment period and tax period
     * @return          		all the income tax periods ordered by their start dates ascending.
     * @see						IncomeTaxPeriod
     */
	List<IncomeTaxPeriod> findAllByOrderByStartDate();
	
	/**
     * Finds the correct income tax period and its related tax rates based on a date in 
     * between the start and the end of a tax period.
     * <p>
     * This method is using spring cache in order to cache the income tax period and its 
     * eagerly fetched tax rates.
     * <p>
     * The related tax rates will be returned order by the tax rate start amount ascending (nulls being first)
     *
     * @param paymentStartDate  the payment start date indicating the payment period and tax period
     * @return          		the income tax period for the supplied payment start date
     * @see						IncomeTaxPeriod
     */
	@Cacheable("incomeTaxPeriods")
	@Query("SELECT incomeTaxPeriod FROM IncomeTaxPeriod incomeTaxPeriod WHERE incomeTaxPeriod.startDate <= :paymentStartDate AND incomeTaxPeriod.endDate >= :paymentStartDate")
	Optional<IncomeTaxPeriod> findIncomeTaxPeriod(@Param("paymentStartDate") LocalDate paymentStartDate);
}
