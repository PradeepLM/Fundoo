package com.bridgelabz.fundoonotes.implimentation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class NoteImplimentation implements NoteService {
	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private UserRepository repository;
	private UserInformation user = new UserInformation();
	@Autowired
	private NoteRepository noteRepository;
	private NoteInformation noteInformation = new NoteInformation();
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ElasticSearchService elasticSevice;

	@Transactional
	@Override
	public void createNote(NoteDto information, String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				noteInformation = modelMapper.map(information, NoteInformation.class);
				noteInformation.setCreatedDateAndTime(LocalDateTime.now());
				noteInformation.setArchieved(false);
				noteInformation.setPinned(false);
				noteInformation.setTrashed(false);
				noteInformation.setColour("yellow");
				noteInformation.setUpDateAndTime(LocalDateTime.now());
				user.getNote().add(noteInformation);
				NoteInformation note = noteRepository.save(noteInformation);
				if(note!=null) {
					try {
						elasticSevice.CreateNote(noteInformation);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			throw new UserException("user is not present given by Id");
		}

	}

	@Transactional
	@Override
	public void updateNote(NoteUpdate information, String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(information.getId());
			if (user != null) {
				noteinf.setId(information.getId());
				noteinf.setDescription(information.getDescription());
				noteinf.setTitle(information.getTitle());
				noteinf.setArchieved(information.isArchieved());
				noteinf.setTrashed(information.isTrashed());
				noteinf.setPinned(information.isPinned());
				noteinf.setUpDateAndTime(LocalDateTime.now());
				noteRepository.save(noteinf);
			} else
				throw new UserException("user not prsent");
		} catch (Exception e) {
			throw new UserException("user is not register");
		}
	}

	@Transactional
	@Override
	public void archievNote(Long id, String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(id);
			if (noteinf != null) {
				noteinf.setPinned(false);
				noteinf.setArchieved(!noteinf.isArchieved());
				noteRepository.save(noteinf);
			}

		} catch (Exception e) {
			throw new UserException("user is not present");
		}
	}

	@Transactional
	@Override
	public void pin(Long id, String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(id);
			if (noteinf != null) {
				noteinf.setArchieved(false);
				noteinf.setPinned(!noteinf.isPinned());
				noteRepository.save(noteinf);
			}
		} catch (Exception e) {
			throw new UserException("user is not present");
		}
	}

	@Transactional
	@Override
	public void deleteNote(Long id, String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(id);
			if (noteinf != null) {
				noteinf.setTrashed(!noteinf.isTrashed());
				noteRepository.save(noteinf);
			}
		} catch (Exception e) {
			throw new UserException("note is not trashed");
		}
	}

	@Transactional
	@Override
	public boolean deletePermently(Long id, String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(id);
			if (noteinf != null) {	
				List<LabelInformation> labels=noteinf.getList();
				if(labels!=null) {
					labels.clear();
				}
				noteRepository.deleteNote(id, userId);
				elasticSevice.deleteNote(noteinf);
			}else {
				throw new UserException("Given not is not present");
			}

		} catch (Exception e) {
			throw new UserException("note is not deleted permently");
		}
		return false;
	}

	@Transactional
	@Override
	public void addColor(Long id, String token, String color) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				NoteInformation noteinf = noteRepository.findById(id);
				if (noteinf != null) {
					noteinf.setColour(color);
					noteRepository.save(noteinf);
				} else {
					throw new UserException("note does not exist");
				}
			} else {
				throw new UserException("user does not exists");
			}
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public List<NoteInformation> getArchieved(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getArchievedNotes(userId);
				return list;
			} else {
				throw new UserException("user does not exist");
			}
		} catch (Exception e) {
			throw new UserException("not get all archieve");
		}
	}

	@Transactional
	@Override
	public List<NoteInformation> getTrashed(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getTrashedNotes(userId);
				return list;
			} else {
				throw new UserException("user does not exist");
			}
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public List<NoteInformation> getAllNotes(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getAllNotes(userId);
				return list;
			}
			throw new UserException("user dosn't exit");
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public void addReminder(Long noteId, String token, RemainderDto remainder) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(noteId);
			if (noteinf != null) {
				noteinf.setReminder(remainder.getReminder());
				noteRepository.save(noteinf);
			} else {
				throw new UserException("note dosn't exit");
			}
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}

	@Transactional
	@Override
	public void removeReminder(Long noteId, String token, RemainderDto remainder) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(noteId);
			if (noteinf != null) {
				noteinf.setReminder(null);
				noteRepository.save(noteinf);
			} else {
				throw new UserException("user doesnot exit");
			}
		} catch (Exception e) {
			throw new UserException("error occured");
		}
	}
	
	@Transactional
	@Override
	public NoteInformation searchByid(Long noteId) throws IOException {
		NoteInformation notes=elasticSevice.searchByNoteId(noteId);
		if(notes!=null) {
			System.out.println("Note :"+notes.getTitle());
			return notes;
		}else {
			return null;
		}
		
	}
	
	@Transactional
	@Override
	public List<NoteInformation> searchByTitle(String title) throws IOException {
		List<NoteInformation> notes=elasticSevice.searchByTitle(title);
		if(notes!=null) {
			return notes;
		}else {
			return null;
		}
	}
}
