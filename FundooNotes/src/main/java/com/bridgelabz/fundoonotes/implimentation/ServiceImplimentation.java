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

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.swagger.models.RefResponse;
import net.bytebuddy.asm.Advice.Return;

@Service // used to write business logic in a different layerS
public class ServiceImplimentation implements Services {
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

	@Override
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
			MailServiceProvider.sendMail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
			return true;
		}

		throw new UserException("user already exists with the same mail id");
	}

	@Transactional
	@Override
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
	@Override
	public boolean verify(String token) throws Exception {
		System.out.println("id in verification" + (long) generate.parseJwt(token));
		Long id = (long) generate.parseJwt(token);

		return repository.verify(id);
		// return true;
	}

	@Override
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
			throw new UserException("User doesn't exist");
		}
	}

	@Transactional
	@Override
	public boolean update(PasswordUpdate information, String token) {
		Long id = null;
		try {
			id = (Long) generate.parseJwt(token);
			String epassword = enceryption.encode(information.getConfirmPassword());
			information.setConfirmPassword(epassword);
			return repository.upDate(information, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("invalid password");
		}
	}

	@Transactional
	@Override
	public List<UserInformation> getUsers() {
		List<UserInformation> users = repository.getUsers();
		UserInformation user = users.get(0);
		return users;
	}

	@Override
	public ResponseEntity<Response> getsingleUser(String token) {
		Long id;
		UserInformation user ;
		try {
			id = (Long) generate.parseJwt(token);
			System.out.println(id);
			
			Optional<UserInformation> user2 =Optional.ofNullable(repository.getUserById(id)); 
//			System.out.println(user);
			if(user2.isPresent()) {
				System.out.println("single user"+user2.get().getEmail());
			
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("user found",202,user2));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("user Not found",202,null));		}
		
		
		return null;
	}
}
