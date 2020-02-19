package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface NoteRepository {
	NoteInformation save(NoteInformation noteInformation);

	NoteInformation findById(Long id);

	boolean deleteNote(Long id, Long userId);

	List<NoteInformation> getArchievedNotes(Long userId);

}
