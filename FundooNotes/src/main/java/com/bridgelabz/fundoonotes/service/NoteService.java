package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;

public interface NoteService {
	void createNote(NoteDto information, String token);
}
