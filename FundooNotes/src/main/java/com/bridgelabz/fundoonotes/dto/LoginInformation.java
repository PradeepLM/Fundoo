package com.bridgelabz.fundoonotes.dto;

import lombok.Data;
	/**
	 * 
	 * @author pradeep
	 * @purpose is a design pattern conceived to reduce the number of calls 
	 * when working with remote interfaces
	 *
	 */
@Data//its used for login
public class LoginInformation {
	private String email;
	private String password;
}
