package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author :pradeep
 *@purpose :controller controls the data flow into model object and updates the view whenever data changes
 */
@RestController
public class LabelesController {
	@Autowired
	private LabelService service;

	/* api for creating label */
	@ApiOperation(value = "its creates label with usernote ", response = Response.class)
	@PostMapping("/label/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		service.createLabel(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label is created"));

	}

	/* api for add label with note */
	@ApiOperation(value = "its added label to note api", response = Response.class)
	@PostMapping("/label/addLabel")
	public ResponseEntity<Response> addLabel(@RequestParam("labelId") Long labelId, @RequestHeader("token") String token,
			@RequestParam("noteId") Long noteId) {
		service.addLabel(labelId, token, noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label added"));
	}

	/* api for remove the label with note */
	@ApiOperation(value = "its remove label to note api", response = Response.class)
	@PostMapping("/label/remove")
	public ResponseEntity<Response> removeLabel(@RequestParam("labelId") Long labelId,
			@RequestHeader("token") String token, @RequestParam("noteId") Long noteId) {
		service.removeLabel(labelId, token, noteId);		
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is removed"));
	}
	
	
	/* api for update the label*/
	@ApiOperation(value = "its api for edit label", response = Response.class)
	@PutMapping("/label/update")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdate label,@RequestHeader ("token") String token){
		service.updateLabel(label,token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is updated",label));
	}
	
	
	/*api for delete the label*/
	@ApiOperation(value = "its api for delete label", response = Response.class)
	@DeleteMapping("/label/delete")
	public ResponseEntity<Response> deleteLabel(@RequestBody LabelUpdate label,@RequestHeader("token") String token){
		service.deleteLabel(label,token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is deleted",label));
	}
	
	
	/*api for get all label */
	@ApiOperation(value = "its api for get all label", response = Response.class)
	@GetMapping("/label/getLabel")
	public ResponseEntity<Response> getLabel(@RequestHeader ("token") String token){
		List<LabelInformation> label=service.getLabel(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The all label is ", label));
	}
	
	/*api for get all labelNotes*/
	@ApiOperation(value = "its api for get all label with notes", response = Response.class)
	@GetMapping("/label/getlabelNotes")
	public ResponseEntity<Response> getLabelNote(@RequestHeader("token") String token,@RequestParam("labelId") Long labelId){
		List<NoteInformation> list=service.getAllNote(token,labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("all label note is",list));
		
	}
	/*api for create label note map*/
	@ApiOperation(value = "its api for create label map", response = Response.class)
	@PostMapping("/label/createLabelMap")
	public ResponseEntity<Response> createLabelMap(@RequestBody LabelDto label,@RequestHeader("token") String token,@RequestParam Long noteId){
		service.createLabelMap(label,token,noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("created label", label));
		
	}
	
	
	
	
	
	
	
	
	
}
