package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import lombok.Data;
/**
 * 
 * @author pradeep
 *
 *
 */
@Data//its used for putting the reminder in note
public class RemainderDto {
	private LocalDateTime reminder;
}
