package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class LabelDto {
	@NotNull
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
