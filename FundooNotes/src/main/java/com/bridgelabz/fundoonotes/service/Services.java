package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;

public interface Services {
	boolean register(UserDto information);

	UserInformation login(LoginInformation information);

	boolean verify(String token) throws Exception;

	boolean isUserExist(String email);

	boolean update(PasswordUpdate information, String token);

	List<UserInformation> getUsers();

	ResponseEntity<Response> getsingleUser(String token);

	NoteInformation addCollabrator(Long noteId, String email, String token);

}
