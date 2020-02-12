package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.Services;

@RestController
public class UserController {
	@Autowired
	private Services service;

	@PostMapping("/user/register")
	public ResponseEntity<Response> registration(@RequestBody UserDto information) {
		boolean result = service.register(information);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("registration sucessfully", 200, information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user alerday", 400, information));

	}
	
	@GetMapping("/hi")
	public ResponseEntity<Response> display() {
		return ResponseEntity.status(HttpStatus.OK).body(new Response("I am getting the response", 200));
	}
}
