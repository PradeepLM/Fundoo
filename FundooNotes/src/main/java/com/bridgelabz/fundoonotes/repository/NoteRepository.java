package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface NoteRepository {
	NoteInformation save(NoteInformation noteInformation);

	NoteInformation findById(Long id);

	boolean deleteNote(Long id, Long userId);

}
