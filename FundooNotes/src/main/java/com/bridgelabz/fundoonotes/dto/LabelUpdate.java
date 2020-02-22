package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class LabelUpdate {
	@NotNull
	private Long labelId;
	@NotNull
	private String labelName;

	

}
