package com.bridgelabz.fundoonotes.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserDto {

	// private long userId;
	private String name;
	private String email;
	private String password;
	private long mobileNumber;
}
