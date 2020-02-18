package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NoteUpdate {
	@NotBlank
	private Long id;
	@NotNull
	private String title;
	@NotNull
	private String description;
	private boolean isArchieved;
	private boolean isPinned;
	private boolean isTrashed;
	private LocalDateTime createdDateAndTime;
	private LocalDateTime upDateAndTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isArchieved() {
		return isArchieved;
	}

	public void setArchieved(boolean isArchieved) {
		this.isArchieved = isArchieved;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isTrashed() {
		return isTrashed;
	}

	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	public LocalDateTime getCreatedDateAndTime() {
		return createdDateAndTime;
	}

	public void setCreatedDateAndTime(LocalDateTime createdDateAndTime) {
		this.createdDateAndTime = createdDateAndTime;
	}

	public LocalDateTime getUpDateAndTime() {
		return upDateAndTime;
	}

	public void setUpDateAndTime(LocalDateTime upDateAndTime) {
		this.upDateAndTime = upDateAndTime;
	}

}
