package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

import io.swagger.annotations.ApiOperation;

@RestController
public class LabelController {
	@Autowired
	private LabelService service;

	/* api for creating label */
	@ApiOperation(value = "its creates label with usernote ", response = Response.class)
	@PostMapping("/label/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		service.createLabel(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label is created", 200));

	}

	/* api for add label with note */
	@ApiOperation(value = "its added label to note api", response = Response.class)
	@PostMapping("/label/addLabel")
	public ResponseEntity<Response> addLabel(@RequestParam("labelId") Long labelId, @RequestHeader("token") String token,
			@RequestParam("noteId") Long noteId) {
		service.addLabel(labelId, token, noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label added", 200));
	}

	/* api for remove the label with note */
	@ApiOperation(value = "its remove label to note api", response = Response.class)
	@PostMapping("/label/remove")
	public ResponseEntity<Response> removeLabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		service.removeLabel(labelId, token, noteId);		
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is removed", 200));
	}
	
	
	/* api for update the label*/
	@PutMapping("/label/update")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdate label,@RequestHeader ("token") String token){
		service.updateLabel(label,token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is updated", 200,label));
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
