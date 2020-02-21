package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDto;

public interface LabelService {

	void createLabel(LabelDto label, String token);

}
