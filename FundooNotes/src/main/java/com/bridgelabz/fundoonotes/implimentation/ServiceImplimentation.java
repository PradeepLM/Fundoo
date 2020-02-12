package com.bridgelabz.fundoonotes.implimentation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.Services;
@Service// used to write business logic in a different layerS
public class ServiceImplimentation implements Services {
	UserInformation userInformation=new UserInformation();
	@Autowired//is used for automatic dependency injection.
	private UserRepository repository;
	@Autowired
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public boolean register(UserDto information) {
		// TODO Auto-generated method stub
		return false;
	}
	
}