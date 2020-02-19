package com.bridgelabz.fundoonotes.implimentation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
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

	@Transactional
	@Override
	public void createNote(NoteDto information, String token) {
		try {
			Long userId = (long)tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				noteInformation = modelMapper.map(information, NoteInformation.class);
				noteInformation.setCreatedDateAndTime(LocalDateTime.now());
				noteInformation.setArchieved(false);
				noteInformation.setPinned(false);
				noteInformation.setTrashed(false);
				noteInformation.setColour("yellow");
				noteInformation.setUpDateAndTime(LocalDateTime.now());
				NoteInformation note = noteRepository.save(noteInformation);

			}
		} catch (Exception e) {
			throw new UserException("user is not present given by Id");
		}

	}

	@Transactional
	@Override
	public void updateNote(NoteUpdate information, String token) {
		try {
			Long userId = (long)tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			
			NoteInformation noteinf = noteRepository.findById(information.getId());
			System.out.println("Note Id "+noteinf.getId());
			System.out.println("note color "+noteinf.getColour());
			if (user != null) {
				noteinf.setId(information.getId());
				noteinf.setDescription(information.getDescription());
				noteinf.setTitle(information.getTitle());
				noteinf.setArchieved(information.isArchieved());
				noteinf.setTrashed(information.isTrashed());
				noteinf.setPinned(information.isPinned());
				noteinf.setUpDateAndTime(LocalDateTime.now());
				noteRepository.save(noteinf);
			}throw new UserException("user not prsent");
		} catch (Exception e) {
			throw new UserException("user is not register");
		}
	}
	
	
	@Transactional
	@Override
	public void archievNote(Long id, String token) {
		NoteInformation note=noteRepository.findById(id);
		note.setArchieved(!note.isArchieved());
		noteRepository.save(note);
	}
	
	@Transactional
	@Override
	public void pin(long id, String token) {
		NoteInformation note=noteRepository.findById(id);
		note.setPinned(!note.isPinned());
		noteRepository.save(note);
	}

}
