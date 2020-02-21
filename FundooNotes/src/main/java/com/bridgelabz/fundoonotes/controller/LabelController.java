package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
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
	@PostMapping("/label/addLabel")
	public ResponseEntity<Response> addLabel(@RequestParam("labelId") Long labeId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		service.addLabel(labeId, token, noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label added", 200));
	}
}
