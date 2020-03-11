package com.bridgelabz.fundoonotes.implimentation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.RabbitMQSender;
import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IServices;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

@Service // used to write business logic in a different layers
public class UserServiceImplimentation implements IServices {
	UserInformation userInformation = new UserInformation();
	@Autowired // is used for automatic dependency injection.
	private UserRepository repository;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private BCryptPasswordEncoder enceryption;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObject;
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private RabbitMQSender rabbitMQSender;

	@Override//its used for registration by user
	public boolean register(UserDto information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user == null) {
			userInformation = modelMapper.map(information, UserInformation.class);
			userInformation.setCreateDate(LocalDateTime.now());
			String epassword = enceryption.encode(information.getPassword());
			userInformation.setPassword(epassword);
			userInformation.setVerified(false);
			userInformation = repository.save(userInformation);

			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					generate.jwtToken(userInformation.getUserId()));
			mailObject.setEmail(information.getEmail());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			//MailServiceProvider.sendMail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
			rabbitMQSender.send(mailObject);
			return true;
		}

		throw new UserException("user already exists with the same mail id",HttpStatus.NOT_FOUND);
	}

	@Transactional
	@Override//its used for login 
	public UserInformation login(LoginInformation information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user != null) {
			if ((user.isVerified() == true) && (enceryption.matches(information.getPassword(), user.getPassword())))
			
				System.out.println(generate.jwtToken(user.getUserId()));
			return user;
		} else {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					generate.jwtToken(user.getUserId()));
			MailServiceProvider.sendMail(information.getEmail(), "verification", mailResponse);
			return null;
		}

	}

	public String generateToken(Long id) {
		return generate.jwtToken(id);
	}

	@Transactional
	@Override//its used for verify the token
	public boolean verify(String token) throws Exception {
		System.out.println("id in verification" + (long) generate.parseJwt(token));
		Long id = (long) generate.parseJwt(token);

		return repository.verify(id);
		// return true;
	}

	@Override//its checks the user is exit or not
	public boolean isUserExist(String email) {
		try {
			UserInformation user = repository.getUser(email);
			if (user.isVerified() == true) {
				String mailRespone = response.fromMessage("http://localhost/8080/verify",
						generate.jwtToken(user.getUserId()));
				MailServiceProvider.sendMail(user.getEmail(), "verification", mailRespone);
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			throw new UserException("User doesn't exist",HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	@Override//its used for update user information
	public boolean update(PasswordUpdate information, String token) {
		Long id = null;
		try {
			id = (Long) generate.parseJwt(token);
			String epassword = enceryption.encode(information.getConfirmPassword());
			information.setConfirmPassword(epassword);
			return repository.upDate(information, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("invalid password",HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@Override//its used for get all the user
	public List<UserInformation> getUsers() {
		List<UserInformation> users = repository.getUsers();
		UserInformation user = users.get(0);
		return users;
	}
	@Transactional
	@Override//its used for get the user
	public ResponseEntity<Response> getsingleUser(String token) {
		Long id;
		UserInformation user ;
		try {
			id = (Long) generate.parseJwt(token);
			System.out.println(id);
			
			Optional<UserInformation> user2 =Optional.ofNullable(repository.getUserById(id)); 
			if(user2.isPresent()) {
				System.out.println("single user"+user2.get().getEmail());
			
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user found",202,user2));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("user Not found",202,null));		}
		
		
		return null;
	}
	@Transactional
	@Override//its used forcollbrate the note with user
	public NoteInformation addCollabrator(Long noteId, String email, String token) {
		UserInformation user;
		UserInformation collabrator=repository.getUser(email);
		try {
			Long userId=generate.parseJwt(token);
			user=repository.getUserById(userId);
		} catch (Exception e) {
			throw new UserException("user is not present given your email id",HttpStatus.NOT_FOUND);
		}
		if(user!=null) {
			if(collabrator!=null) {
				NoteInformation note=noteRepository.findById(noteId);
				collabrator.getColbrateNote().add(note);
				return note;
			}else {
				throw new UserException("Given Email is not present",HttpStatus.NOT_FOUND);
			}
		}else {
			throw new UserException("collabrator is not present",HttpStatus.NOT_FOUND);
		}
	}
	@Transactional
	@Override//its used for who are all collabrate with note
	public List<NoteInformation> getCollabNote(Long noteId, String email, String token) {
		Long userId=generate.parseJwt(token);
		UserInformation user=repository.getUserById(userId);
		List<NoteInformation> notes=user.getColbrateNote();
		return notes;
	}
	
	@Transactional
	@Override//its used for remove collabrate with note
	public NoteInformation removeCollabrator(Long noteId, String email, String token) {
		UserInformation user;
		UserInformation collabrator=repository.getUser(email);
		try {
			Long userId=generate.parseJwt(token);
			user=repository.getUserById(userId);
		} catch (Exception e) {
			throw new UserException("user is not present given your email id",HttpStatus.NOT_FOUND);
		}
		NoteInformation note=noteRepository.findById(noteId);
		note.getColabratorUser().remove(collabrator);
		return note;
		
	}
}
