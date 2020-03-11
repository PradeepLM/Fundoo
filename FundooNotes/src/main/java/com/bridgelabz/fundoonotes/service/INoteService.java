package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface INoteService {
	void createNote(NoteDto information, String token);

	void updateNote(NoteUpdate note, String token);

	void archievNote(Long id, String token);

	void pin(Long id, String token);

	void deleteNote(Long id, String token);

	boolean deletePermently(Long id, String token);

	void addColor(Long id, String token, String color);

	List<NoteInformation> getArchieved(String token);

	List<NoteInformation> getTrashed(String token);

	List<NoteInformation> getAllNotes(String token);

	void addReminder(Long noteId, String token, RemainderDto remainder);

	void removeReminder(Long noteId, String token, RemainderDto remainder);

	NoteInformation searchByid(Long title) throws IOException;

	List<NoteInformation> searchByTitle(String title) throws IOException;

}
