package com.bridgelabz.fundoonotes.implimentation;

import java.util.List;

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
/**
 * 
 * @author pradeep
 *
 */
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
	@Override//its used for create a label
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
	@Override////its used for add a label
	public void addLabel(Long labelId, String token, Long noteId) {
		NoteInformation note = noterepository.findById(noteId);
		LabelInformation label = labelRepository.fetchLabelById(labelId);
		label.getList().add(note);
		labelRepository.save(label);
	}

	@Transactional
	@Override//its used for remove a label
	public void removeLabel(Long labelId, String token, Long noteId) {
		NoteInformation note = noterepository.findById(noteId);
		LabelInformation label = labelRepository.fetchLabelById(labelId);
		label.getList().remove(note);
		labelRepository.save(label);
	}

	@Transactional
	@Override////its used for update a label
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

	@Transactional
	@Override////its used for delete  a label
	public void deleteLabel(LabelUpdate label, String token) {
		Long id = null;
		try {
			id = tokenGenrator.parseJwt(token);
		} catch (Exception e) {
			throw new UserException("user doesn't exit");
		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelinfo = labelRepository.fetchLabelById(label.getLabelId());
			if (labelinfo != null) {
				labelRepository.deleteLabel(label.getLabelId());
			} else {
				throw new UserException("Note does not exit");
			}
		}
	}

	@Transactional
	@Override//its used for get a label
	public List<LabelInformation> getLabel(String token) {
		Long id = null;
		try {
			id = tokenGenrator.parseJwt(token);
		} catch (Exception e) {
			throw new UserException("user doesn't exit");
		}
		List<LabelInformation> labels = labelRepository.fetchLabelId(id);
		return labels;
	}

	@Transactional
	@Override//its used for get all Label 
	public List<NoteInformation> getAllNote(String token, Long labelId) {
		LabelInformation label = labelRepository.getLabelNotes(labelId);
		List<NoteInformation> list = label.getList();
		return list;
	}

	@Transactional
	@Override//its used for createLabel and map
	public void createLabelMap(LabelDto label, String token, Long noteId) {
		Long id = null;
		try {
			id = tokenGenrator.parseJwt(token);
			UserInformation user = userRepository.getUserById(id);
			if (user != null) {
				LabelInformation labelinfo = labelRepository.fetchLabel(user.getUserId(), label.getName());
				if(labelinfo!=null) {
					labelInformation=modelMapper.map(label, LabelInformation.class);
					labelInformation.getLabelId();
					labelInformation.getName();
					labelInformation.setUserId(user.getUserId());
					labelRepository.save(labelInformation);
					NoteInformation note=noterepository.findById(noteId);
					note.getList().add(labelInformation);
					noterepository.save(note);
				}
				
			}
		} catch (Exception e) {
			throw new UserException("user dosnot exit");
		}
	}

}
