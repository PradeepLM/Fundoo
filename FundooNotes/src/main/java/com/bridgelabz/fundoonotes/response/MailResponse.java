package com.bridgelabz.fundoonotes.response;

import org.springframework.stereotype.Component;
/**
 * 
 * @author pradeep
 *
 */
@Component
public class MailResponse {
	public String fromMessage(String url, String token) {
		return url + "/" + token;
	}
}
