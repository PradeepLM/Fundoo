package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface LabelRepository {
	LabelInformation fetchLabel(Long userid, String labelname);

	LabelInformation save(LabelInformation labelInformation);

	LabelInformation fetchLabelById(Long labeId);

	int deleteLabel(Long labelId);

	List<LabelInformation> getAllLabels(Long id);

	LabelInformation getLabelNotes(Long id);

}
