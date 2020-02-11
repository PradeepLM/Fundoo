package com.bridgelabz.funoonotes.implimentation;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.funoonotes.entity.UserInformation;
import com.bridgelabz.funoonotes.service.Services;
@Service
public class ServiceImplimentation implements Services {

	@Override
	public boolean register(UserDto information) {
		UserInformation userInformation=new UserInformation();
		
		return false;
	}

}
