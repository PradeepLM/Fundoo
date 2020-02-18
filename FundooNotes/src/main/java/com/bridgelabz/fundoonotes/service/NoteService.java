package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;

public interface NoteService {
	void createNote(NoteDto information, String token);

	void updateNote(NoteUpdate note, String token);
}