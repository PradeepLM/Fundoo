package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.Profile;

/**
 * 
 * @author pradeep
 *
 */

public interface ProfileService {

	Profile storePicsInS3(MultipartFile file, String originalFilename, String contentType, String token);
	
	
	

}
