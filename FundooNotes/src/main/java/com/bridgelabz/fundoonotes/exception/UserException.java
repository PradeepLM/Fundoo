package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;
/**
 * 
 * @author pradeep
 *
 */
@Getter//its used for user exception
public class UserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String message;

	public UserException(String message) {
		this.message = message;
	}
	
}
