package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface IElasticSearchService {
	String CreateNote(NoteInformation note);

	String deleteNote(NoteInformation noteinf);

//	List<NoteInformation> searchByTitle(String title);
	NoteInformation searchByNoteId(Long noteId) throws IOException;

	List<NoteInformation> searchByTitle(String title) throws IOException;
	
}
