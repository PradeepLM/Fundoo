package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

public interface LabelRepository {
	LabelInformation fetchLabel(Long userid, String labelname);

	LabelInformation save(LabelInformation labelInformation);

	LabelInformation fetchLabelById(Long labeId);

	int deleteLabel(Long labelId);

}
