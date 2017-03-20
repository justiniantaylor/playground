package com.playground.payroll.util.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.playground.payroll.util.validation.CollectionValidator;

/**
 * Controller advice that binds the collection validator. As collections
 * by default are not included when using <code>@Valid.<code>
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
@ControllerAdvice
public class CollectionValidationHandler {

	@Autowired
	protected LocalValidatorFactoryBean validator;

	/**
	 * Adds the {@link CollectionValidator} to the supplied
	 * {@link WebDataBinder}
	 * 
	 * @param binder web data binder.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new CollectionValidator(validator));
	}
}