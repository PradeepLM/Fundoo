package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface Services {
	boolean register(UserDto information);

	UserInformation login(LoginInformation information);

	boolean verify(String token) throws Exception;

	boolean isUserExist(String email);

	boolean update(PasswordUpdate information, String token);

}
