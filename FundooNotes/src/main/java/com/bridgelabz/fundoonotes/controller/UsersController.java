package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.ForgotPassword;
import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserDetail;
import com.bridgelabz.fundoonotes.service.IServices;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author pradeep
 *
 */
//It is a specialization of @Component is auto detected through class path scanning.
@RestController
@CrossOrigin("*")
public class UsersController {
	@Autowired // This annotation allows Spring to resolve and inject collaborating beans into
				// your bean
	private IServices service;
	@Autowired
	private JwtGenerator generate;

	/* api for registration */
	// annotated methods handle the HTTP POST requests matched with given URI
	// expression
	@CachePut(value="user", key="#token")
	@ApiOperation(value = "its api for registration", response = Response.class)
	@PostMapping("/user/register")
	// ResponseEntity represents the whole HTTP response: status code, headers, and
	// body.
	public ResponseEntity<Response> registration(@RequestBody UserDto information) {
		boolean result = service.register(information);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("registration sucessfully", information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user alerday exit"));

	}

	/* api for login */
	@ApiOperation(value = "its api for login", response = Response.class)
	@PostMapping("/user/login")
	@CachePut(value="user", key="#token")
	public ResponseEntity<UserDetail> login(@RequestBody LoginInformation information) {
		UserInformation userInformation = service.login(information);
		if (userInformation != null) {
			String token = generate.jwtToken(userInformation.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserDetail("login succesfull"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("login failed"));

	}
	
	/*api for login verfication*/
	@ApiOperation(value = "its api for user verfication", response = Response.class)
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws Exception {

		boolean update = service.verify(token);
		if (update) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified",token));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("not verified"));

	}
	
	/*api for forgot password*/
	@ApiOperation(value = "its api for forgot password", response = Response.class)
	@PostMapping("user/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPassword user) {
		boolean result = service.isUserExist(user.getEmail());
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", user.getEmail()));
		}

		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new Response("not verfied"));

	}

	/* API for for updating password with token */
	@ApiOperation(value = "its api for update", response = Response.class)
	@PutMapping("user/update/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token, @RequestBody PasswordUpdate update) {
		boolean result = service.update(update, token);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("Password updated sucessfully", update));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Password does't match"));
	}

	/* api getting all user details */
	@ApiOperation(value = "its api for  take all users", response = Response.class)
	@GetMapping("user/getusers")
	public ResponseEntity<Response> getUsers() {
		List<UserInformation> users = service.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("the users are", users));

	}

	/* api getting all user details */
	@ApiOperation(value = "its api for  take one users", response = Response.class)
	@GetMapping("user/getuser")
	public ResponseEntity<Response> getoneUser(@RequestHeader("token") String token) {
		return service.getsingleUser(token);
	}
	@PostMapping("/addCollabrator")
	public ResponseEntity<Response> addCollabrator(@RequestParam ("noteId") Long noteId,@RequestParam("email") String email,@RequestHeader ("token") String token){
		NoteInformation note=service.addCollabrator(noteId,email,token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("added collabrator",note));
		
	}
	
	@GetMapping("/getCollabrator")
	public ResponseEntity<Response> getCollabrator(@RequestParam("noteId") Long noteId,@RequestParam("email") String email,@RequestHeader("token") String token){
		List<NoteInformation> note=service.getCollabNote(noteId,email,token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("The notes are", note));
	}
	@DeleteMapping("/remove collabrator")
	public ResponseEntity<Response> removeCollabrator(@RequestParam("noteId") Long noteId,@RequestParam("email") String email,@RequestHeader("token") String token){
		NoteInformation note=service.removeCollabrator(noteId,email,token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("collbrate note is removed", note));
		
	}
	

}
