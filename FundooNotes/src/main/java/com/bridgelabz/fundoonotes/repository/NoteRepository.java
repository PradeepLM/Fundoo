package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface NoteRepository {
	NoteInformation save(NoteInformation noteInformation);
}
