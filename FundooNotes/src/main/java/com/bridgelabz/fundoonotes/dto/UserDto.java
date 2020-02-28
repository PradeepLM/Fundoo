package com.bridgelabz.fundoonotes.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author pradeep
 *
 */
@Data
@Component//its class used for attribute of user
public class UserDto {

	// private long userId;
	private String name;
	private String email;
	private String password;
	private long mobileNumber;
}
