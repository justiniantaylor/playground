package com.pizzeria.resource.util.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pizzeria.resource.util.message.MessageDTO;
import com.pizzeria.resource.util.message.MessageType;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * This controller advice handles the all data validation for the rest controller API data input values.
 * 
 * It catches all the method argument not valid exceptions and transforms them into error messages that
 * are passed back to the clients so they have an indication of which values they sent through incorrectly.
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
@ControllerAdvice
public class ValidationHandler {
	
	@Autowired
	private MessageSource msgSource;

	/**
	 * Catches all MethodArgumentNotValidExceptions and turns them into an HTTP Bad Request
	 * and includes the validation errors back with the response message.
	 * 
	 * @param ex	the MethodArgumentNotValidException that was thrown to the controller containing the field errors
	 * @return 		the custom message containing the field error
	 * @see 		MethodArgumentNotValidException
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<MessageDTO> processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();
		List<MessageDTO> messages = new ArrayList<MessageDTO>();
		for(FieldError error: errors) {
			messages.add(processFieldError(error));			
		}		
		return messages;
	}

	/**
	 * This processes field errors into our custom message structure which will be
	 * used to return in the response messages to the rest clients.
	 * 
	 * @param error the input data field the error is for.
	 * @return 		the custom message containing the field error
	 */
	private MessageDTO processFieldError(FieldError error) {
		MessageDTO message = null;
		if (error != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
			message = new MessageDTO(MessageType.ERROR, msg);
		}
		return message;
	}
}
