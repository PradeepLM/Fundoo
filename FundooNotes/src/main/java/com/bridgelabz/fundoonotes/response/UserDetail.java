package com.bridgelabz.fundoonotes.response;

import lombok.Data;
/**
 * 
 * @author pradeep
 *
 */
@Data
public class UserDetail {

	private String token;
	private String message;
	private Object obj;
	
	public UserDetail(String token, Object obj) {
		this.setObj(obj);
		
		this.setToken(token);
	}

	public UserDetail(String message) {
		this.message=message;
		 	}

	

}
