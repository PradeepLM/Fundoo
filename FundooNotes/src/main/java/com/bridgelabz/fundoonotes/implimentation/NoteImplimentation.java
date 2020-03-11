package com.bridgelabz.fundoonotes.implimentation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.IElasticSearchService;
import com.bridgelabz.fundoonotes.service.INoteService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
/**
 * 
 * @author pradeep
 *
 */
@Service
public class NoteImplimentation implements INoteService {
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
	private IElasticSearchService elasticSevice;

	@Transactional
	@Override//its used for create a note
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
			throw new UserException("user is not present given by Id",HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	@Override//its used for update a note
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
				throw new UserException("user not prsent",HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new UserException("user is not register",HttpStatus.UNAUTHORIZED);
		}
	}

	@Transactional
	@Override//its used for archive a note
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
			throw new UserException("user is not present",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for pin a note
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
			throw new UserException("user is not present",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for delete a note
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
			throw new UserException("note is not trashed",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for delete permenetly a note
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
				throw new NoteException("Given note is not present",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			throw new NoteException("note is not deleted permently",HttpStatus.NOT_FOUND);
		}
		return false;
	}

	@Transactional
	@Override//its used for add a color a note
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
					throw new NoteException("note does not exist",HttpStatus.NOT_FOUND);
				}
			} else {
				throw new UserException("user does not exists",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new NoteException("error occured",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for Get Archieved a note
	public List<NoteInformation> getArchieved(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getArchievedNotes(userId);
				return list;
			} else {
				throw new UserException("user does not exist",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new UserException("not get all archieve",HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@Override//its used for Get Trashed a note
	public List<NoteInformation> getTrashed(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getTrashedNotes(userId);
				return list;
			} else {
				throw new UserException("user does not exist",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new NoteException("error occured",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for Get all notes a note
	public List<NoteInformation> getAllNotes(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				List<NoteInformation> list = noteRepository.getAllNotes(userId);
				return list;
			}
			throw new UserException("user dosn't exit",HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new NoteException("error occured",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for add remainder a note
	public void addReminder(Long noteId, String token, RemainderDto remainder) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(noteId);
			if (noteinf != null) {
				noteinf.setReminder(remainder.getReminder());
				noteRepository.save(noteinf);
			} else {
				throw new NoteException("note dosn't exit",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new NoteException("error occured",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for remove remainder a note
	public void removeReminder(Long noteId, String token, RemainderDto remainder) {
		try {
			Long userId = (long) tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(noteId);
			if (noteinf != null) {
				noteinf.setReminder(null);
				noteRepository.save(noteinf);
			} else {
				throw new UserException("user doesnot exit",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new UserException("error occured",HttpStatus.NOT_FOUND);
		}
	}
	
	@Transactional
	@Override//its used for  searchByid a note
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
	@Override//its used for  searchBy title a note using elastic search
	public List<NoteInformation> searchByTitle(String title) throws IOException {
		List<NoteInformation> notes=elasticSevice.searchByTitle(title);
		if(notes!=null) {
			return notes;
		}else {
			return null;
		}
	}
}
