package com.playground.payroll.util.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom runtime exception which is thrown when there is no tax period (and rates)
 * for the request payment period.
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Missing income tax period")
public class MissingIncomeTaxPeriodException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingIncomeTaxPeriodException(LocalDate paymentStartDate) {
		super("Could not find income tax period for '" + paymentStartDate.format(DateTimeFormatter.ofPattern("uuuu-MM-dd")) + "'.");
	}
}