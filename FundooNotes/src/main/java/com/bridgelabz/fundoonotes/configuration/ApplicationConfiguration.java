package com.bridgelabz.fundoonotes.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * 
 * @author pradeep
 *
 */
@Configuration//allow to register extra beans in the context or
//import additional configuration classes
public class ApplicationConfiguration {
@Bean
// annotation tells that a method produces a bean to be managed by the Spring container
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
