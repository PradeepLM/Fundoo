 package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;

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
	HttpStatus httpstatus;

//	public UserException(String message) {
//		this.message = message;
//	}
	public UserException(String message,HttpStatus httpstatus) {
		this.message = message;
		this.httpstatus=httpstatus;
	}
	
}
