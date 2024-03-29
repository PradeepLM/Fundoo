package com.bridgelabz.fundoonotes.response;

import lombok.Data;
/**
 * 
 * @author pradeep
 *
 */
@Data
public class Response {
	private String message;
	private int statuscode;
	private Object obj;
	
	public Response(String message, int statuscode) {
		this.message = message;
		this.statuscode = statuscode;
		
	}
	
	public Response(String message, int statuscode, Object obj) {
		this.message = message;
		this.statuscode = statuscode;
		this.obj = obj;
	}
	public Response(String message, Object obj) {
		this.message = message;
		this.obj = obj;
	}

	public Response(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
