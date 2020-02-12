package com.bridgelabz.fundoonotes.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {
@Bean
public BCryptPasswordEncoder getpasswordEncryption()
{
	return new BCryptPasswordEncoder();
}
@Bean
public ModelMapper modelMapper()
{
	return new ModelMapper();
}
}
