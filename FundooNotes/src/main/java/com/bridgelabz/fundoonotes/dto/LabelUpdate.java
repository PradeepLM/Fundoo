package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;
/**
 * @author :pradeep
 * 
 */

import lombok.Data;
@Data
//its used for update the label
public class LabelUpdate {
	@NotNull
	private Long labelId;
	@NotNull
	private String labelName;

	

}
