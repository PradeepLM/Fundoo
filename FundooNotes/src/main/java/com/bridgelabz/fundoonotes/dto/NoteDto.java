package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 
 * @author pradeep
 * @purpose dto is a design pattern conceived to reduce the number of calls 
 * when working with remote interfaces
 */
@Data//
public class NoteDto {
	@NotNull
	private String title;
	@NotNull
	private String description;

}
