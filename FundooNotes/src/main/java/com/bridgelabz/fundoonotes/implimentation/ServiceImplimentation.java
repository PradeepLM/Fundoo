package com.bridgelabz.fundoonotes.implimentation;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.service.Services;
@Service
public class ServiceImplimentation implements Services {

	@Override
	public boolean register(UserDto information) {
		UserInformation userInformation=new UserInformation();
		
		return false;
	}

}
