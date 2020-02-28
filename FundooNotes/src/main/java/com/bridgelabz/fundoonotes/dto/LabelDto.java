package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;
/**
 * @author :pradeep
 * @purose :is a design pattern conceived to reduce the number of calls
 *  when working with remote interfaces
 * 
 */

import lombok.Data;
@Data
//its used label name
public class LabelDto {
	@NotNull
	private String name;
	
}
