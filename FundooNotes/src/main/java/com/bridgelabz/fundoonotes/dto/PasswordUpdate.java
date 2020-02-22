package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class PasswordUpdate {
	@NotNull(message="email may not null")
	private String email;
	@NotBlank(message="newPassword may not empty")
	private String newPassword;
	@NotBlank(message="confirmPassword may not empty")
	private String confirmPassword;
}
