package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface UserRepository {
	UserInformation save(UserInformation userInformation);

	UserInformation getUser(String name);

	//UserInformation getUserById(Long id);

	//boolean upDate(PasswordUpdate information, Long id);

	//boolean verify(Long id);

	//List<UserInformation> getUsers();
}
