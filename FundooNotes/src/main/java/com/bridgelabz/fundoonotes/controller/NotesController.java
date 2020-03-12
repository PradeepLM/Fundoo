package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.INoteService;

import io.swagger.annotations.ApiOperation;

/*
 * @author Pradeep
 * @purpose: controller controls the data flow into model object and updates the view whenever data changes  
 */
@RestController
@CrossOrigin("*")
public class NotesController {
	@Autowired
	private INoteService service;

	/* API for creating a Note */
	@ApiOperation(value = "its api for  create note", response = Response.class)
	@PostMapping("/note/create")
	public ResponseEntity<Response> create(@RequestBody NoteDto information, @RequestHeader("token") String token) {
		service.createNote(information, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created",information));
	}

	/* api for updating a note */
	@ApiOperation(value = "its api for  create note update", response = Response.class)
	@PutMapping("/note/update")
	public ResponseEntity<Response> update(@RequestBody NoteUpdate note, @RequestHeader("token") String token) {
		service.updateNote(note, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note updated", note));

	}

	/* api for archieve a note */
	@ApiOperation(value = "its api for  archieve note", response = Response.class)
	@PostMapping("/note/archieve/{id}")
	public ResponseEntity<Response> archieve(@PathVariable Long id, @RequestHeader("token") String token) {
		service.archievNote(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved"));
	}

	/* api for pin a note */
	@ApiOperation(value = "its api for  pin note", response = Response.class)
	@PostMapping("/note/pin/{id}")
	public ResponseEntity<Response> pin(@PathVariable Long id, @RequestHeader("token") String token) {
		service.pin(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned"));
	}

	/* api for delete a note */
	@ApiOperation(value = "its api for  delete note", response = Response.class)
	@DeleteMapping("/note/delete/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id, @RequestHeader("token") String token) {
		service.deleteNote(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted"));

	}

	/* api for delete a note permently */
	@ApiOperation(value = "its api for  delete perment note", response = Response.class)
	@DeleteMapping("/note/deletePermently/{id}")
	public ResponseEntity<Response> deletePerment(@PathVariable Long id, @RequestHeader("token") String token) {
		service.deletePermently(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted"));
	}

	/* api for updating color to note */
	@ApiOperation(value = "its api for  add color note", response = Response.class)
	@PostMapping("/note/addcolor")
	public ResponseEntity<Response> addColor(@RequestParam("noteId") Long noteId, @RequestParam("color") String color,
			@RequestHeader("token") String token) {
		service.addColor(noteId, token, color);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note color Changed"));

	}

	/* api for getting all archieve all notes */
	@ApiOperation(value = "its api for  get archieve note", response = Response.class)
	@GetMapping("/note/getArchieve/{id}")
	public ResponseEntity<Response> getArchieve(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getArchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("archieve notes", list));

	}

	/* api for getting all trashes notes */
	@ApiOperation(value = "its api for get trashed note", response = Response.class)
	@GetMapping("/note/getTrashed/{id}")
	public ResponseEntity<Response> getTrashed(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getTrashed(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("trashed notes",  list));

	}

	/* api for getting all notes */
	@ApiOperation(value = "its api for  get all note", response = Response.class)
	@GetMapping("/note/getAllNotes/{id}")
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getAllNotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("All Notes",  list));
	}
	
	/*api for adding reminder to notes*/
	@ApiOperation(value = "its api for  add reminder note", response = Response.class)
	@PutMapping("/note/addReminder/{id}")
	public ResponseEntity<Response> addReminder(@RequestHeader("token") String token,@RequestParam("noteId") Long noteId,@RequestBody RemainderDto remainder){
		service.addReminder(noteId, token, remainder);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Reminder Notes", remainder));
		
	}
	
	/*api for removing reminder to notes*/
	@ApiOperation(value = "its api for  remove remainder note", response = Response.class)
	@PutMapping("/note/removeReminder/{id}")
	public ResponseEntity<Response> removeRemainder(@RequestHeader("token") String token,@RequestParam ("noteId") Long noteId,@RequestBody RemainderDto remainder){
		service.removeReminder(noteId, token, remainder);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Reminder Notes", remainder));
		
	}
	/*api for searching id using elastic search*/
	@GetMapping("/note/searchById")
	public ResponseEntity<Response> search(@RequestParam ("noteId") Long noteId,@RequestHeader("token") String token) throws IOException{
		NoteInformation notes=service.searchByid(noteId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("the notes are", notes));
	}
	
	/*api for searching id using elastic search*/
	@GetMapping("/note/searchByTitle")
	public ResponseEntity<Response> search(@RequestParam ("title") String title,@RequestHeader("token") String token) throws IOException{
		List<NoteInformation> notes=service.searchByTitle(title);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("the notes are", notes));
	}
	
	
	
	
	
	
	

}
