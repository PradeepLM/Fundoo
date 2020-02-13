package com.bridgelabz.fundoonotes.implimentation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

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
		UserInformation user = repository.getUser(information.getUsername());
		if (user != null) {
			if ((user.isVerified() == true) && (enceryption.matches(information.getPassword(), user.getPassword())))
				;
			System.out.println(generate.jwtToken(user.getUserId()));
			return user;
		} else {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					generate.jwtToken(user.getUserId()));
			MailServiceProvider.sendMail(information.getUsername(), "verification", mailResponse);
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
		repository.verify(id);
		return true;
	}

}