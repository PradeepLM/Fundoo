package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RemainderDto {
	private LocalDateTime reminder;

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}
}
