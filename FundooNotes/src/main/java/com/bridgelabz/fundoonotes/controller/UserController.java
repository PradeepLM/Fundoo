package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserDetail;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
//It is a specialization of @Component is auto detected through class path scanning.
@RestController
public class UserController {
	@Autowired//This annotation allows Spring to resolve and inject collaborating beans into your bean
	private Services service;
	@Autowired
	private JwtGenerator generate;
	
	/*api for registration*/
	//annotated methods handle the HTTP POST requests matched with given URI expression
	@PostMapping("/user/register")
	// ResponseEntity represents the whole HTTP response: status code, headers, and body.
	public ResponseEntity<Response> registration(@RequestBody UserDto information) {
		boolean result = service.register(information);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("registration sucessfully", 200, information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user alerday", 400, information));

	}
	
	/*api for login*/
	@PostMapping("/user/login")
	public ResponseEntity<UserDetail> login(@RequestBody LoginInformation information)
	{
		UserInformation userInformation=service.login(information);
		if(userInformation!=null)
		{
			String token=generate.jwtToken(userInformation.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login sucessfully",information.getEmail()).body(new UserDetail(token, 200, information));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("login failed", 400, information));
		
	}
	
	
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable ("token") String token) throws Exception{
	
		boolean update=service.verify(token);
		System.out.println("UPDATE :"+update);
		if(update)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified",200,token));
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("not verified",400));
		
	}
	
	
	@PutMapping("user/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String  email)
	{
		System.out.println(email);
		boolean result=service.isUserExist(email);
		if(result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200,email));
		}
				
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("not verfied", 400, email));
		
	}
	
	
	/* API for for updating password with token */
	@PutMapping("user/update/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token,@RequestBody PasswordUpdate update)
	{
		boolean result=service.update(update,token);
		if(result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Password updated sucessfully", 200,update));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Password does't match", 400, update));
	}
	
	
	
	/*api getting all user details*/
	@GetMapping("user/getusers")
	public ResponseEntity<Response> getUsers(){
		List<UserInformation> users=service.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("the users are", 200, users));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
