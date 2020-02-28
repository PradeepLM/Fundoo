package com.bridgelabz.fundoonotes.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
/**
 * 
 * @author pradeep
 *
 */
@Component//is found during classpath scanning and
//registered in the context as a Spring bean
public class JwtGenerator {
private static final String secret="pradeep";
public String jwtToken(long l)
{
	String token=null;
	try {
	token=JWT.create().withClaim("id", l).sign(Algorithm.HMAC256(secret));
	}catch(IllegalArgumentException | JWTCreationException | UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	return token;
}

public Long parseJwt(String jwt)
{
	Long userId=(long) 0;
	if(jwt!=null)
	{
		try {
			userId=JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt).getClaim("id").asLong();
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	return userId;
}
}
