package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NoteController {
	@Autowired
	private NoteService service;

	/* API for creating a Note */
	@PostMapping("/note/create")
	public ResponseEntity<Response> create(@RequestBody NoteDto information, @RequestHeader("token") String token) {
		service.createNote(information, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200, information));
	}

	/* api for updating a note */
	@PutMapping("/note/update")
	public ResponseEntity<Response> update(@RequestBody NoteUpdate note, @RequestHeader("token") String token) {
		service.updateNote(note, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note updated", 200, note));

	}

	/* api for archieve a note */
	@PostMapping("/note/archieve/{id}")
	public ResponseEntity<Response> archieve(@PathVariable Long id, @RequestHeader("token") String token) {
		service.archievNote(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved", 200));
	}

	/* api for pin a note */
	@PostMapping("/note/pin/{id}")
	public ResponseEntity<Response> pin(@PathVariable Long id, @RequestHeader("token") String token) {
		service.pin(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", 200));
	}

	/* api for delete a note */
	@DeleteMapping("/note/delete/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id, @RequestHeader("token") String token) {
		service.deleteNote(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted", 200));

	}

	/* api for delete a note permently */
	@DeleteMapping("/note/deletePermently/{id}")
	public ResponseEntity<Response> deletePerment(@PathVariable Long id, @RequestHeader("token") String token) {
		service.deletePermently(id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted", 200));
	}

	/* api for updating color to note */
	@PostMapping("/note/addcolor")
	public ResponseEntity<Response> addColor(@RequestParam("noteId") Long noteId, @RequestParam("color") String color,
			@RequestHeader("token") String token) {
		service.addColor(noteId, token, color);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note color Changed", 200));

	}

	/* api for getting all archieve all notes */
	@GetMapping("/note/getArchieve/{id}")
	public ResponseEntity<Response> getArchieve(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getArchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("archieve notes", 200, list));

	}

	/* api for getting all trashes notes */
	@GetMapping("/note/getTrashed/{id}")
	public ResponseEntity<Response> getTrashed(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getTrashed(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("trashed notes", 200, list));

	}

	/* api for getting all notes */
	@GetMapping("/note/getAllNotes/{id}")
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token) {
		List<NoteInformation> list = service.getAllNotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("All Notes", 200, list));
	}
	
	/*api for adding reminder to notes*/
	@PutMapping("/note/addReminder/{id}")
	public ResponseEntity<Response> addReminder(@RequestHeader("token") String token,@RequestParam("noteId") Long noteId,@RequestBody RemainderDto remainder){
		service.addReminder(noteId, token, remainder);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Reminder Notes", 200,remainder));
		
	}
	
	
	
	
	
	
	
	

}
