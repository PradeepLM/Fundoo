package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NoteDto {
	@NotNull
	private String title;
	@NotNull
	private String description;

}
