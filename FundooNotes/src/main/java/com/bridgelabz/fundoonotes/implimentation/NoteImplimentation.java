package com.bridgelabz.fundoonotes.implimentation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
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
	private UserInformation user=new UserInformation();
	@Autowired
	private NoteRepository noteRepository;
	private NoteInformation noteInformation=new NoteInformation();
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	@Override
	public void createNote(NoteDto information, String token) {
		try {
			Long userId=tokenGenerator.parseJwt(token);
			System.out.println(userId);
			user=repository.getUserById(userId);
			System.out.println(user);
			if(user!=null) {
				noteInformation=modelMapper.map(information, NoteInformation.class);
				noteInformation.setCreatedDateAndTime(LocalDateTime.now());
				noteInformation.setArchieved(false);
				noteInformation.setPinned(false);
				noteInformation.setTrashed(false);
				noteInformation.setColour("yellow");
				user.getUserId();
			}
		} catch (Exception e) {
			throw new UserException("user is not present given by Id");
		}
		
	}

}
