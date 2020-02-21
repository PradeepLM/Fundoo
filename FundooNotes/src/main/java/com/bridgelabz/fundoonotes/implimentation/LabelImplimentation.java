package com.bridgelabz.fundoonotes.implimentation;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class LabelImplimentation implements LabelService {

	private UserInformation user = new UserInformation();
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtGenerator tokenGenrator;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private NoteRepository noterepository;
	private LabelInformation labelInformation;

	@Transactional
	@Override
	public void createLabel(LabelDto label, String token) {
		Long id = null;
		try {
			id = (Long) tokenGenrator.parseJwt(token);
		} catch (Exception e) {
			throw new UserException("user does not exist");
		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = labelRepository.fetchLabel(user.getUserId(), label.getName());
			if (labelInfo == null) {
				labelInformation = modelMapper.map(label, LabelInformation.class);
				labelInformation.setUserId(user.getUserId());
				labelRepository.save(labelInformation);
			} else {
				throw new UserException("label with that name is already present ");
			}
		} else {
			throw new UserException("Note doesn't exit with userId");
		}
	}

	@Transactional
	@Override
	public void addLabel(Long labelId, String token, Long noteId) {
		NoteInformation note = noterepository.findById(noteId);
		LabelInformation label = labelRepository.fetchLabelById(labelId);
		label.getList().add(note);
		labelRepository.save(label);
	}

	@Transactional
	@Override
	public void removeLabel(Long labelId, String token, Long noteId) {
		NoteInformation note = noterepository.findById(noteId);
		LabelInformation label = labelRepository.fetchLabelById(labelId);
		label.getList().remove(note);
		labelRepository.save(label);
	}

	@Transactional
	@Override
	public void updateLabel(LabelUpdate label, String token) {
		Long id = null;
		try {
			id = tokenGenrator.parseJwt(token);
		} catch (Exception e) {
			throw new UserException("user does not exist");
		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelinfo = labelRepository.fetchLabelById(label.getLabelId());
			if (labelinfo != null) {
				labelinfo.setName(label.getLabelName());
				labelRepository.save(labelinfo);
			} else {
				throw new UserException("Name with label dosn't exit");
			}
		} else {
			throw new UserException("user dosn't exit");
		}
	}

}
