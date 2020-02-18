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
			Long userId = tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			if (user != null) {
				noteInformation = modelMapper.map(information, NoteInformation.class);
				noteInformation.setCreatedDateAndTime(LocalDateTime.now());
				noteInformation.setArchieved(false);
				noteInformation.setPinned(false);
				noteInformation.setTrashed(false);
				noteInformation.setColour("yellow");
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
			Long userId = tokenGenerator.parseJwt(token);
			user = repository.getUserById(userId);
			NoteInformation noteinf = noteRepository.findById(userId);
			System.out.println("notesingf" + noteinf);
			if (user != null) {
				noteinf.setId(information.getId());
				System.out.println("jjj");
				noteinf.setDescription(information.getDescription());
				System.out.println("kkk");
				noteinf.setTitle(information.getTitle());
				System.out.println("aha");
				noteinf.setArchieved(information.isArchieved());
				System.out.println("ppp");
				noteinf.setTrashed(information.isTrashed());
				noteinf.setPinned(information.isPinned());
				noteinf.setUpDateAndTime(LocalDateTime.now());
				noteRepository.save(noteinf);
			}throw new UserException("user not prsent");
		} catch (Exception e) {
			throw new UserException("user is not register");
		}
	}

}
