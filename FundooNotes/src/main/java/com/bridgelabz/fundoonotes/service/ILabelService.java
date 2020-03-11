package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface ILabelService {

	void createLabel(LabelDto label, String token);

	void addLabel(Long labelId, String token, Long noteId);

	void removeLabel(Long labelId, String token, Long noteId);

	void updateLabel(LabelUpdate label, String token);

	void deleteLabel(LabelUpdate label, String token);

	List<LabelInformation> getLabel(String token);

	List<NoteInformation> getAllNote(String token, Long labelId);

	void createLabelMap(LabelDto label, String token, Long noteId);

}
