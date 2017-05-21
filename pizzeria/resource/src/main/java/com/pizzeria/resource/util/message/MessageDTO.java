package com.pizzeria.resource.util.message;

import java.io.Serializable;

/**
 * DTO containing the generic message of a type and a string message
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
public class MessageDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private MessageType type;

	public MessageDTO() {
		super();
	}

	public MessageDTO(MessageType type, String message) {
		super();
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
}