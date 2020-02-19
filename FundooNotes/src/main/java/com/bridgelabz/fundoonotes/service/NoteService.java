package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;

public interface NoteService {
	void createNote(NoteDto information, String token);

	void updateNote(NoteUpdate note, String token);

	void archievNote(Long id, String token);

	void pin(Long id, String token);

	void deleteNote(Long id, String token);

	boolean deletePermently(Long id, String token);

	void addColor(Long id, String token, String color);

}
