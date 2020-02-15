package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PasswordUpdate {
	@NotNull(message="email may not null")
	private String email;
	@NotBlank(message="newPassword may not empty")
	private String newPassword;
	@NotBlank(message="confirmPassword may not empty")
	private String confirmPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
