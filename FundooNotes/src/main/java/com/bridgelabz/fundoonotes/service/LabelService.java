package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;

public interface LabelService {

	void createLabel(LabelDto label, String token);

	void addLabel(Long labelId, String token, Long noteId);

	void removeLabel(Long labelId, String token, Long noteId);

	void updateLabel(LabelUpdate label, String token);

	void deleteLabel(LabelUpdate label, String token);

}
