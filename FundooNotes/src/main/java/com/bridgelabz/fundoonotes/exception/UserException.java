package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
	private String message;

	public UserException(String message) {
		this.message = message;
	}
	
}
